package com.emgram.kr.dobby.service;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

public class WorkbookToString {

    public static String workbookToString(Workbook workbook) {
        StringBuilder sb = new StringBuilder();

        sb.append("Workbook Information: \n");
        sb.append("Number of Sheets: ").append(workbook.getNumberOfSheets()).append("\n");

        // 각 시트에 대한 정보 출력
        for (int sheetIndex = 0; sheetIndex < workbook.getNumberOfSheets(); sheetIndex++) {
            Sheet sheet = workbook.getSheetAt(sheetIndex);
            sb.append("Sheet ").append(sheetIndex + 1).append(":\n");
            sb.append("  Number of Rows: ").append(sheet.getPhysicalNumberOfRows()).append("\n");
            sb.append("  Number of Columns: ").append(sheet.getRow(0).getPhysicalNumberOfCells()).append("\n");

            // 각 셀의 내용 출력
            for (int rowIndex = 0; rowIndex < sheet.getPhysicalNumberOfRows(); rowIndex++) {
                Row row = sheet.getRow(rowIndex);
                sb.append("  Row ").append(rowIndex + 1).append(":\n");
                for (int colIndex = 0; colIndex < row.getPhysicalNumberOfCells(); colIndex++) {
                    Cell cell = row.getCell(colIndex);
                    sb.append("    Column ").append(colIndex + 1).append(": ").append(cellToString(cell)).append("\n");
                }
            }

            // 추가적인 시트 정보가 필요하면 여기에 추가할 수 있습니다.
        }

        return sb.toString();
    }

    private static String cellToString(Cell cell) {
        if (cell == null) {
            return "";
        }

        switch (cell.getCellType()) {
            case STRING:
                return cell.getStringCellValue();
            case NUMERIC:
                return Double.toString(cell.getNumericCellValue());
            case BOOLEAN:
                return Boolean.toString(cell.getBooleanCellValue());
            case FORMULA:
                return cell.getCellFormula();
            default:
                return "";
        }
    }
}
