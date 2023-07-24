package com.emgram.kr.dobby.dto.caldav;

import lombok.Getter;
import lombok.Setter;

@Setter
public class CaldavResult {

    private CalendarStatus calendar;
    private EventStatus event;

    public static class CalendarStatus{
        private String message;
        public CalendarStatus(CalendarStatusType type){
            this.message = type.getMessage();
        }
        public CalendarStatus(CalendarStatusType type, String calendarName, String cTag){
            this.message = String.format("%s [%s(%s)]"
                    , type.getMessage()
                    , calendarName
                    , cTag);
        }
        public CalendarStatus(CalendarStatusType type, String oldName, String oldTag, String newName, String newTag){
            this.message = String.format("%s [%s(%s)]\t>\t[%s(%s)]"
                    , type.getMessage()
                    , oldName
                    , oldTag
                    , newName
                    , newTag);
        }

        public String getMessage() {
            return message;
        }
    }
    public enum CalendarStatusType{
        CAL_NOTFOUND("CST001", "일치하는 캘린더 없음"),
        CAL_MAINTAIN("CST002", "캘린더 변동사항 없음"),
        CAL_NEW("CST101", "신규 캘린더 발생"),
        CAL_UPDATE("CST102", "변동 캘린더 발생");

        private String type;
        private String message;

        CalendarStatusType(String type, String message){
            this.type = type;
            this.message = message;
        }
        public String getType() { return type; }
        public String getMessage() { return message; }
    }

    @Getter
    public static class EventStatus{
        String insert;
        String update;
        String delete;

        public EventStatus() {
            this.setInsert(0);
            this.setUpdate(0);
            this.setDelete(0);
        }

        public void setInsert(int num) {
            this.insert = String.format("%s된 이벤트 : %s", EventStatusType.INSERT.getName(), num);
        }

        public void setUpdate(int num) {
            this.update = String.format("%s된 이벤트 : %s", EventStatusType.UPDATE.getName(), num);
        }

        public void setDelete(int num) {
            this.delete = String.format("%s된 이벤트 : %s", EventStatusType.DELETE.getName(), num);
        }

    }
    public enum EventStatusType{
        DELETE("EST001", "삭제"),
        INSERT("EST001", "추가"),
        UPDATE("EST001", "수정");
        private String code;
        private String name;
        EventStatusType(String code, String name){
            this.code = code;
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }

    public String getCalendarStatus() {
        return calendar.getMessage();
    }

    public EventStatus getEventStatus() {
        return event;
    }
}
