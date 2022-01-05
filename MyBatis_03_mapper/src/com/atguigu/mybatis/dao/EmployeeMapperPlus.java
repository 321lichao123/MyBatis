package com.atguigu.mybatis.dao;

import com.atguigu.mybatis.bean.Employee;

import java.util.List;

public interface EmployeeMapperPlus {

    public Employee getEmpById(Integer id);

    public Employee getEmpAndDeptById(Integer id);

    public Employee getEmpStep(Integer id);
    public Employee getEmpStep1(Integer id);

    public List<Employee> getEmpsByDeptId(Integer deptId);
}
