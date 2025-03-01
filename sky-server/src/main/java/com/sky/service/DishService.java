package com.sky.service;


import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.entity.Dish;
import com.sky.result.PageResult;
import com.sky.vo.DishVO;

import java.util.List;

/**
 * @author czq
 * @version 1.0
 */
public interface DishService {


    /**
     * 新增菜品
     * @param dishDTO
     */
    void saveWithFlavor(DishDTO dishDTO);

    /**
     * 菜品分页查询
     * @param dishPageQueryDTO
     * @return
     */
    PageResult pageQuery(DishPageQueryDTO dishPageQueryDTO);

    /**
     * 菜品批量删除
     * @param ids
     */
    void delete(List<Long> ids);

    /**
     * 根据id查询菜品回显数据
     * @param id
     * @return
     */

    DishVO selectByIdWithFlavor(Long id);

    /**
     * 修改菜品数据
     * @param dishDTO
     */
    void updateWithFlavor(DishDTO dishDTO);

    /**
     * 根据id修改status状态 起售或者停售
     * @param status
     * @param id
     */
    void updateStatus(Integer status, Integer id);

    /**
     * 根据category id查询菜品
     * @param categoryId
     * @return
     */
    List<Dish> findCategory(Long categoryId);
}
