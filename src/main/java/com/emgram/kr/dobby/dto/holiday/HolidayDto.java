package com.emgram.kr.dobby.dto.holiday;

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

}
