package com.emgram.kr.dobby.controller;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.emgram.kr.dobby.dto.syscode.SystemCodeListItem;
import com.emgram.kr.dobby.service.SystemCodeService;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

@WebMvcTest(controllers = SystemCodeController.class)
public class SystemCodeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SystemCodeService systemCodeService;

    @Test
    @DisplayName("성공적으로 시스템 코드를 조회 할 수 있어야 한다.")
    public void getSystemCodes() throws Exception {
        //given
        SystemCodeListItem singleItem = new SystemCodeListItem("day_off", "5001",
            "오후반차", "1");
        List<SystemCodeListItem> items = Collections.singletonList(singleItem);
        given(systemCodeService.getSystemCodes()).willReturn(items);

        //when
        ResultActions resultActions = mockMvc.perform(get("/syscode")
            .contentType("application/json"));

        //then
        resultActions
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.resultCode").value("0"));
    }
}
