package com.imooc.mall.controller;

import com.imooc.mall.common.CommonResponse;
import com.imooc.mall.exception.OrderException;
import com.imooc.mall.exception.OrderExceptionType;
import com.imooc.mall.pojo.dto.CartDTO;
import com.imooc.mall.pojo.dto.OrderItemDTO;
import com.imooc.mall.pojo.dto.UserDTO;
import com.imooc.mall.pojo.po.OrderItemPO;
import com.imooc.mall.pojo.po.OrderPO;
import com.imooc.mall.pojo.vo.OrderItemVO;
import com.imooc.mall.pojo.vo.CartVO;
import com.imooc.mall.pojo.vo.OrderVO;
import com.imooc.mall.OrderService;
import com.imooc.mall.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName OrderController.java
 * @author admin
 * @version 1.0.0
 * @Description 订单操作对应接口
 * @createTime 2021年06月04日 18:55:00
 */
@Slf4j
@RestController
@RequestMapping("/listmode/order")
@CrossOrigin
public class OrderController {
    @Resource
    private OrderService orderService;
    @Resource
    private UserService userService;

    private static final int DEFAULT_CART_NUM = 10;

    /**
     * 添加商品到购物车
     * @param cartVO 前端传入的商品信息
     * @param userId 商品的购买用户
     * @param request HttpServletRequest 通过request对象得到session
     * @return 将得到的购物车商品信息保存到session中并传回前端保存的结果
     */
    @PostMapping("/cart/{userId}")
    public CommonResponse addCart(@RequestBody @Valid CartVO cartVO, @PathVariable("userId") Long userId, HttpServletRequest request, BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            return CommonResponse.error(new OrderException(OrderExceptionType.ORDER_ERROR,"传入的商品数据格式存在错误!"));
        }
        CartDTO cartDTO = new CartDTO();
        BeanUtils.copyProperties(cartVO, cartDTO);
        Map<String, Object> cartInfo = orderService.addCart(cartDTO);
        UserDTO existingUser = userService.getUserById(userId);
        //判断购物车信息和购买用户是否都存在
        if (cartInfo != null && existingUser != null){
            //获取session中存储的购物车信息
            Object cart = request.getSession().getAttribute("cart");
            //存储购物车信息的map
            Map<String, Object> cartMap = new HashMap<>(DEFAULT_CART_NUM);
            //存储购物车商品信息的list
            List<CartVO> goodInfo = new ArrayList<>();
            //判断session中是否存在cart
            if (cart != null){
                cartMap = (Map<String, Object>) cart;
                cartMap.put("totalPrice", (double)cartMap.get("totalPrice") + (double)cartInfo.get("totalPrice"));
                cartMap.put("userId", userId);
                goodInfo = (List<CartVO>) cartMap.get("goodInfo");
            } else {
                cartMap.put("totalPrice", cartInfo.get("totalPrice"));
                cartMap.put("userId", userId);
            }
            cartDTO = (CartDTO) cartInfo.get("goodInfo");
            BeanUtils.copyProperties(cartDTO, cartVO);
            goodInfo.add(cartVO);
            cartMap.put("goodInfo", goodInfo);
            request.getSession().setAttribute("cart", cartMap);
            return CommonResponse.success().messages("商品添加到购物车成功!").data(cartMap);
        } else {
            return CommonResponse.error(new OrderException(OrderExceptionType.ORDER_ERROR,"您的商品存在异常，添加到购物失败!"));
        }
    }

    /**
     * 清空购物车
     * @param request 清空购物车中的商品信息
     * @return 返回封装有清空结果的CommonResponse对象
     */
    @DeleteMapping("/cart")
    public CommonResponse deleteCart(HttpServletRequest request){
        HttpSession session = request.getSession();
        Object cart = session.getAttribute("cart");
        if (cart != null){
            session.removeAttribute("cart");
            return CommonResponse.success().messages("购物车清空成功!");
        } else {
            return CommonResponse.error(new OrderException(OrderExceptionType.ORDER_ERROR,"您暂未添加商品到购物车中!"));
        }
    }

    /**
     * 提交订单
     * @param address 用户填写的送达地址
     * @param request 通过request获取session得到购物车信息
     * @return 封装有订单保存结果的CommonResponse对象
     */
    @PostMapping
    public CommonResponse saveOrder(@RequestParam("address") String address, HttpServletRequest request){
        if (address == null || address.length() <= 0 || address.length() >= 200){
            return CommonResponse.error(new OrderException(OrderExceptionType.ORDER_ERROR,"您填写的地址存在错误，订单提交失败!"));
        }
        HttpSession session = request.getSession();
        //得到购物车信息
        Object cart = session.getAttribute("cart");
        if (cart != null){
            //将购物车信息对象转换为map
            Map<String, Object> cartMap = (Map<String, Object>) cart;
            //将地址信息也存放到map中
            cartMap.put("address", address);
            boolean addFlag = orderService.addOrder(cartMap);
            if (addFlag){
                //订单提交完成后清空购物车信息
                session.removeAttribute("cart");
                return CommonResponse.success().messages("订单提交成功!");
            } else {
                return CommonResponse.error(new OrderException(OrderExceptionType.ORDER_ERROR,"您的购物车存在问题，订单提交失败!"));
            }
        } else {
            return CommonResponse.error(new OrderException(OrderExceptionType.ORDER_ERROR,"您的购物车为空!"));
        }
    }

    /**
     * 根据订单id获取订单信息
     * @param orderId 需要得到的订单信息
     * @return 封装有订单相关信息的CommonResponse
     */
    @GetMapping("/{orderId}")
    public CommonResponse getOrder(@PathVariable("orderId") Long orderId){
        if (orderId != null){
            OrderDTO orderInfo = orderService.getOrderInfo(orderId);
            if (orderInfo != null){
                OrderVO orderVO = new OrderVO();
                orderVO.setAddress(orderInfo.getAddress());
                orderVO.setOrderId(orderInfo.getOrderId());
                orderVO.setPrice(orderInfo.getPrice());
                orderVO.setUserName(orderInfo.getUserName());
                orderVO.setCreationTime(orderInfo.getCreationTime());
                List<OrderItemDTO> goodInfo = orderInfo.getGoodInfo();
                List<OrderItemVO> cartRecordVOList = new ArrayList<>();
                for (int i = 0; i < goodInfo.size(); i++) {
                    OrderItemVO cartRecordVO = new OrderItemVO();
                    BeanUtils.copyProperties(goodInfo.get(i),cartRecordVO);
                    cartRecordVOList.add(cartRecordVO);
                }
                orderVO.setGoodInfo(cartRecordVOList);
                return CommonResponse.success().messages("订单信息查询成功!").data(orderVO);
            } else {
                return CommonResponse.error(new OrderException(OrderExceptionType.ORDER_ERROR,"未能查询到对应订单ID的订单信息!"));
            }
        } else {
            return CommonResponse.error(new OrderException(OrderExceptionType.ORDER_ERROR,"您未输入订单ID!"));
        }
    }
}

