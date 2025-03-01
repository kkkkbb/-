package com.sky.mapper;

import com.github.pagehelper.Page;
import com.sky.annotation.AutoFill;
import com.sky.dto.DishPageQueryDTO;
import com.sky.entity.Dish;
import com.sky.enumeration.OperationType;
import com.sky.vo.DishVO;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface DishMapper {

    /**
     * 根据分类id查询菜品数量
     * @param categoryId
     * @return
     */
    @Select("select count(id) from dish where category_id = #{categoryId}")
    Integer countByCategoryId(Long categoryId);


    @AutoFill(OperationType.INSERT)
    void insert(Dish dish);


    Page<DishVO> pageQuery(DishPageQueryDTO dishPageQueryDTO);

    /**
     * 根据id删除菜品数据
     * @param id
     */
    @Delete("delete from dish where id =#{id}")
    void deleteById(Long id);

    /**
     * 根据菜品id集合删除菜品
     * @param ids
     */
    void deleteByIds(@Param("ids") List<Long> ids);

    /**
     * 根据菜品id查询数据回显
     * @param id
     * @return
     */
    @Select("select * from  dish where id = #{id}")
    DishVO selectById(Long id);
}
