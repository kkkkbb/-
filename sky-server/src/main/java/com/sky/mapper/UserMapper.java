package com.sky.mapper;


import com.sky.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.Map;

/**
 * @author czq
 * @version 1.0
 */
@Mapper
public interface UserMapper {


    /**
     * 根据openid查询user是否存在                                                                                           
     * @param openid
     * @return
     */
    @Select("select * from user where openid = #{openid}")
    User getByOpenId (String openid);

    /**
     * 插入数据
     * @param user
     */
    void save(User user);

    /**
     * 动态统计用户数量
     */
    Integer countByMap(Map map);
}
