package com.emgram.kr.dobby.dao;

import com.emgram.kr.dobby.dto.employee.Employee;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface EmployeeDao {
   Employee getEmployeeInfo(String employeeNo);

}
