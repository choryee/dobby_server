package com.emgram.kr.dobby.dto.dayoff;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Date;

@Getter
@RequiredArgsConstructor
public class DayoffVacation {

    private String employee_no;
    private String dayoff_type;
    private Date dayoff_dt;
    private String code_name;
    private String code_val;
}
