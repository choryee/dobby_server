package com.emgram.kr.dobby.dto.dayoff;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Date;

@Getter
@RequiredArgsConstructor
@AllArgsConstructor
public class DayoffVacation {

    private String employeeNo;
    private String dayoffType;
    private Date dayoffDt;
    private String codeName;
    private String codeVal;
}
