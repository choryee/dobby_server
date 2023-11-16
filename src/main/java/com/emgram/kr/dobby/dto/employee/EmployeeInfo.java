package com.emgram.kr.dobby.dto.employee;

import lombok.*;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeInfo {

    private String employeeNo; //사원번호
    private int rankNo; //직급번호
    private int departmentNo; //부서번호
    private String password; //암호
    private String name; //사원명
    private Date joiningDt; //입사년월
    private Date leavingDt; //퇴사년월
    private String auth; //권한
    private Date createDt; //생성일
    private String creatorId; //생성자
    private Date updateDt; // 수정일
    private String updatorId; //수정자
    private Date deleteDt; //삭제일
    private String deletorId; //삭제자
    private String wid;

}
