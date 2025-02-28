package com.sky.mapper;


import com.github.pagehelper.Page;
import com.sky.dto.CategoryPageQueryDTO;
import com.sky.entity.Category;
import com.sky.entity.Employee;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author czq
 * @version 1.0
 */
@Mapper
public interface CategoryMapper {


    @Insert("insert into category (sort,category.type, category.name, category.status, category.create_time, category.update_time, category.create_user, category.update_user )" +
            "values (#{sort},#{type},#{name},#{status},#{createTime},#{updateTime},#{createUser},#{updateUser}) ")
    void save(Category category);

    Page<Category> pageQuery(CategoryPageQueryDTO categoryPageQueryDTO);

    void update(Category category);
}
