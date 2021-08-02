package com.imooc.mall.controller;


import com.imooc.mall.pojo.vo.CartVO;
import com.imooc.mall.pojo.dto.CartDTO;
import com.imooc.mall.common.CommonResponse;
import com.imooc.mall.service.ProductService;
import com.imooc.mall.exception.derExceptionType;
import com.imooc.mall.exception.OrderException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;

/**
 * @Description 商品操作对应接口
 */
@Slf4j
@RestController
@RequestMapping("/mall/good")
@CrossOrigin
public class CartController {
    @Resource
    private GoodService goodService;

    /**
     * 商品的添加
     */
    @PostMapping
    public CommonResponse saveGood(@RequestBody @Valid CartVO cartVO, BindingResult bindingResult){
        if (cartVO != null){
            if (bindingResult.hasErrors()){
                return CommonResponse.error(new OrderException(OrderExceptionType.USER_INPUT_ERROR)).messages("传入的数据格式存在错误!");
            }
            CartDTO goodDTO = new cartDTO();
            BeanUtils.copyProperties(cartVO, cartDTO);
            Boolean addFlag = goodService.add(cartDTO);
            if (addFlag) {
                return CommonResponse.success().messages("商品添加成功!");
            } else {
                return CommonResponse.error(new OrderException(OrderExceptionType.USER_INPUT_ERROR)).messages("未能成功进行添加!");
            }
        } else {
            throw new OrderException(OrderExceptionType.USER_INPUT_ERROR);
        }
    }

    /**
     * 商品信息的修改
     */
    @PutMapping
    public CommonResponse updateGood(@RequestBody @Valid CartVO cartVO, BindingResult bindingResult){
        if (cartVO != null){
            if (bindingResult.hasErrors()){
                return CommonResponse.error(new OrderException(OrderExceptionType.USER_INPUT_ERROR)).messages("传入的数据格式存在错误!");
            }
            CartDTO goodDTO = new CartDTO();
            BeanUtils.copyProperties(cartVO, cartDTO);
            Boolean updateFlag = goodService.update(cartDTO);
            if (updateFlag) {
                return CommonResponse.success().messages("商品修改成功!");
            } else {
                return CommonResponse.error(new OrderException(OrderExceptionType.USER_INPUT_ERROR)).messages("未能成功进行修改!");
            }
        } else {
            throw new OrderException(OrderExceptionType.USER_INPUT_ERROR);
        }
    }

    /**
     * 商品的删除
     */
    @DeleteMapping("/{id}")
    public CommonResponse deleteGood(@PathVariable("id") Long id){
        if (id != null){
            Boolean removeFlag = goodService.remove(id);
            if (removeFlag){
                return CommonResponse.success().messages("商品删除成功!");
            } else {
                return CommonResponse.error(new OrderException(OrderExceptionType.USER_INPUT_ERROR)).messages("商品删除失败!");
            }
        } else {
            throw new OrderException(OrderExceptionType.USER_INPUT_ERROR);
        }
    }

    /**
     * 商品信息的查询
     */
    @GetMapping("/{id}")
    public CommonResponse getGoodById(@PathVariable("id") Long id){
        if (id != null) {
            CartDTO cartDTO = ProductService.Service.queryById(id);
            if (cartDTO != null){
                CartVO cartVO = new CartVO();
                BeanUtils.copyProperties(cartDTO, cartVO);
                return CommonResponse.success().messages("商品信息查询成功!").data(cartVO);
            } else {
                return CommonResponse.error(new OrderException(OrderExceptionType.USER_INPUT_ERROR)).messages("商品信息查询失败!");
            }
        } else {
            throw new OrderException(OrderExceptionType.USER_INPUT_ERROR);
        }
    }
}

