package com.emgram.kr.dobby.dao;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThan;
import static org.junit.jupiter.api.Assertions.assertEquals;

import com.emgram.kr.dobby.dto.syscode.SystemCodeListItem;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
public class SystemCodeDaoTest {

    @Autowired
    private SystemCodeDao systemCodeDao;

    @Test
    @DisplayName("시스템 코드 조회시 성공적으로 조회 할 수 있어야 한다.")
    @Transactional //for rollback
    public void getTest1() {
        //setup
        systemCodeDao.insertSystemCode(new SystemCodeListItem("day_off", "5001", "연차", "1"));
        systemCodeDao.insertSystemCode(new SystemCodeListItem("day_off", "5002", "오후반차", "0.5"));
        systemCodeDao.insertSystemCode(new SystemCodeListItem("day_off", "5003", "오전반차", "0.5"));

        //given
        String codeId = "5001";

        //when
        List<SystemCodeListItem> codeListItems = systemCodeDao.getAllSystemCodeList();
        SystemCodeListItem systemCodeListItem =
            codeListItems.stream().filter(item -> item.getCodeId().equals(codeId)).findFirst()
                .orElseThrow(NullPointerException::new);

        //then
        assertThat(codeListItems.size(), greaterThan(3));
        assertEquals(systemCodeListItem.getCodeId(), codeId);

    }

}
