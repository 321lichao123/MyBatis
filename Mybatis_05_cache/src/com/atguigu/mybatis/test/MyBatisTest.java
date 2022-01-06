package com.atguigu.mybatis.test;

import com.atguigu.mybatis.bean.Department;
import com.atguigu.mybatis.bean.Employee;
import com.atguigu.mybatis.dao.DepartmentMapper;
import com.atguigu.mybatis.dao.EmployeeMapper;
import com.atguigu.mybatis.dao.EmployeeMapperDynamicSQL;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
 *      com.atguigu.mybatis: Dao ===>>> XXMapper.xml
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

    /*
    * 两级缓存：
    *   一级缓存：（本地缓存）sqlSession级别的缓存。一级缓存是一直开启的；SqlSession级别的一个Map
    *       与数据库同一次会话期间查询到的数据会放在本地缓存中
    *       以后如果需要获取相同的数据，直接从缓存中拿，没必要再去查询数据库
    *       一级缓存失效情况（没有使用到当前一级缓存的情况，效果就是，还需要再向数据库发出查询）：
     * 		1、sqlSession不同。
     * 		2、sqlSession相同，查询条件不同.(当前一级缓存中还没有这个数据)
     * 		3、sqlSession相同，两次查询之间执行了增删改操作(这次增删改可能对当前数据有影响)
     * 		4、sqlSession相同，手动清除了一级缓存（缓存清空）
     *
    *   二级缓存：（全局缓存）：基于namespace级别的缓存：一个namespace对应一个二级缓存：
     * 		工作机制：
     * 		1、一个会话，查询一条数据，这个数据就会被放在当前会话的一级缓存中；
     * 		2、如果会话关闭；一级缓存中的数据会被保存到二级缓存中；新的会话查询信息，就可以参照二级缓存中的内容；
     * 		3、sqlSession===EmployeeMapper==>Employee
     * 						DepartmentMapper===>Department
     * 			不同namespace查出的数据会放在自己对应的缓存中（map）
     *          效果：数据会从二级缓存中获取
     * 				查出的数据都会被默认先放在一级缓存中。
     * 				只有会话提交或者关闭以后，一级缓存中的数据才会转移到二级缓存中
     * 		使用：
     * 			1）、开启全局二级缓存配置：<setting name="cacheEnabled" value="true"/>
     * 			2）、去mapper.xml中配置使用二级缓存：
     * 				<cache></cache>
     * 			3）、我们的POJO需要实现序列化接口
    * */

    @Test
    public void testFirstLevelCache() throws IOException {
        SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();
        SqlSession sqlSession = sqlSessionFactory.openSession();
        try {
            EmployeeMapper mapper = sqlSession.getMapper(EmployeeMapper.class);
            Employee employee1 = mapper.getEmpById(1);
            System.out.println(employee1);

            // 1、sqlSession不同。
            //SqlSession sqlSession2 = sqlSessionFactory.openSession();
            //EmployeeMapper mapper2 = sqlSession2.getMapper(EmployeeMapper.class);
            //Employee employee2 = mapper2.getEmpById(1);

            // 2、sqlSession相同，查询条件不同.(当前一级缓存中还没有这个数据),
            //Employee employee3 = mapper.getEmpById(3);  // 不相同
            //Employee employee2 = mapper.getEmpById(1); // 相同

            // 3、sqlSession相同，两次查询之间执行了增删改操作(这次增删改可能对当前数据有影响)
            //mapper.addEmp(new Employee(null, "testCache", "testCaCha@126.com", "0"));
            //System.out.println("添加数据");

            // 4、sqlSession相同，手动清除了一级缓存（缓存清空）
            sqlSession.clearCache();
            Employee employee2 = mapper.getEmpById(1);
            System.out.println(employee2);


            System.out.println(employee1 == employee2);

            //sqlSession2.close();

        } finally {
            sqlSession.close();
        }
    }

    @Test
    public void testSecondLevelCache() throws IOException {
        SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();

        SqlSession sqlSession1 = sqlSessionFactory.openSession();
        SqlSession sqlSession2 = sqlSessionFactory.openSession();

        EmployeeMapper mapper1 = sqlSession1.getMapper(EmployeeMapper.class);
        EmployeeMapper mapper2 = sqlSession2.getMapper(EmployeeMapper.class);

        Employee emp1 = mapper1.getEmpById(1);
        System.out.println(emp1);
        sqlSession1.close();
        Employee emp2 = mapper2.getEmpById(1);
        System.out.println(emp2);
        sqlSession2.close();
    }
}
