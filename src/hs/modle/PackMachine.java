package hs.modle;

/**
 * @author zzx
 * @version 1.0
 * @date 2020/2/16 17:34
 */


import hs.modle.order.Order;
import hs.service.Command;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;

/**
 * 包装机类，包含车道订单分配、订单修改、订单删除、订单取消分配
 * 用pipe来做初始化车道
 */

public class PackMachine implements Runnable {
    public static Logger logger = Logger.getLogger(PackMachine.class);

    private LanePipe lanePipe;
    private PackerConfigure packerConfigure;
    private DefaultLaneContext currentLaneContext = null;
    private Order currentExecuteOrder = null;

    private Executor executor;
    private int deviceOrder;

    @Autowired
    private MessageBus messageBus;

    public PackMachine(PackerConfigure packerConfigure) {
        this.packerConfigure = packerConfigure;
        this.deviceOrder = packerConfigure.getDeviceOrder();
    }


    /**
     *订单获取方法
     * */

    /**
     * 获取所有订单,这里需要经过排序，大致为将有订单的车道按照第一条订单的创建时间
     * 进行排序，最后按照这个顺序，将所有车道内的订单按照按照车道顺序交叉插入到列表中
     */
    java.util.List<Order> getAllSortOrder() {
        List<Order> list = new ArrayList<Order>();
        CarLane[] carLaneSortByFirstOrder=new CarLane [packerConfigure.getCarLanes().size()];
        int j=0;
        int maxCountOneLane=0;
        for (int i=0 ;i<packerConfigure.getCarLanes().size();i++){
            //车道内是否有订单
            if(packerConfigure.getCarLanes().get(i).getWaitExecuteOfOrders().size()!=0){
                carLaneSortByFirstOrder[j]=packerConfigure.getCarLanes().get(i);
                if(maxCountOneLane<carLaneSortByFirstOrder[j].getWaitExecuteOfOrders().size()){
                    maxCountOneLane=carLaneSortByFirstOrder[j].getWaitExecuteOfOrders().size();
                }
                //加入的车道数量是否大于2
                if(j>=2){
                    //判断当前车道的第一个订单常见时间是否小于
                    int lanecount=j;
                    while(lanecount>0){
                        if(carLaneSortByFirstOrder[lanecount].getWaitExecuteOfOrders().get(0).getCreate_time().isBefore(carLaneSortByFirstOrder[lanecount-1].getWaitExecuteOfOrders().get(0).getCreate_time())){
                            CarLane exchange;
                            exchange =carLaneSortByFirstOrder[lanecount];
                            carLaneSortByFirstOrder[lanecount]=carLaneSortByFirstOrder[lanecount-1];
                            carLaneSortByFirstOrder[lanecount-1]=exchange;
                            lanecount--;
                        }else {
                            break;
                        }
                    }

                }
                j++;
            }

        }



        for(int i=0;i<maxCountOneLane;i--){
            for(CarLane carLane:carLaneSortByFirstOrder){
                if(i<carLane.getWaitExecuteOfOrders().size()){
                    list.add(carLane.getWaitExecuteOfOrders().get(i));
                }
            }
        }

        return list;
    }

    /**
     * 获取当前正在执行的订单
     */
    public Order getCurrentExecuteOrder() {
        return currentExecuteOrder;
    }


    /***
     * 以下方法为UI操作方法
     * */

    /**
     * 未分配列表的订单，分配至相应包装机的车道
     * */
    public boolean addOrder(int laneindex, Order order) {
        order.setAssign_time(Instant.now());
        order.setProductLineIndex(packerConfigure.getProductLine());
        DefaultLaneContext checkctx = lanePipe.head.next;
        while (checkctx != lanePipe.tail) {
            if (checkctx.getLane().getLaneIndex() == (laneindex)) {
                checkctx.getLane().addLast(order);
                return true;
            }
            checkctx = checkctx.next;
        }
        return false;
    }


