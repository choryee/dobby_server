package com.emgram.kr.dobby.controller;

import com.emgram.kr.dobby.dto.login.User;
import com.emgram.kr.dobby.service.ExcelService;
import com.emgram.kr.dobby.service.UserService;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.jws.soap.SOAPBinding;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

@RestController
public class ExcelController {

    @Autowired
    ExcelService excelService;

    @GetMapping("/api/v1/users/excel/download")
    public void downloadExcel(User user,HttpServletResponse response ) throws IOException {
        System.out.println("ExcelController 탐..excel/download >>  " + user);
        excelService.excelDownload(user, response);

    }
}
