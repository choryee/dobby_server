package com.emgram.kr.dobby.dto.dayoff;

import java.sql.Date;
import java.time.LocalDate;
<<<<<<< HEAD
=======
import java.util.ArrayList;
>>>>>>> 3f1457c21bd1dd4452ab7c8cd203bf432fc2b800
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
@AllArgsConstructor
public class DayoffVacation {

    private String employeeNo;

    private String dayoffType; // 1001-->2(연차)
    private LocalDate dayoffDt;
    private String codeName;
    private String codeVal;

    public DayoffVacation(String employeeNo, String dayoffType, Date dayoffDt, String codeName, String codeVal) {
        this.employeeNo = employeeNo;
        this.dayoffType = dayoffType;
        this.dayoffDt = dayoffDt.toLocalDate();
        this.codeName = codeName;
        this.codeVal = codeVal;
    }

    public List<DayoffVacation> dayOffVacationsList(){
        List<DayoffVacation> list =new ArrayList<>();
        DayoffVacation dayoffVacation =new DayoffVacation();
        list.add(dayoffVacation);
        return list;
    }

    @Override
    public String toString() {
        return "DayoffVacation{" +
                "employeeNo='" + employeeNo + '\'' +
                ", dayoffType='" + dayoffType + '\'' +
                ", dayoffDt=" + dayoffDt +
                ", codeName='" + codeName + '\'' +
                ", codeVal='" + codeVal + '\'' +
                '}';
    }
>>>>>>> 3f1457c21bd1dd4452ab7c8cd203bf432fc2b800
}
