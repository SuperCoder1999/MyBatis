package com.atguigu.mybatis.test;

import com.atguigu.mybatis.mapper.ParameterMapper;
import com.atguigu.mybatis.pojo.User;
import com.atguigu.mybatis.utils.SqlSessionUtils;
import org.apache.ibatis.session.SqlSession;
import org.junit.Test;

import java.sql.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Date:2021/11/27
 * Author:ybc
 * Description:
 */
public class ParameterMapperTest {

    /**
     * MyBatis获取参数值的两种方式：${}和#{}
     * ${}本质字符串拼接(和jdbc的获取参数对比)
     * #{}本质占位符赋值
     *
     * MyBatis获取参数值的各种情况：(主要用@Param>5/实例>4两种情况[单个参数也能用@Param])
     * 1、mapper接口方法的参数为单个的字面量类型
     * 可以通过${}和#{}以任意的名称获取参数值，但是需要注意${}的单引号问题
     * (传入字符串/时间,需要手动加,在mapper.xml中添加就可以了)
     * 2、mapper接口方法的参数为多个时
     * 此时MyBatis会将这些参数放在一个map集合中，以两种方式进行存储
     * a>以arg0,arg1...为键，以参数为值
     * b>以param1,param2...为键，以参数为值
     * 因此只需要通过#{}和${}以键的方式访问值即可，但是需要注意${}的单引号问题
     * 3、若mapper接口方法的参数有多个时，可以手动将这些参数放在一个map中存储
     * 只需要通过#{}和${}以键(键名是存放时的键名)的方式访问值即可，但是需要注意${}的单引号问题
     * 4、mapper接口方法的参数是实体类类型的参数
     * 只需要通过#{}和${}以属性的方式访问属性值(属性值指的是set/getXXX方法)即可，但是需要注意${}的单引号问题
     * 5、使用@Param注解命名参数
     * 此时MyBatis会将这些参数放在一个map集合中，以两种方式进行存储(方式2/3的结合)
     * a>以@Param注解的值为键名，以参数为值
     * b>以param1,param2...为键名，以参数为值(没有 arg0/arg1)
     * 因此只需要通过#{}和${}以键的方式访问值即可，但是需要注意${}的单引号问题
     * 6.获取 集合  : 简便方式: 用@Param 自定义集合名. 集合名[] 调用每个元素.
     */

    @Test
    public void testCheckLoginByParam(){
        SqlSession sqlSession = SqlSessionUtils.getSqlSession();
        ParameterMapper mapper = sqlSession.getMapper(ParameterMapper.class);
        User user = mapper.checkLoginByParam("admin", "123456");
        System.out.println(user);
    }

    @Test
    public void testInsertUser(){
        SqlSession sqlSession = SqlSessionUtils.getSqlSession();
        ParameterMapper mapper = sqlSession.getMapper(ParameterMapper.class);
        int result = mapper.insertUser(new User(null, "李四", "123", 23, "男", "123@qq.com"));
        System.out.println(result);
    }

    @Test
    public void testCheckLoginByMap(){
        SqlSession sqlSession = SqlSessionUtils.getSqlSession();
        ParameterMapper mapper = sqlSession.getMapper(ParameterMapper.class);
        Map<String, Object> map = new HashMap<>();
        map.put("username", "admin");
        map.put("password", "123456");
        User user = mapper.checkLoginByMap(map);
        System.out.println(user);
    }

    @Test
    public void testCheckLogin(){
        SqlSession sqlSession = SqlSessionUtils.getSqlSession();
        ParameterMapper mapper = sqlSession.getMapper(ParameterMapper.class);
        User user = mapper.checkLogin("admin", "123456");
        System.out.println(user);
    }

    @Test
    public void testGetUserByUsername(){
        SqlSession sqlSession = SqlSessionUtils.getSqlSession();
        ParameterMapper mapper = sqlSession.getMapper(ParameterMapper.class);
        User user = mapper.getUserByUsername("admin");
        System.out.println(user);
    }

    @Test
    public void testGetAllUser(){
        SqlSession sqlSession = SqlSessionUtils.getSqlSession();
        ParameterMapper mapper = sqlSession.getMapper(ParameterMapper.class);
        List<User> list = mapper.getAllUser();
        list.forEach(user -> System.out.println(user));
    }

    @Test
    public void testJDBC() throws Exception {

        String id = "1";
        //Class.forName("");
        //Connection connection = DriverManager.getConnection("", "", "");
        //连接数据库
        Class.forName("com.mysql.jdbc.Driver");
        Connection connection = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/mybatis?user=root&password=N331150871");

        //jdbc用拼接sql 获取参数
        Statement statement = connection.createStatement();
        String sql = "select * from t_user where id = '" + id + "'";
        ResultSet resultSet = statement.executeQuery(sql);

        //jdbc用占位符 获取参数
        //PreparedStatement ps = connection.prepareStatement("select * from t_user where id = ?");
        //ps.setString(1, id);
        //ResultSet resultSet = ps.executeQuery();


        while (resultSet.next()) {
            int res_id = resultSet.getInt("id");
            String name = resultSet.getString("username");
            String sex = resultSet.getString("sex");
            System.out.println(id + "\t" + name + "\t" + sex);
        }
    }
}