    boolean modifyOrder(int laneorder, int index, Order neworder) {
        DefaultLaneContext checkctx = lanePipe.head.next;
        while (checkctx != lanePipe.tail) {
            if (checkctx.getLane().getLaneIndex() == (laneorder)) {
                checkctx.getLane().modifyOrderByIndex(getAllSortOrder().get(index), neworder);
                return true;
            }
            checkctx = checkctx.next;
        }
        return false;
    }


    public boolean deleteOrder(int laneorder, int index) {
        DefaultLaneContext checkctx = lanePipe.head.next;
        while (checkctx != lanePipe.tail) {
            if (checkctx.getLane().getLaneIndex() == (laneorder)) {
                checkctx.getLane().deleteOrderByIndex(getAllSortOrder().get(index));
                return true;
            }
            checkctx = checkctx.next;
        }
        return false;
    }

    public Order cancelAssignOrder(int laneorder, int index) {
        DefaultLaneContext checkctx = lanePipe.head.next;
        while (checkctx != lanePipe.tail) {
            if (checkctx.getLane().getLaneIndex() == (laneorder)) {
                return checkctx.getLane().canncelAssignOrderByIndex(getAllSortOrder().get(index));
            }
            checkctx = checkctx.next;
        }
        return null;
    }

    /**
     * 包装机构建类
     */
    public static PackMachine build(PackerConfigure packerConfigure) {
        PackMachine packMachine = new PackMachine(packerConfigure);
        packMachine.lanePipe = new LanePipe();
        for (CarLane carLane : packerConfigure.getCarLanes()) {
            packMachine.lanePipe.addLast(carLane.getCommentZh(), carLane);
        }
        return packMachine;
    }


    /**
     * 判断是否存在需要下发的订单,这种方式必须要求两个车道一边装一次
     */

    //ToDO  把两个车道的首个订单，找出来，然后准备同于下发；
    public void checkNeedExecuteOrderByRecycle() {

        /**
         *包装机定位包装机第一个订单，从该车道开始查找第一个订单数据
         */
        List<Order> allOrders = getAllSortOrder();
        DefaultLaneContext checkContextstart = null;
        DefaultLaneContext checklocal = null;
        //无订单
        if (allOrders.size() == 0) {
            return;
        }
        //存在订单并获取第一个订单
        for (CarLane carLane : packerConfigure.getCarLanes()) {
            //判断车道内的第一个订单是否未最新加入的订单，如果是最新加入的则定位该车道上下文
            if (carLane.getWaitExecuteOfOrders().indexOf(allOrders.get(0)) >= 0) {
                DefaultLaneContext checkContextstart0 = lanePipe.head.next;
                while (checkContextstart0 != lanePipe.tail) {
                    if (checkContextstart0.getLane().equals(carLane)) {
                        checkContextstart = checkContextstart0;
                        break;
                    }
                    checkContextstart0 = checkContextstart0.next;
                }
            }
        }

        if (checkContextstart != null) {
            //检测每一个车道的第一单是否需要下发
            checklocal = checkContextstart;
            do {
                Order waitExecuteOrder = checkContextstart.getLane().getFistOrder();
                if (waitExecuteOrder != null && (!waitExecuteOrder.getCreate_time().equals(checkContextstart.getLane().getLastAssignOrderAssignTime()))) {
                    Message message = new Message();
                    message.setContext(Command.SEND_ORDER.build(waitExecuteOrder));
                    message.setSendTo(packerConfigure.getDeviceIp());
                    messageBus.putMessage(message);
                    //update
                    checkContextstart.getLane().setLastAssignOrderAssignTime(waitExecuteOrder.getCreate_time());
                }
                checkContextstart = checkContextstart.next;
                if (checkContextstart.equals(lanePipe.tail)) {
                    checkContextstart = lanePipe.head.next;
                }
            } while (!checklocal.equals(checkContextstart));
        }
    }

    @Override
    public void run() {
        Thread.currentThread().setDaemon(true);
        while (!Thread.currentThread().isInterrupted()) {
            try {
                checkNeedExecuteOrderByRecycle();
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                logger.error(e);
                Thread.currentThread().interrupt();
            }
        }

    }

    public int getDeviceOrder() {
        return deviceOrder;
    }


}
