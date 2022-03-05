package com.atguigu.mybatis.test;

import com.atguigu.mybatis.mapper.CacheMapper;
import com.atguigu.mybatis.pojo.Emp;
import com.atguigu.mybatis.utils.utils_singleFactory.Bean1;
import com.atguigu.mybatis.utils.SqlSessionUtils;
import com.atguigu.mybatis.utils.utils_singleFactory.testUtils;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;

/**
 * Date:2021/11/30
 * Author:ybc
 * Description:
 */
public class CacheMapperTest {

    /*
    1. 一级缓存的范围就是 sqlSession:
    2. 同一个mapper查询出的记录都会缓存.发现查询条件一样(调用同一方法,传入同一参数)时,就会从缓存中取出记录
        [疑问:如果查询的结果一样.不是同一方法/同一参数,会不会也使用缓存??]
    3. 同一个sqlSession创建的mapper也相互关联.在缓存中通用(mapper1的缓存,mapper2也能使用)

    使一级缓存失效的四种情况：
    1) 不同的SqlSession对应不同的一级缓存
    2) 同一个SqlSession但是查询条件不同
    3) 同一个SqlSession两次查询期间执行了任何一次增删改操作
    4) 同一个SqlSession两次查询期间手动清空了缓存
     */
    @Test
    public void testOneCache(){
        SqlSession sqlSession1 = SqlSessionUtils.getSqlSession();
        CacheMapper mapper1 = sqlSession1.getMapper(CacheMapper.class);
        Emp emp1 = mapper1.getEmpByEid(1);
        System.out.println(emp1);

        //3) 同一个SqlSession两次查询期间执行了任何一次增删改操作
        //mapper1.insertEmp(new Emp(null,"abc",23,"男","123@qq.com"));

        //4) 同一个SqlSession两次查询期间手动清空了缓存
        //sqlSession1.clearCache();

        Emp emp2 = mapper1.getEmpByEid(1);
        System.out.println(emp2);

        //2) 同一个SqlSession但是查询条件不同
        Emp emp3 = mapper1.getEmpByEid(2);
        System.out.println(emp3);

        /*  1. 一级缓存的范围就是 sqlSession: sqlSession1和2的缓存空间不通用
            1) 不同的SqlSession对应不同的一级缓存
        SqlSession sqlSession2 = SqlSessionUtils.getSqlSession();
        CacheMapper mapper2 = sqlSession2.getMapper(CacheMapper.class);
        Emp emp2 = mapper2.getEmpByEid(1);
        System.out.println(emp2);*/
    }

    @Test
    public void testTwoCache(){

        //不用 utils的原因是,在于 每一次Utils的静态方法new出的Factory就不一样.
        /*
        注意静态方法和静态变量区别:静态方法只是随着加载而执行.内部的数据对于多个对象不共享.静态变量才共享.
        */
        try {
            InputStream is = Resources.getResourceAsStream("mybatis-config.xml");
            SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(is);
            SqlSession sqlSession1 = sqlSessionFactory.openSession(true);
            CacheMapper mapper1 = sqlSession1.getMapper(CacheMapper.class);
            System.out.println(mapper1.getEmpByEid(1));
            System.out.println("sql1" + sqlSession1);
            sqlSession1.close();
            SqlSession sqlSession2 = sqlSessionFactory.openSession(true);
            CacheMapper mapper2 = sqlSession2.getMapper(CacheMapper.class);
            System.out.println(mapper2.getEmpByEid(1));
            System.out.println("sql2" + sqlSession2);
            sqlSession2.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // 改良 SqlSessionUtils 可以实现返回相同 Factory下的 sqlSession
        /*SqlSession sqlSession1 = SqlSessionUtils.getSqlSession();
        CacheMapper mapper1 = sqlSession1.getMapper(CacheMapper.class);
        System.out.println(mapper1.getEmpByEid(1));
        System.out.println("sql1" + sqlSession1);
        sqlSession1.close();
        SqlSession sqlSession2 = SqlSessionUtils.getSqlSession();
        CacheMapper mapper2 = sqlSession2.getMapper(CacheMapper.class);
        System.out.println(mapper2.getEmpByEid(1));
        System.out.println("sql2" + sqlSession2);
        sqlSession2.close();*/
    }
}
