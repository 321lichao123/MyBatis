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

    @Test
    public void test01() throws IOException {
        SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();
        SqlSession sqlSession = sqlSessionFactory.openSession();
        try {
            DepartmentMapper mapper = sqlSession.getMapper(DepartmentMapper.class);
            //List<Department> dept = mapper.getDeptByIdPlus(2);
            Department dept = mapper.getDeptByIdStep(2);
            System.out.println(dept);
            System.out.println(dept.getEmps());
        } finally {
            sqlSession.close();
        }
    }

    @Test
    public void test02() throws IOException {
        SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();
        SqlSession sqlSession = sqlSessionFactory.openSession();
        try {
            EmployeeMapperDynamicSQL mapper = sqlSession.getMapper(EmployeeMapperDynamicSQL.class);
            Employee employee = new Employee(2, "lucy", null, null);
            //List<Employee> emps = mapper.getEmpsByConditionIf(employee);
            //System.out.println(emps);

            /*
                查询的时候如果某些条件没带可能导致sql的拼装出问题
                    1、给where后面添加1=1，以后的条件都and xxx.
                    2、mybatis使用where标签来将所有的查询条件包括在内。
                        mybatis就会将where标签中拼装的sql，多出来的and或者or去除
                        注意：where指挥去掉第一个多出来的and或者or
            */
            /*List<Employee> emps = mapper.getEmpsByConditionTrim(employee);
            System.out.println(emps);*/
            /*List<Employee> emps = mapper.getEmpsByConditionChoose(employee);
            for (Employee emp : emps) {
                System.out.println(emp);
            }*/

            /*mapper.updateEmp(employee);
            sqlSession.commit();*/
            List<Employee> emps = mapper.getEmpsByConditionForEach(Arrays.asList(1, 2, 3, 4));
            for (Employee emp : emps) {
                System.out.println(emp);
            }
        } finally {
            sqlSession.close();
        }
    }

    @Test
    public void testBatchSave() throws IOException {
        SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();
        SqlSession sqlSession = sqlSessionFactory.openSession();
        try {
            EmployeeMapperDynamicSQL mapper = sqlSession.getMapper(EmployeeMapperDynamicSQL.class);
            List<Employee> emps = new ArrayList<>();
            emps.add(new Employee(null, "张三", "张三@126.com", "1", 1));
            emps.add(new Employee(null, "李四", "李四@126.com", "1", 2));
            emps.add(new Employee(null, "王五", "王五@126.com", "1", 3));
            mapper.addEmps(emps);
            sqlSession.commit();
        } finally {
            sqlSession.close();
        }
    }
}
