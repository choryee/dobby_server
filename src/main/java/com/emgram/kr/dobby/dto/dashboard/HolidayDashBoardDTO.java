package com.emgram.kr.dobby.dto.dashboard;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class HolidayDashBoardDTO {

    private String name;

    private Integer holidayWorkDateCount;
}
