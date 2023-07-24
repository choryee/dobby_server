package com.emgram.kr.dobby.dto.caldav;

public class CaldavRequestData {
    private String requestData;

    public CaldavRequestData(CaldavRequestType type) {
        this.requestData = type.getValue();
    }

    public CaldavRequestData(CaldavRequestType type, String param) {
        this.requestData = type.getValue()
                .replace("%1%", param);
    }

    public CaldavRequestData(CaldavRequestType type, String param1, String param2) {
        this.requestData = type.getValue()
                .replace("%1%", param1)
                .replace("%2%", param2);
    }

    public String getRequestData() {
        return requestData;
    }
}
