package com.emgram.kr.dobby.dto.dayoff;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
@AllArgsConstructor
public class DayoffVacation {

    private String employeeNo;

    private String dayoffType; // 1001-->2(연차)
    private LocalDate dayoffDt;
    private String codeName;
    private String codeVal;

//    private List<String> dayoffType;
//    private List<LocalDate> dayoffDt;
//    private List<String> codeName;
//    private List<String> codeVal;

    private List<DayOffVacationDetail> dayOffVacationDetails;

//    public DayoffVacation(String employeeNo,
//                          String dayoffType,
//                          Date dayoffDt,
//                          String codeName,
//                          String codeVal) {
//        this.employeeNo = employeeNo;
//        this.dayoffType = dayoffType;
//        this.dayoffDt = dayoffDt.toLocalDate();
//        this.codeName = codeName;
//        this.codeVal = codeVal;
//    }
}
