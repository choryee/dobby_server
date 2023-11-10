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

    private String searchType;

    private String query;

    public SearchCondition(Integer pageNum, Integer pageSize, String searchType, String query) {
        this.pageNum = pageNum == null ? 0 : pageNum;
        this.pageSize = pageSize == null ? 10 : pageSize;
        this.pageOffset = this.pageSize * this.pageNum;
        if (searchType != null && searchType.equals("date")) {
            LocalDateContainer container = DateUtil.parseDate(query);
            if (container != null) {
                this.startDate = DateUtil.getStartDayOfDetail(container);
                this.endDate = DateUtil.getEndDayOfDetail(container);
            }
        }
        this.query = query;
    }
}
