package com.emgram.kr.dobby.dao;

import com.emgram.kr.dobby.dto.employee.Employee;
import com.emgram.kr.dobby.dto.employee.Employee.SimpleEmployeeDTO;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface EmployeeDao {
   Employee getEmployeeInfo(String employeeNo);

   List<SimpleEmployeeDTO> findAllSimpleEmployeeList();

   List<Employee> findAllEmployee();

}
