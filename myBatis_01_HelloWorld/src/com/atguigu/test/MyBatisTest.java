package com.atguigu.test;

import com.atguigu.mybatis.Employee;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;

/**
 * @ClassName MyBatisTest
 * @Description
 * @Author 12468
 * @Date 2021/12/30 0:18
 * @Version 1.0
 */
public class MyBatisTest {

    /**
     * 1、根据xml配置文件(全局配置文件)创建一个SqlSessionFactory对象
     * @throws IOException
     */
    @Test
    public void test() throws IOException {
        String resource = "mybatis-config.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);

        // 2、获取sqlSession实例能执行已经映射的sql语句
        SqlSession sqlSession = sqlSessionFactory.openSession();
        Employee employee;
        try {
            employee = sqlSession.selectOne("com.atguigu.mybatis.EmployeeMapper.selectBlog", 1);
            System.out.println("employee = " + employee);
        } finally {
            sqlSession.close();
        }
    }
}
