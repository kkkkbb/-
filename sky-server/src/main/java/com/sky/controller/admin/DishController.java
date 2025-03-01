package com.sky.controller.admin;


import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.DishService;
import com.sky.vo.DishVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author czq
 * @version 1.0
 */
@RestController
@RequestMapping("/admin/dish")
@Slf4j
@Api(tags = "菜品相关")
public class DishController {

    @Autowired
    private DishService dishService;

    @PostMapping
    @ApiOperation("新增菜品")
    public Result save(@RequestBody DishDTO dishDTO) {
        log.info("新增菜品：{}", dishDTO);
        dishService.saveWithFlavor(dishDTO);

        return Result.success();
    }


    @GetMapping("/page")
    @ApiOperation("菜品分页查询")
    public Result<PageResult> page(DishPageQueryDTO dishPageQueryDTO) {
        log.info("菜品分页查询{}", dishPageQueryDTO);
        PageResult pageResult =  dishService.pageQuery(dishPageQueryDTO);

        return Result.success(pageResult);
    }
    /**
     * 批量删除菜品
     */
    @DeleteMapping
    @ApiOperation("菜品批量删除")
    public Result delete(@RequestParam List<Long> ids) //用注解进行类型转换
    {
        log.info("菜品批量删除：{}",ids);
        dishService.delete(ids);


        return Result.success();
    }


    @GetMapping("/{id}")
    @ApiOperation("根据id查询菜品回显数据")
    public Result<DishVO> findById(@PathVariable Long id) {
        log.info("菜品数据回显：{}",id);
        DishVO dishVO = dishService.selectByIdWithFlavor(id);

        return Result.success(dishVO);
    }

    @PutMapping
    @ApiOperation("根据id修改菜品数据")
    public Result update(@RequestBody DishDTO dishDTO) {

        log.info("修改菜品数据{}", dishDTO);
        dishService.updateWithFlavor(dishDTO);


        return Result.success();
    }

    /**
     * 根据id修改status状态 起售或者停售
     * @param status
     * @param id
     * @return
     */
    @PostMapping("/status/{status}")
    public Result startOrStop(@PathVariable Integer status ,Integer id) {

        dishService.updateStatus(status,id);
        return Result.success();
    }




































}
