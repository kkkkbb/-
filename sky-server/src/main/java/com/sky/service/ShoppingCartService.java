package com.sky.service;


import com.sky.dto.ShoppingCartDTO;
import com.sky.entity.ShoppingCart;

import java.util.List;

/**
 * @author czq
 * @version 1.0
 */
public interface ShoppingCartService {


    /**
     * 添加购物车
     * @param shoppingCartDTO
     */
    void add(ShoppingCartDTO shoppingCartDTO);

    /**
     * 查看购物车
     * @param userId
     * @return
     */
    List<ShoppingCart> find(Long userId);

    /**
     * 清空购物车
     */
    void cleanShoppingCart();
}
