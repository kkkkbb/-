package com.sky.service;


/**
 * @author czq
 * @version 1.0
 */

import com.sky.dto.CategoryDTO;

public interface CategoryService {


    /**
     * 新增套餐
     * @param categorydto
     */
    void save(CategoryDTO categorydto);
}
