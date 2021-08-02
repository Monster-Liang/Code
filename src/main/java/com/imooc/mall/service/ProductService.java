package com.imooc.mall.service;

import com.github.pagehelper.PageInfo;
import com.imooc.mall.pojo.dto.CategoryDTO;
import com.imooc.mall.pojo.po.CategoryPO;
import com.imooc.mall.pojo.po.ProductPO;
import java.util.List;

/**
 * 描述：     商品Service
 */
public interface ProductService {

    void add(AddProductReq addProductReq);

    void update(Product updateProduct);

    void delete(Integer id);

    void batchUpdateSellStatus(Integer[] ids, Integer sellStatus);


    Product detail(Integer id);

}
