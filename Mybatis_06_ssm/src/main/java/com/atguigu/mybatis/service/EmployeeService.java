package com.atguigu.mybatis.service;

import com.atguigu.mybatis.bean.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeService employeeMapper;

    public List<Employee> getEmps() {
        return employeeMapper.getEmps();
    }

}
