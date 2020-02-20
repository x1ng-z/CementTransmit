package hs.modle;

/**
 * @author zzx
 * @version 1.0
 * @date 2020/2/16 23:16
 */

import hs.modle.order.Order;
import hs.modle.order.PackManulOrder;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 *处理未分配订单
 * */
@Component
public class UnassignOrderList {

    private List<Order> unsignOrder=new CopyOnWriteArrayList<>();

    public List<Order> getAllUnassignOrders(){
        return unsignOrder;
    }

    public boolean addUnsignOrder(Order order){
        unsignOrder.add(order);
        return true;
    }

    public void  canncelOrder(int index){
        unsignOrder.remove(index);
    }

    public void modifyOrder(int index,Order newOrder){
        Order oldOrder=unsignOrder.get(index);
        oldOrder.setVehicleno(newOrder.getVehicleno());
        oldOrder.setMaterial(newOrder.getMaterial());
        oldOrder.setTotal_amount(newOrder.getTotal_amount());
//                new_order.setAlready_amount(old_order.getAlready_amount());
        oldOrder.setConsumer_code(newOrder.getConsumer_code());
        oldOrder.setBatch_no(newOrder.getBatch_no());
        oldOrder.setBillcode(newOrder.getBillcode());
    }

    public Location assignOrder(int index,int productline,int packmachine,int carline){
        Order needAssign=unsignOrder.remove(index);
        //这里我需要将单据分配到相应的车道
        Location lt=new Location();
        lt.setCarLaneIndex(carline);
        lt.setProductionLine(productline);
        lt.setPackmachineIndex(packmachine);
        lt.setValue(needAssign);
        return lt;
    }

}
