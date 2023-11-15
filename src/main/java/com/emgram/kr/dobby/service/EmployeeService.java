package com.emgram.kr.dobby.service;

import com.emgram.kr.dobby.dao.EmployeeDao;
import com.emgram.kr.dobby.dto.dayoff.DayoffResult;
import com.emgram.kr.dobby.dto.employee.Employee;
import com.emgram.kr.dobby.dto.employee.Employee.SimpleEmployeeDTO;
import com.emgram.kr.dobby.utils.DateUtil;
import java.time.LocalDate;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;

@Service
@RequiredArgsConstructor
public class EmployeeService {
    private final EmployeeDao employeeDao;
    private static final int BASE_VACATION_DAYS = 15;
    private static final int VACATION_ACCRUAL_RATE = 2;

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

    public double calculateTotalVacation(Employee employee, int year) {
        LocalDate joiningDate = employee.getJoiningDt();
        LocalDate endYear = DateUtil.getEndDayOfYear(year);
        LocalDate oneYearLater = getOneYearLater(joiningDate);
        return endYear.isAfter(oneYearLater) ? calculateLeavesAfterFirstYear(BASE_VACATION_DAYS, endYear, oneYearLater) :
                        calculateLeavesForFirstYear(endYear, joiningDate);
    }

    private LocalDate getOneYearLater(LocalDate joiningDate) {
        return LocalDate.of(joiningDate.getYear() + 1, joiningDate.getMonth(), joiningDate.getDayOfMonth());
    }

    private int calculateLeavesForFirstYear(LocalDate now, LocalDate joiningCalendar) {
        if (joiningCalendar.getYear() == now.getYear())
            return Math.max(0, now.getMonthValue() -  joiningCalendar.getMonthValue());
        else return BASE_VACATION_DAYS;
    }

    private int calculateLeavesAfterFirstYear(int baseVacation, LocalDate now, LocalDate oneYearAfterJoining) {
        int yearsAfterJoining = now.getYear() -  oneYearAfterJoining.getYear();
        if (now.isBefore(oneYearAfterJoining)) yearsAfterJoining--;
        return baseVacation + yearsAfterJoining / VACATION_ACCRUAL_RATE;
    }

}
