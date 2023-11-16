package com.emgram.kr.dobby.dao;

import com.emgram.kr.dobby.dto.employee.Employee;
import com.emgram.kr.dobby.dto.employee.Employee.SimpleEmployeeDTO;
import java.util.List;

import com.emgram.kr.dobby.dto.employee.EmployeeInfo;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface EmployeeDao {
   Employee getEmployeeInfo(String employeeNo);

   List<Employee> findAllEmployee();

   List<SimpleEmployeeDTO> findAllSimpleEmployeeList();

   List<EmployeeInfo> getListEmployee();

   EmployeeInfo getEmployee(String employeeNo);

   void insertEmployee(EmployeeInfo employeeInfo);

   int updateEmployee(EmployeeInfo employeeInfo);

   int deleteEmployee(String employeeNo);
}
