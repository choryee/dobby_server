package com.emgram.kr.dobby.utils;

import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import org.springframework.stereotype.Component;

public class DateUtil {

    public static LocalDate getStartDayOfYear(LocalDate date) {;
        return LocalDate.of(date.getYear(), 1, 1);
    }

    public static LocalDate getStartDayOfYear(int year) {;
        return LocalDate.of(year, 1, 1);
    }

    public static LocalDate getEndDayOfYear(LocalDate date) {
        return LocalDate.of(date.getYear(), 12, 31);
    }

    public static LocalDate getEndDayOfYear(int year) {
        return LocalDate.of(year, 12, 31);
    }

    public static LocalDate getStartOfMonth(LocalDate date) {
        int year = date.getYear();
        int month = date.getMonthValue();
        return LocalDate.of(year, month, 1);
    }

    public static LocalDate getEndOfMonth(LocalDate date) {
        int year = date.getYear();
        int month = date.getMonthValue();
        int lastDay = date.lengthOfMonth(); // 해당 월의 마지막 날짜를 가져옵니다.
        return LocalDate.of(year, month, lastDay);
    }

    public static boolean isWeekend(LocalDate date) {
        DayOfWeek dayOfWeek = date.getDayOfWeek();
        return dayOfWeek == DayOfWeek.SATURDAY || dayOfWeek == DayOfWeek.SUNDAY;
    }
}
