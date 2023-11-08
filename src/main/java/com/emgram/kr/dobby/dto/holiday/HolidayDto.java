package com.emgram.kr.dobby.dto.holiday;

import java.sql.Date;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class HolidayDto {

    private String holidayName;

    private LocalDate holiday;

    private Boolean isRestInstitutions;

    private String dateKindNum;

    public HolidayDto (String holidayName, Date holiday, Boolean isRestInstitutions, String dateKindNum) {
        this.holidayName = holidayName;
        this.holiday = holiday.toLocalDate();
        this.isRestInstitutions = isRestInstitutions;
        this.dateKindNum = dateKindNum;
    }

}
