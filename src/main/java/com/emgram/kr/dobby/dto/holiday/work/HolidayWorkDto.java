package com.emgram.kr.dobby.dto.holiday.work;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.sql.Date;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class HolidayWorkDto {

    private Long holidayWorkId;

    private String employeeNo;

    private String name;

    private String rankName;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate workDate;

    private String memo;

    public HolidayWorkDto(Long holidayWorkId, String employeeNo, String name, String rankName, Date workDate, String memo) {
        this.holidayWorkId = holidayWorkId;
        this.employeeNo = employeeNo;
        this.name = name;
        this.rankName = rankName;
        if (workDate != null) {
            this.workDate = workDate.toLocalDate();
        }
        this.memo = memo;
    }
}
