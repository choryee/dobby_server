package com.emgram.kr.dobby.dto;

import com.emgram.kr.dobby.utils.DateUtil;
import java.time.LocalDate;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class SearchCondition {

    private Integer pageNum;

    private Integer pageSize;

    private Integer pageOffset;

    private LocalDate startDate;

    private LocalDate endDate;

    private Integer year;

    private Integer month;

    private String query;

    private String sortQuery;

    private String direction;

    public SearchCondition(Integer pageNum, Integer pageSize, Integer year, Integer month,
        String query, String sort) {
        this.pageNum = pageNum == null ? 0 : pageNum;
        this.pageSize = pageSize == null ? 10 : pageSize;
        this.pageOffset = this.pageSize * this.pageNum;
        this.year = year == null ? LocalDate.now().getYear() : year;
        this.month = month;
        if (month == null) {
            this.startDate = DateUtil.getStartDayOfYear(this.year);
            this.endDate = DateUtil.getEndDayOfYear(this.year);
        } else {
            this.startDate = DateUtil.getStartOfMonth(this.year, month);
            this.endDate = DateUtil.getEndOfMonth(this.year, month);
        }
        this.query = query;

        if (sort != null) {
            String[] sorts = sort.split(",");
            this.sortQuery = sorts[0];
            this.direction = sorts[1];
        }
    }
}
