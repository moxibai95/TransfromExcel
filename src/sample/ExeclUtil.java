package sample;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.CellReference;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;


import java.io.*;

public class ExeclUtil {
    public static Workbook InputExcel(File file) {
        Workbook workBook = null;
        InputStream inputStream = null;
        try {
            inputStream = new FileInputStream(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        String fileName = file.getName();
        String fileType = fileName.substring(fileName.lastIndexOf("."));
        try {
            if (".xls".equals(fileType)) {
                workBook = new HSSFWorkbook(inputStream);
            }else if (".xlsx".equals(fileType)){
                workBook = new XSSFWorkbook(inputStream);
            }
        }catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return workBook;
    }

    public static void OutputExcel(Workbook workbook, String fileName) {
        FileOutputStream fileOutputStream = null;
        try {
            fileOutputStream = new FileOutputStream("D:/" + fileName);
            workbook.write(fileOutputStream);
            fileOutputStream.flush();

        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            try {
                fileOutputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void hanleExcel(Workbook workbook) {
        CellStyle xssfCellStyle = workbook.createCellStyle();
        xssfCellStyle.setFillForegroundColor(IndexedColors.RED.getIndex());
        xssfCellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        String firstName = null;
        String SecondName = null;
        Integer sheetNumber = workbook.getNumberOfSheets();
        if (sheetNumber != 0) {
            //循环excel sheet
            for (int i = 1; i <= sheetNumber; i++) {
                Sheet sheet = workbook.getSheetAt(i - 1);
                //获取该sheet下最大的行数
                int rowNum = sheet.getPhysicalNumberOfRows();
                if (rowNum != 0) {
                    for (int j = 1; j <= rowNum; j++) {
                        Row row = sheet.getRow(j - 1);
                        Integer cellNum = row.getPhysicalNumberOfCells();
                        for (int k = 1; k <= cellNum; k++) {
                            String cellName = CellReference.convertNumToColString(k - 1);
                            if ("A".equals(cellName)) {
                                Cell cell = row.getCell(k - 1);
                                cell.setCellType(CellType.STRING);
                                if ("北京".equals(cell.getStringCellValue())) {
                                    cell.setCellStyle(xssfCellStyle);
                                }

                            }
                        }

                    }
                }
            }
        }


    }

}
