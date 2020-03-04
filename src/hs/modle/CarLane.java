package hs.modle;


import hs.modle.order.Order;
import hs.modle.order.PackManulOrder;
import org.apache.log4j.Logger;

import java.time.Instant;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @author zzx
 * @version 1.0
 * @date 2020/2/13 12:25
 */
public class CarLane {
    public static Logger logger = Logger.getLogger(CarLane.class);

    private int id;
    private String hardCode;
    private String commentZh;
    private int laneIndex;
    private Instant lastAssignOrderAssignTime =null;

    private CopyOnWriteArrayList<Order> waitExecuteOfOrders=new CopyOnWriteArrayList<Order>();

    void addLast(Order order){
        order.setCarLaneHardCode(hardCode);
        order.setCarLaneIndex(laneIndex);
        waitExecuteOfOrders.add(order);
    }

    public Order getOrderByIndex(int index){
        return waitExecuteOfOrders.get(index);
    }

    /**
     * 获取车道内第一个订单
     * */
    public Order getFistOrder(){
        if(waitExecuteOfOrders.size()>0){
            return waitExecuteOfOrders.get(0);
        }
        return null;
    }


    public Order getOrderByTime(Instant creattime){
        for(Order order:waitExecuteOfOrders){
            if(order.getCreate_time().equals(creattime)){
                return order;
            }
        }
        return null;
    }


    boolean modifyOrderByIndex(Order oldorder,Order neworder){
        int index=waitExecuteOfOrders.indexOf(oldorder);
        if(index<0){
            logger.error("don't find index="+index+"order");
            return false;
        }
        //已装数量比总数量大，修改失败
        if(oldorder.getAlready_amount()>neworder.getTotal_amount()){
            logger.info("已装包数："+oldorder.getAlready_amount()+" 订单总数："+neworder.getTotal_amount()+" 修改失败！");
            return false;
        }
//        waitExecuteOfOrders.set(index,neworder);no replace ,just update part properties
        oldorder.setVehicleno(neworder.getVehicleno());
        oldorder.setMaterial(neworder.getMaterial());
        oldorder.setTotal_amount(neworder.getTotal_amount());
        oldorder.setConsumer_code(neworder.getConsumer_code());
        oldorder.setBatch_no(neworder.getBatch_no());
        oldorder.setBillcode(neworder.getBillcode());
        return true;
    }

    Order deleteOrderByIndex(Order oldorder){
        int index=waitExecuteOfOrders.indexOf(oldorder);
        if(index<0){
            return null;
        }
        return waitExecuteOfOrders.remove(index);
    }


    Order canncelAssignOrderByIndex(Order oldorder){
        int index=waitExecuteOfOrders.indexOf(oldorder);
        if(index<0){
            return null;
        }
       return waitExecuteOfOrders.remove(index);
    }


    public String getHardCode() {
        return hardCode;
    }

    public void setHardCode(String hardCode) {
        this.hardCode = hardCode;
    }

    public String getCommentZh() {
        return commentZh;
    }

    public void setCommentZh(String commentZh) {
        this.commentZh = commentZh;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public CopyOnWriteArrayList<Order> getWaitExecuteOfOrders() {
        return waitExecuteOfOrders;
    }

    public int getLaneIndex() {
        return laneIndex;
    }

    public void setLaneIndex(int laneIndex) {
        this.laneIndex = laneIndex;
    }

    public Instant getLastAssignOrderAssignTime() {
        return lastAssignOrderAssignTime;
    }

    public void setLastAssignOrderAssignTime(Instant lastAssignOrderAssignTime) {
        this.lastAssignOrderAssignTime = lastAssignOrderAssignTime;
    }
}
