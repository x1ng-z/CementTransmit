package hs.modle;

import hs.modle.order.Order;

import java.util.Map;
import java.util.concurrent.*;

/**
 * @author zzx
 * @version 1.0
 * @date 2020/2/17 12:43
 */
public class PackMAchineGroup {
   private Map<Integer,ProductLine> productLineMaps=new ConcurrentHashMap<>();


    public static PackMAchineGroup build(Map<Integer,PackerConfigure> packerConfigureMap,MessageBus messageBus){
        PackMAchineGroup packMAchineGroup=new PackMAchineGroup();
        ExecutorService executor =Executors.newCachedThreadPool(new ThreadFactory(){
            @Override
            public Thread newThread(Runnable r) {
                Thread thread=new Thread(r);
                thread.setDaemon(true);
                return thread;
            }
        });
        for(Map.Entry<Integer, PackerConfigure>packerConfigureEntry:packerConfigureMap.entrySet()){
            PackerConfigure packerConfigure=packerConfigureEntry.getValue();
            if(packMAchineGroup.productLineMaps.containsKey(packerConfigure.getProductLine())){
                //已经包含了，直接添加包装机
                ProductLine existProductLine=packMAchineGroup.productLineMaps.get(packerConfigure.getProductLine());
                PackMachine newPackMachine=PackMachine.build(packerConfigure,executor,messageBus);
                existProductLine.putPackMachine(newPackMachine);
            }else {
                //含没有新建相关产线，需要先新建产线后加入
                ProductLine newProductLine=new ProductLine(packerConfigure.getProductLine());
                packMAchineGroup.productLineMaps.put(packerConfigure.getProductLine(),newProductLine);
                PackMachine newPackMachine=PackMachine.build(packerConfigure,executor,messageBus);
                newProductLine.putPackMachine(newPackMachine);
            }

        }
        return packMAchineGroup;
    }

    /**
     *订单分配
     * @location
     * */
    public boolean addSolver(Location location){
        PackMachine packMachine=productLineMaps.get(location.getProductionLine()).deviceMaps.get(location.getPackmachineIndex());
        location.getValue().setClass_no(packMachine.defaultClass+"");
        return packMachine.addOrder(location.getCarLaneIndex(),location.getValue());
    }

    public Order unassignSolver(Location location,int index){
        return productLineMaps.get(location.getProductionLine()).deviceMaps.get(location.getPackmachineIndex()).cancelAssignOrder(location.getCarLaneIndex(),index);
    }

    public boolean modifySolver(Location location,int index,Order newOrder){
        return productLineMaps.get(location.getProductionLine()).deviceMaps.get(location.getPackmachineIndex()).modifyOrder(location.getCarLaneIndex(),index,newOrder);
    }

    public boolean deleteSolver(Location location,int index){
        return productLineMaps.get(location.getProductionLine()).deviceMaps.get(location.getPackmachineIndex()).deleteOrder(location.getCarLaneIndex(),index);
    }

    public Map<Integer, ProductLine> getProductLineMaps() {
        return productLineMaps;
    }


    public static class ProductLine{
        private int id;
        private Map<Integer,PackMachine> deviceMaps;
        public ProductLine(int id) {
            this.id = id;
            this.deviceMaps =new ConcurrentHashMap<>();
        }
        public int getId() {
            return id;
        }
        public void putPackMachine(PackMachine p){
            deviceMaps.put(p.getDeviceOrder(),p);
        }

        public Map<Integer, PackMachine> getDeviceMaps() {
            return deviceMaps;
        }
    }

}
