package hs.service;

import hs.modle.Location;
import hs.modle.Message;
import hs.modle.order.Order;
import hs.modle.PackMAchineGroup;
import hs.modle.UnassignOrderList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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

    private SessionManager sessionManager;

    public SessionManager getSessionManager() {
        return sessionManager;
    }
    @Autowired
    public void setSessionManager(SessionManager sessionManager) {
        this.sessionManager = sessionManager;
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
    }


    public void modifyOrderInUnassignList(int index,Order newOrder){
        unassignOrderList.modifyOrder(index,newOrder);
    }

    /**
     *中间操作面板
     * */

    public void unassignOrderInPackList(Location location,int index){
        //查询否为车道的第一张订单,如果是第一张订单需要停止包装机
        boolean isneed=packMAchineGroup.getProductLineMaps().get(location.getProductionLine()).getDeviceMaps().get(location.getPackmachineIndex()).isFristOrderInCarLane(index);

        Order order=packMAchineGroup.unassignSolver(location,index);

        unassignOrderList.addUnsignOrder(order);

        if(isneed&&(order!=null)){
            Message message=new Message();
            message.setContext(Command.SEND_PAUSE.build(order));
            message.setSendTo(packMAchineGroup.getProductLineMaps().get(location.getProductionLine()).getDeviceMaps().get(location.getPackmachineIndex()).getPackerConfigure().getDeviceIp());
            sessionManager.getMessageBus().putMessage(message);
        }



    }


    public void modifyOrderInPackList(Location location,int index,Order order){
        //TODO 修改后的订单如果订单总数小于已装数量，则拒绝修改
        boolean isneed=packMAchineGroup.getProductLineMaps().get(location.getProductionLine()).getDeviceMaps().get(location.getPackmachineIndex()).isFristOrderInCarLane(index);

        packMAchineGroup.modifySolver(location,index,order);
        if(isneed){
            Message message=new Message();
            message.setSendTo( packMAchineGroup.getProductLineMaps().get(location.getProductionLine()).getDeviceMaps().get(location.getPackmachineIndex()).getPackerConfigure().getDeviceIp());
            message.setContext(Command.SEND_REPLENISH.build(packMAchineGroup.getProductLineMaps().get(location.getProductionLine()).getDeviceMaps().get(location.getPackmachineIndex()).getAllSortOrder().get(index)));
            sessionManager.getMessageBus().putMessage(message);
        }
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


    public UnassignOrderList getUnassignOrderList() {
        return unassignOrderList;
    }

    public PackMAchineGroup getPackMAchineGroup() {
        return packMAchineGroup;
    }
}
