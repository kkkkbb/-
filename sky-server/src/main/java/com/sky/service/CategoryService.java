package com.sky.service;


/**
 * @author czq
 * @version 1.0
 */

import com.sky.dto.CategoryDTO;
import com.sky.dto.CategoryPageQueryDTO;
import com.sky.result.PageResult;

public interface CategoryService {


    /**
     * 新增套餐
     * @param categorydto
     */
    void save(CategoryDTO categorydto);

    /**
     * 分类分页查询
     * @param categoryPageQueryDTO
     * @return
     */
    PageResult pageQuery(CategoryPageQueryDTO categoryPageQueryDTO);
}
