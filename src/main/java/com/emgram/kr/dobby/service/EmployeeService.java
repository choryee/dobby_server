package com.emgram.kr.dobby.service;

import com.emgram.kr.dobby.dao.EmployeeDao;
import com.emgram.kr.dobby.dto.employee.Employee;
import com.emgram.kr.dobby.dto.employee.Employee.SimpleEmployeeDTO;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;

@Service
@RequiredArgsConstructor
public class EmployeeService {
    private final EmployeeDao employeeDao;

    public Employee getEmployeeInfo(String employeeNo){
      return employeeDao.getEmployeeInfo(employeeNo);
    }

   public List<SimpleEmployeeDTO> getSimpleEmployeeList() {
        return employeeDao.findAllSimpleEmployeeList();
    }

    public int totalVacation(String employeeNo, int year) {
        Calendar joiningDate = toCalendar(employeeDao.getEmployeeInfo(employeeNo).getJoiningDt());
        Calendar endYear = getEndYear(year);
        Calendar oneYearLater = getOneYearLater(joiningDate);

        if (!endYear.after(joiningDate)) return 0;

        if (endYear.after(oneYearLater)) return calculateLeavesAfterFirstYear(15, endYear, oneYearLater);
        else return calculateLeavesForFirstYear(endYear, joiningDate);
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
        int joiningYear = joiningCalendar.get(Calendar.YEAR);
        int currentYear = now.get(Calendar.YEAR);
        int joiningMonth = joiningCalendar.get(Calendar.MONTH);
        int currentMonth = now.get(Calendar.MONTH);

        if (joiningYear == currentYear) return Math.max(0, currentMonth - joiningMonth);
        else return 15;
    }

    private int calculateLeavesAfterFirstYear(int baseVacation, Calendar now, Calendar oneYearAfterJoining) {
        int yearsAfterJoining = now.get(Calendar.YEAR) - oneYearAfterJoining.get(Calendar.YEAR);
        if (now.before(oneYearAfterJoining)) yearsAfterJoining--;
        return baseVacation + yearsAfterJoining / 2;
    }

}
