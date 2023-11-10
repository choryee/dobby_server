package com.emgram.kr.dobby.dto.holiday.work;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.sql.Date;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class HolidayWork {

    private Long holidayWorkId;

    private String employeeNo;

    private LocalDate workDate;

    private String memo;

}
