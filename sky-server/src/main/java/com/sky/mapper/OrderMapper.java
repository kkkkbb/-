package com.sky.mapper;


import com.github.pagehelper.Page;
import com.sky.dto.GoodsSalesDTO;
import com.sky.dto.OrdersPageQueryDTO;
import com.sky.entity.OrderDetail;
import com.sky.entity.Orders;
import com.sky.vo.OrderVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

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
     * 根据订单状态和下单时间查询订单
     * @param status
     * @param orderTime
     * @return
     */


    @Select("select * from orders where status = #{status} and order_time < #{orderTime}")
    List<Orders> getByStatusAndOrder(Integer status, LocalDateTime orderTime);

    /**
     * 根据动态条件统计营业额数据
     * @param map
     * @return
     */
    Double sumByMap(Map map);

    /**
     * 动态查询订单数量
     * @param map
     * @return
     */
    Integer getOrdersByMap(Map map);

    /**
     * 统计指定时间菜品销量排名前十
     * @param map
     * @return
     */
    List<GoodsSalesDTO> findGoodsSalesDTO(LocalDateTime beginTime,LocalDateTime endTime);
}
