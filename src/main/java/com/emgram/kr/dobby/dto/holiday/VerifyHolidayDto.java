package com.emgram.kr.dobby.dto.holiday;

import com.emgram.kr.dobby.utils.DateUtil;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
public class VerifyHolidayDto extends HolidayDto {

    private boolean isWeekend;

    public VerifyHolidayDto(String holidayName, LocalDate holiday, Boolean isRestInstitutions,
        String dateKindNum) {
        super(holidayName, holiday, isRestInstitutions, dateKindNum);
        this.isWeekend = DateUtil.isWeekend(holiday);
    }

    public VerifyHolidayDto(String holidayName, LocalDate holiday, Boolean isRestInstitutions,
        String dateKindNum, boolean isWeekend) {
        super(holidayName, holiday, isRestInstitutions, dateKindNum);
        this.isWeekend = isWeekend;
    }

    public static VerifyHolidayDto of (HolidayDto holidayDto) {
        return new VerifyHolidayDto(
            holidayDto.getHolidayName(),
            holidayDto.getHoliday(),
            holidayDto.getIsRestInstitutions(),
            holidayDto.getDateKindNum(),
            DateUtil.isWeekend(holidayDto.getHoliday()));
    }
}
