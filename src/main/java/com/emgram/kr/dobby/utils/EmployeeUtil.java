package com.emgram.kr.dobby.utils;

import com.emgram.kr.dobby.dto.dashboard.DashBoardDayoffDTO;
import com.emgram.kr.dobby.dto.employee.Employee;
import java.util.Arrays;
import java.util.List;

public class EmployeeUtil {

    private static final List<String> EMPLOYEE_RANK_NAMES = Arrays.asList("매니저", "팀장");

    /**
     * 사원인지 임원인지 판별
     */
    public static boolean isEmployee(Employee employee) {
        return EMPLOYEE_RANK_NAMES.stream()
            .anyMatch((rankName) -> employee.getRankName().equals(rankName));
    }

    public static boolean isEmployee(DashBoardDayoffDTO item) {
        return EMPLOYEE_RANK_NAMES.stream()
            .anyMatch((rankName) -> item.getRankName().equals(rankName));
    }
}
