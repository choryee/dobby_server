package com.emgram.kr.dobby.dto.employee;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.Date;

@Getter
@NoArgsConstructor
@SuperBuilder
public class Employee {

    private String employeeNo;
    private String rankName;
    private int departmentNo;
    private String name;
    private Date joiningDt;

    public Employee(String employeeNo, String rankName, int departmentNo, String name, Date joiningDt) {
        this.employeeNo = employeeNo;
        this.rankName = rankName;
        this.departmentNo = departmentNo;
        this.name = name;
        this.joiningDt = joiningDt;
    }

    @Getter
    @NoArgsConstructor
    public static class SimpleEmployeeDTO {
        private String employeeNo;

        private String rankName;

        private String name;
    }
}
