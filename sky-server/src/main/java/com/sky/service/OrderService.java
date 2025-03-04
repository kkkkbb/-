package com.sky.service;


import com.sky.dto.OrdersDTO;
import com.sky.dto.OrdersPageQueryDTO;
import com.sky.dto.OrdersSubmitDTO;
import com.sky.entity.Orders;
import com.sky.result.PageResult;
import com.sky.vo.OrderSubmitVO;
import com.sky.vo.OrderVO;

/**
 * @author czq
 * @version 1.0
 */
public interface OrderService {
    /**
     * 用户下单
     * @param ordersSubmitDTO
     * @return
     */
    OrderSubmitVO submitOrder(OrdersSubmitDTO ordersSubmitDTO);

    /**
     * 分页查询订单
     * @param ordersPageQueryDTO
     * @return
     */
    PageResult findAll(OrdersPageQueryDTO ordersPageQueryDTO);

    /**
     * c查看订单详情
     * @param id
     * @return
     */
    OrderVO findOrder(Long id);

    /**
     * 接单
     * @param id
     */
    void update(Long id);

    /**
     * 拒单
     * @param orders
     */
    void refuseOrder(Orders orders);

    /**
     * 派送订单
     * @param id
     */
    void DispatchOrder(Long id);
}
