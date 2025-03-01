package com.sky.mapper;


import com.sky.entity.SetmealDish;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author czq
 * @version 1.0
 */
@Mapper
public interface SetmealDishMapper {
    /**
     * 根据菜品id查询套餐id
     */

    List<Long> getSetmealIdsByDishIds(@Param("dishIds") List<Long> dishIds);

    /**
     * 增加套餐和菜品之间的关系
     */
    void save(SetmealDish setmealDish);

    /**
     * 批量删除套餐和菜品之间的联系
     * @param ids
     */
    void delete(@Param("ids")  List<Long> ids);

    /**
     * 根据id查询 信息
     * @param id
     * @return
     */
    List<SetmealDish> ById(Long id);
}
