package hs.dao.service;

import hs.dao.OrderOperate;
import hs.modle.order.Order;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;

/**
 * @author zzx
 * @version 1.0
 * @date 2020/3/4 10:40
 */

@Service
public class OrderService {
    @Autowired
    private OrderOperate orderOperate;


    @Transactional(isolation = Isolation.READ_COMMITTED)
    public void  insertOrder(Order order){
        orderOperate.insertOrder(order);
    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public void updateOrder(Order order){
        orderOperate.updateOrder(order);
    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public List<Order> getOrders(Integer plno,Integer packIndex, String meterialName, Instant start_time, Instant end_time, Double limit_weight){
        return orderOperate.getOrders( plno,packIndex,  meterialName,  start_time,  end_time,  limit_weight);
    }

}
