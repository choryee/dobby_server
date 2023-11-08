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


    public int totalVacation(String employeeNo) {
        Date joiningDate = employeeDao.getEmployeeInfo(employeeNo).getJoiningDt();
        Calendar now = Calendar.getInstance();
        Calendar joiningCalendar = Calendar.getInstance();
        joiningCalendar.setTime(joiningDate);

        int baseVacation = 15;
        Calendar oneYearAfterJoining = (Calendar) joiningCalendar.clone();
        oneYearAfterJoining.add(Calendar.YEAR, 1);

        // 입사 후 첫 해가 끝나는 경우, 기본적으로 15일의 휴가를 부여합니다.
        if (now.get(Calendar.YEAR) > joiningCalendar.get(Calendar.YEAR)) {
            return calculateLeavesAfterFirstYear(baseVacation, now, oneYearAfterJoining);
        } else {
            return calculateLeavesForFirstYear(now, joiningCalendar);
        }
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
