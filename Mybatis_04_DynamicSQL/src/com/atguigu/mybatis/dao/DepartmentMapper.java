package com.atguigu.mybatis.dao;

import com.atguigu.mybatis.bean.Department;

import java.util.List;

public interface DepartmentMapper {
    public Department getDeptById(Integer id);

    public List<Department> getDeptByIdPlus(Integer id);

    public Department getDeptByIdStep(Integer id);
}
