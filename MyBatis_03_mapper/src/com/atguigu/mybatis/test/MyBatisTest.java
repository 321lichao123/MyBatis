package com.atguigu.mybatis.test;

import com.atguigu.mybatis.bean.Employee;
import com.atguigu.mybatis.dao.EmployeeMapper;
import com.atguigu.mybatis.dao.EmployeeMapperAnnotation;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName MyBatisTest
 * @Description
 * @Author 12468
 * @Date 2021/12/30 0:18
 * @Version 1.0
 */

/**
 * 1、接口式编程
 *      原生：    Dao  ===>>> DaoImpl
 *      mybatis: Dao ===>>> XXMapper.xml
 * 2、sqlSession代表和数据库的一次回话；用完必须关掉
 * 3、sqlSession和connection一样都是非线程安全的。每次使用都应该去获取新对象
 * 4、mapper接口没有实现类，但是myBatis会为这个接口生成一个代理对象、
 *      前提是接口和xml进行绑定
 *      EmployeeMapper mapper = sqlSession.getMapper(EmployeeMapper.class);
 * 5、两个重要配置文件：
 *      myBatis的全局配置文件：包含数据库连接池信息、事务管理器信息等。。。系统运行环境信息
 *      sql映射文件：保存每一个sql语句信息。将sql抽取出来
 */

public class MyBatisTest {

    public SqlSessionFactory getSqlSessionFactory() throws IOException {
        String resource = "mybatis-config.xml";
        InputStream resourceAsStream = Resources.getResourceAsStream(resource);
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(resourceAsStream);
        return sqlSessionFactory;

    }

    /**
     * 1、根据xml配置文件(全局配置文件)创建一个SqlSessionFactory对象
     * 2、sql映射文件：配置了每一个sql，以及sql的封装规则
     * 3、将sql映射文件注册在全局配置文件中中
     * 4、写代码
     *      1) 根据全局配置文件得到SqlSessionFactory
     *      2) 使用SqlSession工厂获取SqlSession对象使用他来执行增删改查
     *          一个sqlSession就是一个代表和数据库的一次回话，用完关闭
     *      3) 使用sql唯一标识告诉mybatis执行那个sql。sql都是保存在sql映射文件中
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
            employee = sqlSession.selectOne("com.atguigu.dao.EmployeeMapper.selectEmp", 1);
            System.out.println("employee = " + employee);
        } finally {
            sqlSession.close();
        }
    }

    @Test
    public void testMyBatis1() throws IOException {
        // 1、获取SqlSessionFactory对象
        SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();
        // 2、获取sqlSession对象
        SqlSession sqlSession = sqlSessionFactory.openSession();
        // 3、mybatis自动创建一个代理对象，代理对象执行增删改查
        EmployeeMapper mapper = sqlSession.getMapper(EmployeeMapper.class);
        // 4、获取接口的实现类对象
        Employee employee = mapper.getEmpById(1);
        System.out.println(employee);
        sqlSession.close();
    }

    @Test
    public void test2() throws IOException {
        SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();
        SqlSession sqlSession = sqlSessionFactory.openSession();
        Employee employee;
        try {
            EmployeeMapperAnnotation mapper = sqlSession.getMapper(EmployeeMapperAnnotation.class);
            employee = mapper.getEmpById(1);
            System.out.println("employee = " + employee);
        } finally {
            sqlSession.close();
        }
    }

    /**
     * 测试增删改
     *  1、mybatis允许增删改直接定义以下类型返回值
     *      Integer、Long、Boolean、void
     *  2、需要手动提交数据
     *      sqlSessionFactory.openSession() ===》》》手动提及
     *      sqlSessionFactory.openSession(true) ===》》》自动提及
     * @throws IOException
     */
    @Test
    public void test3() throws IOException {
        SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();
        // 1、获取到sqlSession不会自动提交数据
        SqlSession sqlSession = sqlSessionFactory.openSession();

        try {
            EmployeeMapper mapper = sqlSession.getMapper(EmployeeMapper.class);
            Employee employee = new Employee(null, "lucy", "lucy@126.com", "0");
            long emp = mapper.addEmp(employee);
            System.out.println(employee.getId());
            System.out.println(emp);

            //mapper.updateEmp(new Employee(1, "jack", "jack@126.com", "1"));
            //mapper.deletedEmpById(5);
            // 2、手动提交
            sqlSession.commit();
        } finally {
            sqlSession.close();
        }
    }

    @Test
    public void test4() throws IOException {
        SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();
        // 1、获取到sqlSession不会自动提交数据
        SqlSession sqlSession = sqlSessionFactory.openSession();

        try {
            EmployeeMapper mapper = sqlSession.getMapper(EmployeeMapper.class);
            //Employee employee = mapper.getEmpByIdAndLastName(1, "jack");
            Map<String, Object> map = new HashMap<>();
            map.put("id", "1");
            map.put("lastName", "jack");
            Employee employee = mapper.getEmpMap(map);
            System.out.println(employee);
        } finally {
            sqlSession.close();
        }
    }
}
