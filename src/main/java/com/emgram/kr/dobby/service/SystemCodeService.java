package com.emgram.kr.dobby.service;

import com.emgram.kr.dobby.dao.SystemCodeDao;
import com.emgram.kr.dobby.dto.syscode.SystemCodeListItem;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;

@Service
@RequestMapping
@RequiredArgsConstructor
public class SystemCodeService {

    private final SystemCodeDao systemCodeDao;

    public List<SystemCodeListItem> getSystemCodes() {
        return systemCodeDao.getAllSystemCodeList();
    }

}
