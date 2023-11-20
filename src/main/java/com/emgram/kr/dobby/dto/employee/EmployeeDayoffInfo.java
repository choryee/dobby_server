package com.emgram.kr.dobby.dto.employee;

import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmployeeDayoffInfo {

    private String employeeNo;

    private String name;

    private String rankName;

    private LocalDate joiningDt;

    private Double totalDayoffCount;

    private Double usedDayoffCount;

    private Double remainingDayoffCount;

}
