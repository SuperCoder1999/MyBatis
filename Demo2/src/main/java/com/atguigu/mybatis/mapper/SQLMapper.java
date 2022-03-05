package com.atguigu.mybatis.mapper;

import com.atguigu.mybatis.pojo.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Date:2021/11/29
 * Author:ybc
 * Description: 模糊查询 / in( )中 / 表名 时,需要用到 ${}
 */
public interface SQLMapper {

    /**
     * 根据用户名模糊查询用户信息
     */
    List<User> getUserByLike(@Param("username") String username);

    /**
     * 批量删除
     */
    int deleteMore(@Param("ids") String ids);

    /**
     * 查询指定表中的数据
     */
    List<User> getUserByTableName(@Param("tableName") String tableName);

    /**
     * 添加用户信息
     */
    void insertUser(User user);

}
