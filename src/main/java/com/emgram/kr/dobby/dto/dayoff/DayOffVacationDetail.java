package com.emgram.kr.dobby.dto.dayoff;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class DayOffVacationDetail {
    private String dayoffType; // 1001-->2(연차)
    private LocalDate dayoffDt;
    private String codeName;
    private String codeVal;


    @Override
    public String toString() {
        return "DayOffVacationDetail{" +
                "dayoffType='" + dayoffType + '\'' +
                ", dayoffDt=" + dayoffDt +
                ", codeName='" + codeName + '\'' +
                ", codeVal='" + codeVal + '\'' +
                '}';
    }
}
