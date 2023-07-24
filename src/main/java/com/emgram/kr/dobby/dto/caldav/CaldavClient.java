package com.emgram.kr.dobby.dto.caldav;

import lombok.Getter;

@Getter
public class CaldavClient {
    private String hostname;
    private CaldavAuth auth;
    private String calendarId;
    private String domainUrl;
    private String homeSetUrl;

    public CaldavClient(String hostname, int port, String username, String password, String calendarId){
        this.hostname = (port==443?"https://":"http://") + hostname;
        this.auth = new CaldavAuth(username, password);
        this.calendarId = calendarId;
    }

    public void setDomainUrl(String domainUrl) {
        this.domainUrl = domainUrl;
    }

    public void setHomeSetUrl(String homeSetUrl) {
        this.homeSetUrl = homeSetUrl;
    }
}
