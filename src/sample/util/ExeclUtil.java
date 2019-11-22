package sample.util;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.CellReference;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.openxml4j.opc.PackageAccess;
import org.apache.poi.poifs.crypt.EncryptionInfo;
import org.apache.poi.poifs.crypt.EncryptionMode;
import org.apache.poi.poifs.crypt.Encryptor;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;


import java.io.*;
import java.security.GeneralSecurityException;
import java.text.SimpleDateFormat;
import java.util.Date;

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

    public static File hanle(Workbook workbook, Integer begin, Integer last) {
        File file = new File("工作表表头.xlsx");
        Sheet sheet = workbook.getSheetAt(0);
        //获取该sheet下最大的行数
        int rowNum = sheet.getLastRowNum();
        for (int j = 1; j <= rowNum; j++) {
            if (j < begin) {
                sheet.removeRow(sheet.getRow(j));
            }
            if (j > last) {
                sheet.removeRow(sheet.getRow(j));
            }
        }
        if (begin != 1) {
            sheet.shiftRows(begin, last, 1 - begin);
        }
        FileOutputStream os = null;
        try {
            os = new FileOutputStream(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        try {
            workbook.write(os);
            workbook.close();
            os.close();

            //加密?
            EncryptionInfo info = new EncryptionInfo(EncryptionMode.standard);
            Encryptor enc = info.getEncryptor();
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("YYYYMM");
            String password = simpleDateFormat.format(new Date());
            enc.confirmPassword(password);
            OPCPackage opc = OPCPackage.open(file, PackageAccess.READ_WRITE);
            POIFSFileSystem fs = new POIFSFileSystem();
            OutputStream os1 = enc.getDataStream(fs);
            opc.save(os1);
            opc.close();
            FileOutputStream fos = new FileOutputStream(file);
            fs.writeFilesystem(fos);
            fos.close();

        } catch (IOException | InvalidFormatException | GeneralSecurityException e) {
            e.printStackTrace();
        }
        return file;
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
