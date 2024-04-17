package com.emgram.kr.dobby.dto.employee;

import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.sql.Date;

@Getter
@NoArgsConstructor
@SuperBuilder
@AllArgsConstructor
public class Employee {

    private String wid;
    private String employeeNo;
    private String rankName;
    private int departmentNo;
    private String name;
    private LocalDate joiningDt;

    private String dayoffType;
    private LocalDate dayoffDt;
    private String codeName;
    private String codeVal;

    public Employee(String wid, String employeeNo, String rankName, int departmentNo, String name, Date joiningDt, String dayoffType,
                    Date dayoffDt, String codeName, String codeVal) {
        this.wid = wid;
        this.employeeNo = employeeNo;
        this.rankName = rankName;
        this.departmentNo = departmentNo;
        this.name = name;
        this.joiningDt = joiningDt.toLocalDate();
        this.dayoffType=dayoffType;
        this.dayoffDt = dayoffDt.toLocalDate();
        this.codeName=codeName;
        this.codeVal=codeVal;
    }

    @Getter
    @NoArgsConstructor
    public static class SimpleEmployeeDTO {
        private String employeeNo;

        private String rankName;

        private String name;
    }
}
