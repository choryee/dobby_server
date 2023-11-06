package com.emgram.kr.dobby.dao;

import com.emgram.kr.dobby.dto.syscode.SystemCodeListItem;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface SystemCodeDao {
    List<SystemCodeListItem> getAllSystemCodeList();
    void insertSystemCode(SystemCodeListItem vo);
}
