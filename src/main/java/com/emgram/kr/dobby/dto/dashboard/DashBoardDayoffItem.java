package com.emgram.kr.dobby.dto.dashboard;

import java.sql.Date;
import java.time.LocalDate;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class DashBoardDayoffItem {

    private String employeeNo;

    @Getter(AccessLevel.NONE)
    private Date joiningDt;

    private String rankName;

    private String dayoffType;

    @Getter(AccessLevel.NONE)
    private Date dayoffDt;

    private String codeName;

    private double codeVal;

    public LocalDate getJoiningDt() {
        return joiningDt.toLocalDate();
    }

    public LocalDate getDayoffDt() {
        return dayoffDt.toLocalDate();
    }

}