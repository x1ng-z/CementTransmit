package hs.service;

import hs.dao.Packer;
import hs.dao.service.OrderService;
import hs.modle.*;
import hs.modle.order.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.List;
import java.util.Set;

/**
 * @author zzx
 * @version 1.0
 * @date 2020/2/17 23:12
 */
@Component("orderOperateService")
public class OrderOperateService {
    @Autowired
    private UnassignOrderList unassignOrderList;
    @Autowired
    private PackMAchineGroup packMAchineGroup;
    private OrderService orderService;
    @Autowired
    public void setOrderService(OrderService orderService) {
        this.orderService = orderService;
    }
    public OrderService getOrderService() {
        return orderService;
    }

    private SessionManager sessionManager;

    public SessionManager getSessionManager() {
        return sessionManager;
    }
    @Autowired
    public void setSessionManager(SessionManager sessionManager) {
        this.sessionManager = sessionManager;
    }
    private Packer packer;


    public Packer getPacker() {
        return packer;
    }
    @Autowired
    public void setPacker(Packer packer) {
        this.packer = packer;
    }


    /**
     *订单分配,订单从未分配列表分配至某一个包装机的车道
     * @param order 订单，主要
     * */
    public void addOrderToUnassignList(Order order){
        unassignOrderList.addUnsignOrder(order);
    }

    public void delectOrderInUnassignList(int index){
        unassignOrderList.canncelOrder(index);
    }

    public void assignOrderInUnassignList(int index,int productline,int packmachine,int carline){
        Location lt =unassignOrderList.assignOrder(index,productline,packmachine,carline);
        packMAchineGroup.addSolver(lt);
        if(lt.getValue().getId()==null){
            orderService.insertOrder(lt.getValue());
        }else {
            orderService.updateOrder(lt.getValue());
        }
    }

    public boolean modifyOrderInUnassignList(int index,Order newOrder){
        return unassignOrderList.modifyOrder(index,newOrder);
    }

    /***中间操作面板* */
    public void unassignOrderInPackList(Location location,int index){
        //查询否为车道的第一张订单,如果是第一张订单需要停止包装机
        boolean isneedStop=packMAchineGroup.getProductLineMaps().get(location.getProductionLine()).getDeviceMaps().get(location.getPackmachineIndex()).isFristOrderInCarLane(index);

        Order order=packMAchineGroup.unassignSolver(location,index);

        unassignOrderList.addUnsignOrder(order);

        if(isneedStop&&(order!=null)){
            Message message=new Message();
            message.setContext(Command.SEND_PAUSE.build(order));
            message.setSendTo(packMAchineGroup.getProductLineMaps().get(location.getProductionLine()).getDeviceMaps().get(location.getPackmachineIndex()).getPackerConfigure().getDeviceIp());
            sessionManager.getMessageBus().putMessage(message);
        }
    }

    public boolean modifyOrderInPackList(Location location,int index,Order neworder){

        boolean isneed=packMAchineGroup.getProductLineMaps().get(location.getProductionLine()).getDeviceMaps().get(location.getPackmachineIndex()).isFristOrderInCarLane(index);

        boolean ismodifysuccess=packMAchineGroup.modifySolver(location,index, neworder);
        //修改后的订单如果订单总数小于已装数量，则拒绝修改
        if(isneed&&ismodifysuccess){
            Message message=new Message();
            message.setSendTo( packMAchineGroup.getProductLineMaps().get(location.getProductionLine()).getDeviceMaps().get(location.getPackmachineIndex()).getPackerConfigure().getDeviceIp());
            message.setContext(Command.SEND_REPLENISH.build(packMAchineGroup.getProductLineMaps().get(location.getProductionLine()).getDeviceMaps().get(location.getPackmachineIndex()).getAllSortOrder().get(index)));
            sessionManager.getMessageBus().putMessage(message);
        }
        return ismodifysuccess;
    }

    public void delectOrderInPackList(Location location,int index){
        //查询否为车道的第一张订单,如果是第一张订单需要停止包装机
        boolean isneed=packMAchineGroup.getProductLineMaps().get(location.getProductionLine()).getDeviceMaps().get(location.getPackmachineIndex()).isFristOrderInCarLane(index);

        Order order=packMAchineGroup.deleteSolver(location,index);

        if(isneed&&(order!=null)){
            Message message=new Message();
            message.setContext(Command.SEND_PAUSE.build(order));
            message.setSendTo(packMAchineGroup.getProductLineMaps().get(location.getProductionLine()).getDeviceMaps().get(location.getPackmachineIndex()).getPackerConfigure().getDeviceIp());
            sessionManager.getMessageBus().putMessage(message);
        }
    }

    /**历史单据查找**/
    public List<Order> findOrders(Integer plno,Integer packIndex, String meterialName, Instant start_time, Instant end_time, Double limit_weight){
        return orderService.getOrders(plno, packIndex,  meterialName,  start_time,  end_time,  limit_weight);
    }

    /**重要结构数据*/
    public UnassignOrderList getUnassignOrderList() {
        return unassignOrderList;
    }

    public PackMAchineGroup getPackMAchineGroup() {
        return packMAchineGroup;
    }

    public List<MaterialName> getAllMaterialNames(){
        return packer.getMaterialName();
    }
    /**得到包装机号*/
    public Set<Integer> getAllPackmachineOrder(){
        return packer.getPackerConfigure().keySet();
    }
    /**得到常用车牌*/
    public List<String> getConVechel(){
        return packer.getCommonnVechel();
    }
}
