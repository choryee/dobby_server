package com.emgram.kr.dobby.service;

import com.emgram.kr.dobby.dao.EmployeeDao;
import com.emgram.kr.dobby.dto.dayoff.DayoffResult;
import com.emgram.kr.dobby.dto.employee.Employee;
import com.emgram.kr.dobby.dto.employee.Employee.SimpleEmployeeDTO;
import com.emgram.kr.dobby.utils.DateUtil;
import java.time.LocalDate;
import java.util.List;

import com.emgram.kr.dobby.dto.employee.EmployeeInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Calendar;
import java.util.Date;

@Service
@RequiredArgsConstructor
public class EmployeeService {
    private final EmployeeDao employeeDao;

    public Employee getEmployeeInfo(String employeeNo){
      return employeeDao.getEmployeeInfo(employeeNo);
    }

    public List<Employee> getAllEmployeeList() {
        return employeeDao.findAllEmployee();
    }

    public List<SimpleEmployeeDTO> getSimpleEmployeeList() {
        return employeeDao.findAllSimpleEmployeeList();
    }

    public Employee getEmployeeByEmployeeNo(String employeeNo) {
        return employeeDao.getEmployeeInfo(employeeNo);
    }

    public List<EmployeeInfo> getListEmployee() {
        return employeeDao.getListEmployee();
    }

    public EmployeeInfo getList(String employeeNo) {
        return employeeDao.getEmployee(employeeNo);
    }

    public void insertEmployee(EmployeeInfo employeeInfo){

        employeeDao.insertEmployee(employeeInfo);
    }

    public int updateEmployee(EmployeeInfo employeeInfo) {

        return employeeDao.updateEmployee(employeeInfo);
    }

    public int deleteEmployee(String employeeNo) {
        return employeeDao.deleteEmployee(employeeNo);
    }
}
