package com.sky.mapper;


import com.github.pagehelper.Page;
import com.sky.dto.OrdersPageQueryDTO;
import com.sky.entity.OrderDetail;
import com.sky.entity.Orders;
import com.sky.vo.OrderVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * @author czq
 * @version 1.0
 */
@Mapper
public interface OrderMapper {

    /**
     * 插入订单
     * @param order
     */
    void insert(Orders order);

    /**
     * 分页查询订单
     * @param ordersPageQueryDTO
     * @return
     */
    Page<Orders> findAll(OrdersPageQueryDTO ordersPageQueryDTO);

    /**
     * 查看订单详情
     * @param id
     * @return
     */
    OrderVO findOrder(Long id);
    /**
     * 接单
     * @param orders
     */
    void update(Orders orders);

    /**
     * 各个状态的订单数量统计
     * @param orders
     * @return
     */
    Integer count(Orders orders);

    /**
     * 用户查询订单
     * @param ordersPageQueryDTO
     * @return
     */
    Page<Orders> pageQuery(OrdersPageQueryDTO ordersPageQueryDTO);

    /**
     * 根据id查询订单
     * @param id
     */
    @Select("select * from orders where id=#{id}")
    Orders getById(Long id);

    /**
     * 根据订单id查询订单明细
     * @param orderId
     * @return
     */


}
