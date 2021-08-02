package com.imooc.mall.service;



import com.imooc.mall.pojo.dto.CartDTO;
import com.imooc.mall.pojo.po.OrderPO;

import java.util.Map;

public interface OrderService {

    Map<String, Object> addCart(CartDTO cartDTO);


    boolean addOrder(Map<String, Object> cartMap);


    OrderDTO getOrderInfo(Long orderNo);
}



