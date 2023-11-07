package com.emgram.kr.dobby.service;

import com.emgram.kr.dobby.dao.EmployeeDao;
import com.emgram.kr.dobby.dto.employee.Employee;
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

    public int leftVacation(String employeeNo) {
        Date joiningDate = employeeDao.getEmployeeInfo(employeeNo).getJoiningDt();
        Calendar now = Calendar.getInstance();
        Calendar joiningCalendar = Calendar.getInstance();
        joiningCalendar.setTime(joiningDate);

        int baseVacation = 15;
        Calendar oneYearAfterJoining = (Calendar) joiningCalendar.clone();
        oneYearAfterJoining.add(Calendar.YEAR, 1);

        if (now.before(oneYearAfterJoining)) return calculateLeavesForFirstYear(now, joiningCalendar);
        else return calculateLeavesAfterFirstYear(baseVacation,now, oneYearAfterJoining);
    }

    private int calculateLeavesForFirstYear(Calendar now, Calendar joiningCalendar) {
        return Math.max(0, now.get(Calendar.MONTH) - joiningCalendar.get(Calendar.MONTH)
                + ((now.get(Calendar.YEAR) - joiningCalendar.get(Calendar.YEAR)) * 12));
    }

    private int calculateLeavesAfterFirstYear(int baseVacation,Calendar now, Calendar oneYearAfterJoining) {
        return baseVacation
                + (now.get(Calendar.YEAR) - oneYearAfterJoining.get(Calendar.YEAR) + 1) / 2;
    }

}
