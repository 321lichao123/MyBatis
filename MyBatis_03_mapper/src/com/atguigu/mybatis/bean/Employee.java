package com.atguigu.mybatis.bean;

import org.apache.ibatis.type.Alias;

/**
 * @ClassName Employee
 * @Description
 * @Author 12468
 * @Date 2021/12/30 0:08
 * @Version 1.0
 */
@Alias("emp")
public class Employee {
    private Integer id;
    private String lastName;
    private String email;
    private String gender;
    private Integer dId;
    private Department department;

    public Employee() {
    }

    public Employee(Integer id, String lastName, String email, String gender) {
        this.id = id;
        this.lastName = lastName;
        this.email = email;
        this.gender = gender;
    }

    public Employee(Integer id, String lastName, String email, String gender, Integer dId, Department department) {
        this.id = id;
        this.lastName = lastName;
        this.email = email;
        this.gender = gender;
        this.dId = dId;
        this.department = department;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public Integer getdId() {
        return dId;
    }

    public void setdId(Integer dId) {
        this.dId = dId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "id=" + id +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", gender='" + gender + '\'' +
                ", dId=" + dId +
                ", department=" + department +
                '}';
    }
}
