package com.emgram.kr.dobby.dao;


import com.emgram.kr.dobby.dto.employee.Employee;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
@SpringBootTest
public class EmployeeDaoTest {

    @Test
    @DisplayName("Employee 객체 생성 테스트")
    public void createEmployeeObject() {

        String employeeNo = "M078";
        int rankNo = 1;
        int departmentNo = 101;
        String name = "김진성";

        Employee employee = Employee.builder()
                .employeeNo(employeeNo)
                .rankNo(rankNo)
                .departmentNo(departmentNo)
                .name(name)
                .joiningDt(new java.util.Date())
                .build();

        // Then
        assertNotNull(employee);
        assertEquals(employee.getEmployeeNo(), employeeNo);
        assertEquals(employee.getRankNo(), rankNo);
        assertEquals(employee.getDepartmentNo(), departmentNo);
        assertEquals(employee.getName(), name);
        assertNotNull(employee.getJoiningDt());
    }
}
