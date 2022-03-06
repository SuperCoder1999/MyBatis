package com.atguigu.mybatis.test;

import com.atguigu.mybatis.mapper.EmpMapper;
import com.atguigu.mybatis.pojo.Emp;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * Date:2021/11/30
 * Author:ybc
 * Description:
 */
public class PageHelperTest {

    /**
     * limit index,pageSize
     * index:当前页的起始索引
     * pageSize：每页显示的条数
     * pageNum：当前页的页码
     * index=(pageNum-1)*pageSize
     *
     * 使用MyBatis的分页插件实现分页功能：
     * 1、需要在查询功能之前开启分页
     * PageHelper.startPage(int pageNum, int pageSize);
     * 2、在查询功能之后获取分页相关信息
     * PageInfo<Emp> page = new PageInfo<>(list, 5);
     * list表示分页数据
     * 5表示当前导航分页的数量
     */

    @Test
    public void testPageHelper(){
        try {
            InputStream is = Resources.getResourceAsStream("mybatis-config.xml");
            SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(is);
            SqlSession sqlSession = sqlSessionFactory.openSession(true);
            EmpMapper mapper = sqlSession.getMapper(EmpMapper.class);

            //在查询之前开启分页功能,在为查询之前,page1只有
            //内容:Page{count=true, pageNum=2, pageSize=4, startRow=4, endRow=8, total=0, pages=0, reasonable=null, pageSizeZero=null}[]
            //直到查询操作完成后.page1才被赋值查询的记录集合
            // (疑问:为什么开启分页功能会影响查询?未来的查询都会变成分页的?)
            /*Page<Object> page1 = PageHelper.startPage(2, 4);
            System.out.println(page1);
            List<Emp> list = mapper.selectByExample(null);
            for (Emp emp : list) { System.out.println(emp);
            }*/

            //获取详细的查询信息
            PageHelper.startPage(2, 4);
            List<Emp> list2 = mapper.selectByExample(null);
            PageInfo<Emp> pageInfo = new PageInfo<Emp>(list2, 3);//navigatePages:导航页数,即导航栏显示几个页码供用户点击
            System.out.println(pageInfo);
            for (Emp emp : list2) { System.out.println(emp);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
