package com.emgram.kr.dobby.dto.calendar;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Calendar {

    private int eventIdx; //이벤트 순번
    private String eventId; //이벤트 ID
    private String calendarId; //캘린더 ID
    private String eventName; //이벤트 이름
    private Date startDt; //이벤트 시작일
    private Date endDt; //이벤트 종료일
    private Date eventTag; //이벤트 태크
    private String wid; //wid
    private String createUser; //이벤트 생성자
    private String updateUser; //이벤트 수정자
    private Date createDt; //이벤트 생성일
    private Date updateDt; //이벤트 수정일
    private Date deleteDt; //이벤트 삭제일
}
