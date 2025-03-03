package com.sky.mapper;


import com.sky.dto.ShoppingCartDTO;
import com.sky.entity.ShoppingCart;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * @author czq
 * @version 1.0
 */
@Mapper

public interface ShoppingCartMapper {

    /**
     * 动态查询购物车数据
     */
    List<ShoppingCart> list(ShoppingCart shoppingCart);

    /**
     * 将购物车商品数量加一
     * @param shoppingCart1
     */
    @Update("update shopping_cart set number = #{number} where id = #{id}")
    void updateNumberById(ShoppingCart shoppingCart1);

    /**
     * 插入购物车
     * @param shoppingCart
     */
    @Insert("insert into shopping_cart (name, image, user_id, dish_id, setmeal_id, dish_flavor, amount, create_time,number) " +
            "VALUES " +
            "(#{name},#{image},#{userId},#{dishId},#{setmealId},#{dishFlavor},#{amount},#{createTime},#{number})")
    void insert(ShoppingCart shoppingCart);
}

