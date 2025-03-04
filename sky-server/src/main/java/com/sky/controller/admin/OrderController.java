package com.sky.controller.admin;


import com.sky.dto.OrdersDTO;
import com.sky.dto.OrdersPageQueryDTO;
import com.sky.entity.Orders;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.OrderService;
import com.sky.vo.OrderOverViewVO;
import com.sky.vo.OrderSubmitVO;
import com.sky.vo.OrderVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author czq
 * @version 1.0
 */
@RestController("adminOrderController")
@RequestMapping("/admin/order")
@Api(tags = "订单管理")
@Slf4j
public class OrderController {
    @Autowired
    private OrderService orderService;

    @GetMapping("/details/{id}")
    @ApiOperation("查询订单详细")
    public Result<OrderVO> select(@PathVariable Long id){

        OrderVO orderVO = orderService.findOrder(id);
        return Result.success(orderVO);
    }

    @GetMapping("/conditionSearch")
    @ApiOperation("订单搜索")
    public Result<PageResult> findAll(OrdersPageQueryDTO ordersPageQueryDTO){
        log.info("订单搜索开始：{}",ordersPageQueryDTO);
        PageResult pageResult = orderService.findAll(ordersPageQueryDTO);

        return Result.success(pageResult);
    }

    @PutMapping("/confirm")
    @ApiOperation("接单")
    public Result getOrder(@RequestBody OrdersDTO ordersDTO){
        log.info("接单：{}",ordersDTO);
        Long id = ordersDTO.getId();
        orderService.update(id);
        return Result.success();
    }

    @PutMapping("/rejection")
    @ApiOperation("拒单")
    public Result refuseOrder(@RequestBody Orders orders){
        log.info("拒单：{}",orders);
        orderService.refuseOrder(orders);
        return Result.success();
    }

    @PutMapping("/delivery/{id}")
    @ApiOperation("派送订单")
    public Result DispatchOrder(@PathVariable Long id){
        log.info("派送订单：{}",id);
        orderService.DispatchOrder(id);
        return Result.success();
    }

    @PutMapping("/complete/{id}")
    @ApiOperation("完成订单")
    public Result endOrder(@PathVariable Long id){
        log.info("完成订单：{}",id);
        orderService.endOrder(id);
        return Result.success();
    }

    @PutMapping("/cancel")
    @ApiOperation("取消订单")
    public Result cancelOrder(@RequestBody Orders orders){
        log.info("取消订单");
        orderService.cancelOrder(orders);
        return Result.success();
    }
}
