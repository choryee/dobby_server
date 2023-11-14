package com.emgram.kr.dobby.utils;

import com.emgram.kr.dobby.dto.holiday.HolidayDto;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;
import lombok.AllArgsConstructor;
import lombok.Getter;

public class DateUtil {

    private static TimeZone timeZone = TimeZone.getTimeZone("Asia/Seoul");

    @Getter
    @AllArgsConstructor
    static class FormatterContainer {

        private SimpleDateFormat formatter;

        private String detail;
    }

    @Getter
    @AllArgsConstructor
    public static class LocalDateContainer {

        private LocalDate localDate;

        private String detail;
    }

    private static FormatterContainer[] dateFormatterContainers = {
        new FormatterContainer(new SimpleDateFormat("yyyyMMdd", Locale.KOREAN), "day"),
        new FormatterContainer(new SimpleDateFormat("yyyyMMd", Locale.KOREAN), "day"),
        new FormatterContainer(new SimpleDateFormat("yyyyMM", Locale.KOREAN), "month"),
        new FormatterContainer(new SimpleDateFormat("yyyyM", Locale.KOREAN), "month"),
        new FormatterContainer(new SimpleDateFormat("yyyy", Locale.KOREAN), "year"),
        new FormatterContainer(new SimpleDateFormat("yyy", Locale.KOREAN), "year"),
        new FormatterContainer(new SimpleDateFormat("yy", Locale.KOREAN), "year"),
        new FormatterContainer(new SimpleDateFormat("y", Locale.KOREAN), "year")
    };

    public static LocalDateContainer parseDate(String dateString) {
        String replacedString = dateString
            .replace(".", "")
            .replace("/", "")
            .replace(" ", "")
            .replace("-", "");

        try {
            FormatterContainer formatterContainer =
                dateFormatterContainers[8 - replacedString.length()];

            Date parsedDate = formatterContainer.formatter.parse(replacedString);
            LocalDate localDate = parsedDate.toInstant().atZone(timeZone.toZoneId()).toLocalDate();

            return new LocalDateContainer(localDate, formatterContainer.detail);
        } catch (DateTimeParseException e) {
            e.printStackTrace();
        } catch (Exception e) {
            return null;
        }

        return null;
    }

    public static LocalDate getStartDayOfDetail(LocalDateContainer localDateContainer) {
        if (localDateContainer == null) {
            return null;
        }

        switch (localDateContainer.detail) {
            case "day":
                return localDateContainer.getLocalDate();
            case "month":
                return getStartOfMonth(localDateContainer.getLocalDate());
            case "year":
                return getStartDayOfYear(localDateContainer.getLocalDate());
        }
        return null;
    }

    public static LocalDate getEndDayOfDetail(LocalDateContainer localDateContainer) {
        if (localDateContainer == null) {
            return null;
        }

        switch (localDateContainer.detail) {
            case "day":
                return localDateContainer.getLocalDate();
            case "month":
                return getEndOfMonth(localDateContainer.getLocalDate());
            case "year":
                return getEndDayOfYear(localDateContainer.getLocalDate());
        }
        return null;
    }

    public static LocalDate getStartDayOfYear(LocalDate date) {
        return LocalDate.of(date.getYear(), 1, 1);
    }

    public static LocalDate getStartDayOfYear(int year) {
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


    public static LocalDate getStartOfMonth(int year, int month) {
        return LocalDate.of(year, month, 1);
    }

    public static LocalDate getEndOfMonth(int year, int month) {
        LocalDate localDate = LocalDate.of(year, month, 1);
        return LocalDate.of(year, month, localDate.lengthOfMonth());
    }

    public static LocalDate getEndOfMonth(LocalDate date) {
        int year = date.getYear();
        int month = date.getMonthValue();
        int lastDay = date.lengthOfMonth(); // 해당 월의 마지막 날짜를 가져옵니다.
        return LocalDate.of(year, month, lastDay);
    }

    public static boolean isHoliday(LocalDate date, List<? extends HolidayDto> list) {
        return list.stream().anyMatch((item) -> item.getHoliday().isEqual(date));
    }

    public static boolean isWeekend(LocalDate date) {
        DayOfWeek dayOfWeek = date.getDayOfWeek();
        return dayOfWeek == DayOfWeek.SATURDAY || dayOfWeek == DayOfWeek.SUNDAY;
    }
}
