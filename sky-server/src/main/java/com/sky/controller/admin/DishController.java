package com.sky.controller.admin;


import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.entity.Dish;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.DishService;
import com.sky.vo.DishVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

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
    @Autowired
    private RedisTemplate redisTemplate;

    @PostMapping
    @ApiOperation("新增菜品")
    public Result save(@RequestBody DishDTO dishDTO) {
        log.info("新增菜品：{}", dishDTO);
        dishService.saveWithFlavor(dishDTO);

        //清理redis数据
        String key = "dish_" + dishDTO.getCategoryId();
        cleanCache(key);

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


        //清理所有菜品缓存数据
        cleanCache("dish_*");


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

        //清理所有菜品缓存数据
        cleanCache("dish_*");

        return Result.success();
    }

    /**
     * 根据id修改status状态 起售或者停售
     * @param status
     * @param id
     * @return
     */
    @PostMapping("/status/{status}")
    public Result startOrStop(@PathVariable Integer status ,Long id) {

        dishService.updateStatus(status,id);


        //清理所有菜品缓存数据
        cleanCache("dish_*");


        return Result.success();
    }


    /**
     * 根据 category id查询菜品
     */

    @GetMapping("/list")
    @ApiOperation("根据 category id查询菜品")
    public Result<List<Dish>> findAll(Long categoryId) {
        List<Dish> dishes = dishService.findCategory(categoryId);
        return Result.success(dishes);
    }

    /**
     * 清理缓存数据
     * @param pattern
     */
    private void cleanCache(String pattern){
        //清理所有菜品缓存数据
        Set keys = redisTemplate.keys(pattern);

        redisTemplate.delete(keys);
    }




}
