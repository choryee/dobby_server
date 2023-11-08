package com.emgram.kr.dobby.dto.dayoff;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class DayoffResult {

    private double totalDayoff;
    private double leftDayOff;
    private double usedDayoff;

    @Builder
    public DayoffResult(double totalDayoff, double leftDayOff, double usedDayoff){
        this.totalDayoff = totalDayoff;
        this.leftDayOff = leftDayOff;
        this.usedDayoff = usedDayoff;
    }
}
