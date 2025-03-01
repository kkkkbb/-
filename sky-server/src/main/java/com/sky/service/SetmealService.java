package com.sky.service;


import com.sky.dto.SetmealDTO;
import com.sky.dto.SetmealPageQueryDTO;
import com.sky.result.PageResult;

/**
 * @author czq
 * @version 1.0
 */
public interface SetmealService {
    /**
     * 套餐新增
     * @param setmealDTO
     */
    void save(SetmealDTO setmealDTO);

    /**
     * 套餐分页查询
     * @param setmealPageQueryDTO
     * @return
     */

    PageResult pageQuery(SetmealPageQueryDTO setmealPageQueryDTO);
}
