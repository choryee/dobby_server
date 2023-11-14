package com.emgram.kr.dobby.dto.dayoff;

import java.sql.Date;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
@AllArgsConstructor
public class DayoffVacation {

    private String employeeNo;
    private String dayoffType;
    private LocalDate dayoffDt;
    private String codeName;
    private String codeVal;

    public DayoffVacation(String employeeNo, String dayoffType, Date dayoffDt, String codeName,
        String codeVal) {
        this.employeeNo = employeeNo;
        this.dayoffType = dayoffType;
        this.dayoffDt = dayoffDt.toLocalDate();
        this.codeName = codeName;
        this.codeVal = codeVal;
    }
}
