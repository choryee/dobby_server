package com.emgram.kr.dobby.dto.employee;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Date;

@Getter
@NoArgsConstructor
public class Employee {

    private String employeeNo;
    private int rankNo;
    private int departmentNo;
    private String name;
    private Date joiningDt;

    @Builder
    public Employee(String  employeeNo, int rankNo, int departmentNo, String name,
                    Date joiningDt){
        this.employeeNo = employeeNo;
        this.rankNo = rankNo;
        this.departmentNo = departmentNo;
        this.name = name;
        this.joiningDt = joiningDt;
    }

}
