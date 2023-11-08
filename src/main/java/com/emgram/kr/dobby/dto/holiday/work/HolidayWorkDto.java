package com.emgram.kr.dobby.dto.holiday.work;

import java.sql.Date;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class HolidayWorkDto {

    private Long holidayWorkId;

    private String employeeNo;

    private LocalDate workDate;

    private String memo;

    public HolidayWorkDto(Long holidayWorkId, String employeeNo, Date workDate, String memo) {
        this.holidayWorkId = holidayWorkId;
        this.employeeNo = employeeNo;
        if (workDate != null) {
            this.workDate = workDate.toLocalDate();
        }
        this.memo = memo;
    }
}
