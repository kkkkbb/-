package com.sky.mapper;


import com.github.pagehelper.Page;
import com.sky.annotation.AutoFill;
import com.sky.dto.CategoryPageQueryDTO;
import com.sky.entity.Category;
import com.sky.entity.Employee;
import com.sky.enumeration.OperationType;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author czq
 * @version 1.0
 */
@Mapper
public interface CategoryMapper {


    @AutoFill(value = OperationType.INSERT)
    @Insert("insert into category (sort,category.type, category.name, category.status, category.create_time, category.update_time, category.create_user, category.update_user )" +
            "values (#{sort},#{type},#{name},#{status},#{createTime},#{updateTime},#{createUser},#{updateUser}) ")
    void save(Category category);


    Page<Category> pageQuery(CategoryPageQueryDTO categoryPageQueryDTO);


    @AutoFill(value = OperationType.UPDATE)
    void update(Category category);

    @Delete("delete from category where id = #{id}")
    void delete(Long id);

    List<Category> list(Integer type);
}
