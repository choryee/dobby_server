package com.emgram.kr.dobby.utils;

public enum DayoffType {
    DAY_OFF(1000, 1999),
    VACATION(2000, 2999);

    private final int start;
    private final int end;

    DayoffType(int start, int end) {
        this.start = start;
        this.end = end;
    }

    public boolean inRange(int dayoffType) {
        return dayoffType >= start && dayoffType <= end;
    }
}