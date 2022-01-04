package com.atguigu.mybatis.dao;

import com.atguigu.mybatis.bean.Employee;
import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface EmployeeMapper {

    // 多条记录封装到一个map；Map<Integer, Employee>:键是这条记录的主键，值是记录封装后的JavaBean
    @MapKey("id") // 如果向指定返回的key可以通过MapKey类指定返回map的key
    public Map<Integer, Employee> getEmpByLastNameReturnMap(String lastName);

    // 返回一条记录的map; key就是列名，值就是对应的值
    public Map<String, Object> getEmpByIdReturnMap(Integer id);

    public List<Employee> getEmpsLastNameLike(String lastName);

    public Employee getEmpMap(Map<String, Object> map);

    public Employee getEmpByIdAndLastName(@Param("id") Integer id, @Param("lastName") String lastName);

    public Employee getEmpById(Integer id);

    public long addEmp(Employee employee);

    public boolean updateEmp(Employee eMployee);

    public boolean deletedEmpById(Integer id);
}
