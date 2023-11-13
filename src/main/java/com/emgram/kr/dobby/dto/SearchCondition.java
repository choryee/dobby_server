package com.emgram.kr.dobby.dto;

import com.emgram.kr.dobby.utils.DateUtil;
import com.emgram.kr.dobby.utils.DateUtil.LocalDateContainer;
import java.time.LocalDate;
import lombok.Getter;

@Getter
public class SearchCondition {

    private Integer pageNum;

    private Integer pageSize;

    private Integer pageOffset;

    private LocalDate startDate;

    private LocalDate endDate;

    private Integer year;

    private Integer month;

    private String query;

    public SearchCondition(Integer pageNum, Integer pageSize, Integer year, Integer month, String query) {
        this.pageNum = pageNum == null ? 0 : pageNum;
        this.pageSize = pageSize == null ? 10 : pageSize;
        this.pageOffset = this.pageSize * this.pageNum;

        if (year != null && month == null) {
            startDate = DateUtil.getStartDayOfYear(year);
            endDate = DateUtil.getEndDayOfYear(year);
        } else if (year != null && month != null) {
            startDate = DateUtil.getStartOfMonth(year, month);
            endDate = DateUtil.getEndOfMonth(year, month);
        }

        this.query = query;
    }
}
