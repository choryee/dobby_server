package com.emgram.kr.dobby.dto.caldav;

import lombok.Getter;

import java.util.List;

@Getter
public class CaldavCalendar {
    private String calendarId;
    private String calendarUrl;
    private String calendarName;
    private String cTag;
    private List<CaldavEvent> eventList;

    public CaldavCalendar() {}

    public CaldavCalendar(String calendarId, String calendarUrl, String calendarName, String cTag) {
        this.calendarId = calendarId;
        this.calendarUrl = calendarUrl;
        this.calendarName = calendarName;
        this.cTag = cTag;
    }

    public void setEventList(List<CaldavEvent> eventList) {
        this.eventList = eventList;
    }
}
