package com.emgram.kr.dobby.controller;

import com.emgram.kr.dobby.dto.login.User;
import com.emgram.kr.dobby.service.ExcelService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
public class ExcelController {

    private static final Logger logger = LoggerFactory.getLogger(ExcelController.class);


    @Autowired
    ExcelService excelService;

    @GetMapping("/api/v1/users/excel/download")
    public void downloadExcel(User user,HttpServletResponse response ) throws IOException {
        logger.info("ExcelController 탐..excel/download >>  {}", user);
        excelService.excelDownload(user, response);
    }
}
