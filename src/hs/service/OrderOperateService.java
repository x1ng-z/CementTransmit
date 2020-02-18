package hs.service;

import hs.modle.Location;
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
@Component
public class OrderOperateService {
    @Autowired
    private UnassignOrderList unassignOrderList;
    @Autowired
    private PackMAchineGroup packMAchineGroup;
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
        Order order=packMAchineGroup.unassignSolver(location,index);
        //TODO 查询否为车道的第一张订单,如果是第一张订单需要停止包装机
        unassignOrderList.addUnsignOrder(order);
    }


    public void modifyOrderInPackList(Location location,int index,Order order){
        packMAchineGroup.modifySolver(location,index,order);
    }

    public void delectOrderInPackList(Location location,int index){
        packMAchineGroup.deleteSolver(location,index);
    }






}
