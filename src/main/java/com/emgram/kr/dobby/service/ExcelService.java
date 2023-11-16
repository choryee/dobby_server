package com.emgram.kr.dobby.service;

import com.emgram.kr.dobby.dao.Employee_adminDao;
import com.emgram.kr.dobby.dto.login.User;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@Service
public class ExcelService {

    @Autowired
    private Employee_adminDao Employee_adminDao;

    public void excelDownload(User user, HttpServletResponse response) throws IOException {
        System.out.println("ExcelService 탐..excel/download >>  " + user);

        List<User> list = Employee_adminDao.getAllUsers();
        System.out.println("ExcelService.. >>"+ list);

        try {
            //Workbook wb = new HSSFWorkbook();
            Workbook wb = new XSSFWorkbook();
            Sheet sheet = wb.createSheet("첫번째 시트");
            //행,열,열번호
            Row row = null;
            Cell cell = null;
            int rowNum = 0;

            // Header
            row = sheet.createRow(rowNum++);
            cell = row.createCell(0);
            cell.setCellValue("사원번호");

            cell = row.createCell(1);
            cell.setCellValue("권한");
            cell = row.createCell(2);
            cell.setCellValue("이름");
            cell = row.createCell(3);
            cell.setCellValue("메모");


            // Body
//        for (int i=0; i<3; i++) {
//            row = sheet.createRow(rowNum++);
//            cell = row.createCell(0);
//            cell.setCellValue(i);
//            cell = row.createCell(1);
//            cell.setCellValue(i+"_name");
//            cell = row.createCell(2);
//            cell.setCellValue(i+"_title");
//        }
            for(User excelData : list){
                row=sheet.createRow(rowNum++);

                cell=row.createCell(0);
                cell.setCellValue(excelData.getEmployee_no());
                cell=row.createCell(1);
                cell.setCellValue(excelData.getRoles());
                cell=row.createCell(2);
                cell.setCellValue(excelData.getName());
                cell=row.createCell(3);
                cell.setCellValue(excelData.getMemo());

            }

            // 컨텐츠 타입과 파일명 지정
//        response.setHeader("Content-Disposition", "attachment;filename=example.xls");
            response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
            response.setHeader("Content-Disposition", "attachment;filename=example.xlsx");


            // Excel File Output
            response.getOutputStream().flush();
            wb.write(response.getOutputStream());

            String workbookInfo = WorkbookToString.workbookToString(wb);
            System.out.println("wb >> "+workbookInfo);
            wb.close();
        }catch (Exception e){
            e.printStackTrace();
        }


    }
}
