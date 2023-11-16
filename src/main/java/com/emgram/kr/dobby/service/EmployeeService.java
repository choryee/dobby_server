package com.emgram.kr.dobby.service;

import com.emgram.kr.dobby.dao.EmployeeDao;
import com.emgram.kr.dobby.dto.dayoff.DayoffResult;
import com.emgram.kr.dobby.dto.employee.Employee;
import com.emgram.kr.dobby.dto.employee.Employee.SimpleEmployeeDTO;
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
    private static final int BASE_VACATION_DAYS = 15;
    private static final int VACATION_ACCRUAL_RATE = 2;

    public Employee getEmployeeInfo(String employeeNo){
      return employeeDao.getEmployeeInfo(employeeNo);
    }

   public List<SimpleEmployeeDTO> getSimpleEmployeeList() {
        return employeeDao.findAllSimpleEmployeeList();
    }

    public DayoffResult totalVacation(String employeeNo, int year) {
        Employee employee = employeeDao.getEmployeeInfo(employeeNo);
        Calendar joiningDate = toCalendar(employee.getJoiningDt());
        Calendar endYear = getEndYear(year);
        Calendar oneYearLater = getOneYearLater(joiningDate);

        double totalDayoff = endYear.after(oneYearLater) ? calculateLeavesAfterFirstYear(BASE_VACATION_DAYS, endYear, oneYearLater) :
                        calculateLeavesForFirstYear(endYear, joiningDate);

        return DayoffResult.buildTotalDayoffResult(employee, totalDayoff);
    }


    private Calendar toCalendar(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar;
    }

    private Calendar getEndYear(int year) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, Calendar.DECEMBER);
        calendar.set(Calendar.DAY_OF_MONTH, 31);
        return calendar;
    }

    private Calendar getOneYearLater(Calendar joiningCalendar) {
        Calendar oneYearAfter = (Calendar) joiningCalendar.clone();
        oneYearAfter.add(Calendar.YEAR, 1);
        return oneYearAfter;
    }

    private int calculateLeavesForFirstYear(Calendar now, Calendar joiningCalendar) {
        if (joiningCalendar.get(Calendar.YEAR) == now.get(Calendar.YEAR))
            return Math.max(0, now.get(Calendar.MONTH) -  joiningCalendar.get(Calendar.MONTH));
        else return BASE_VACATION_DAYS;
    }

    private int calculateLeavesAfterFirstYear(int baseVacation, Calendar now, Calendar oneYearAfterJoining) {
        int yearsAfterJoining = now.get(Calendar.YEAR) - oneYearAfterJoining.get(Calendar.YEAR);
        if (now.before(oneYearAfterJoining)) yearsAfterJoining--;
        return baseVacation + yearsAfterJoining / VACATION_ACCRUAL_RATE;
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
