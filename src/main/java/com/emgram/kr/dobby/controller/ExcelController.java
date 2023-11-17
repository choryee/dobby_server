package com.emgram.kr.dobby.controller;

import com.emgram.kr.dobby.dto.login.User;
import com.emgram.kr.dobby.service.ExcelService;
import java.io.IOException;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ExcelController {

    @Autowired
    ExcelService excelService;

    @GetMapping("/api/v1/users/excel/download")
    public void downloadExcel(User user, HttpServletResponse response) throws IOException {
        System.out.println("ExcelController 탐..excel/download >>  " + user);
        excelService.excelDownload(user, response);

    }
}
