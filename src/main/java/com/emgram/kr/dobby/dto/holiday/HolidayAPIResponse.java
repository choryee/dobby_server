package com.emgram.kr.dobby.dto.holiday;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class HolidayAPIResponse {

    private HolidayAPiResponseContainer response;

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class HolidayAPiResponseContainer {

        private HolidayAPIResponseHeader header;

        private HolidayAPIResponseBody body;
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class HolidayAPIResponseHeader {

        private String resultCode;

        private String resultMsg;
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class HolidayAPIResponseBody {

        private HolidayAPIItemContainer items;

        private Integer numOfRows;

        private Integer pageNo;

        private Integer totalCount;
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class HolidayAPIItemContainer {

        private List<HolidayAPIItem> item;
    }


    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class HolidayAPIItem {

        private Integer seq;

        private String dateName;

        private String locdate;

        private String isHoliday;

        private String dateKind;

        public Boolean isHoliday() {
            return isHoliday.equals("Y");
        }

        public HolidayDto toHolidayDto() {
            LocalDate localDate = LocalDate.parse(locdate,
                DateTimeFormatter.BASIC_ISO_DATE); //yyyyMMdd
            return new HolidayDto(dateName, localDate, isHoliday(), dateKind);
        }
    }
}
