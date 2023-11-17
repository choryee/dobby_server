package com.emgram.kr.dobby.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PageInfo<T> {

    private int totalCount;

    private int pageNum;

    private int pageSize;

    private List<T> content;

    private boolean hasNextPage;

    private boolean hasPreviousPage;

    public PageInfo(int totalCount, int pageNum, int pageSize, List<T> content) {
        this.totalCount = totalCount;
        this.pageNum = pageNum;
        this.pageSize = pageSize;

        hasNextPage = content.size() == pageSize && totalCount > (pageNum+1) * pageSize;
        hasPreviousPage = pageNum <= 0;
    }

    /**
     * list size가 pageSize보다 작을때 사용
     * @param searchCondition
     * @param content
     */
    public PageInfo(SearchCondition searchCondition, List<T> content) {
        this.pageNum = searchCondition.getPageNum();
        this.pageSize = searchCondition.getPageSize();
        this.totalCount = this.pageNum * this.pageSize + content.size();
        this.hasNextPage = false;
        this.hasPreviousPage = this.pageNum != 0;
        this.content = content;
    }

    public PageInfo(int totalCount, SearchCondition searchCondition, List<T> content) {
        this.totalCount = totalCount;
        this.pageNum = searchCondition.getPageNum();
        this.pageSize = searchCondition.getPageSize();

        this.hasNextPage = content.size() == pageSize && totalCount > (pageNum+1) * pageSize;
        this.hasPreviousPage = pageNum != 0;
        this.content = content;
    }
}
