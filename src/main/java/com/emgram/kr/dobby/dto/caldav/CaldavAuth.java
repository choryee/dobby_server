package com.emgram.kr.dobby.dto.caldav;

import okhttp3.Credentials;

public class CaldavAuth {
    private String credential;

    public CaldavAuth(String username, String password) {
        this.credential = Credentials.basic(username, password);
    }

    public String getCredential() {
        return credential;
    }
}
