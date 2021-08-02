package com.imooc.mall.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.imooc.mall.pojo.domainobject.OrderDO;
import com.imooc.mall.pojo.po.OrderPO;

public interface OrderItemMapper extends BaseMapper<OrderPO>{
    /**
     * 插入订单对象到数据库中并将主键赋值给传入的实体类参数
     * @param orderPO 订单对象的添加
     * @return 影响的行数
     */
    int insertOrder(OrderPO orderPO);

    /**
     * 根据订单id获取订单信息
     * @param orderId 订单id
     * @return 封装有订单信息的订单视图对象
     */
    OrderDO getOrderInfo(Long orderNo);
}
