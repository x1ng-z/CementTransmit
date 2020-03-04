package hs.dao;


import hs.modle.order.Order;
import hs.modle.order.PackManulOrder;
import org.apache.ibatis.annotations.Param;

import java.time.Instant;
import java.util.List;

/**
 * @author zzx
 * @version 1.0
 * @date 2020/3/3 8:36
 */
public interface OrderOperate {
    void insertOrder(Order order);
    List<Order> getOrders(@Param("ProductLineno") Integer ProductLineno,@Param("packIndex") Integer packIndex, @Param("meterialName")String meterialName, @Param("start_time")Instant start_time, @Param("end_time")Instant end_time, @Param("limit_weight")Double limit_weight);
    void updateOrder(Order order);
}
