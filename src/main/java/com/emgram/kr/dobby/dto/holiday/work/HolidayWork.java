package com.emgram.kr.dobby.dto.holiday.work;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.sql.Date;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.jetbrains.annotations.NotNull;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class HolidayWork {

    private Long holidayWorkId;

    @NotNull
    private String employeeNo;

    @NotNull
    private LocalDate workDate;

    @NotNull
    private String memo;

}
