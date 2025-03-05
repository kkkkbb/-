package com.sky.mapper;

import com.github.pagehelper.Page;
import com.sky.annotation.AutoFill;
import com.sky.dto.DishPageQueryDTO;
import com.sky.entity.Dish;
import com.sky.enumeration.OperationType;
import com.sky.vo.DishVO;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

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

    /**
     * 菜品表更新数据根据id
     * @param dish
     */
    @AutoFill(OperationType.UPDATE)
    void update(Dish dish);

    /**
     *根据id修改status状态 起售或者停售
     * @param status
     * @param id
     */
    @Update("update dish set status = #{status} where id = #{id}")
    void updateStatus(@Param("status") Integer status, @Param("id") Long id);

    /**
     * 根据category id
     *查询菜品
     *
     * @return
     */
    List<Dish> selectALl(@Param("dish") Dish dish);

    /**
     * 将dish和setmeal——dish表连接根据setmeal id来查询是否存在dish已经停售
     * @param id
     * @return
     */
    List<Dish> selectWithSetMealDishById(Long id);
    /**
     * 根据条件统计菜品数量
     * @param map
     * @return
     */
    Integer countByMap(Map map);
}
