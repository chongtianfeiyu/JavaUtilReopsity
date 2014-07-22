package com.yang.javalib.poi;

import static org.junit.Assert.fail;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.yang.javalib.poi.pojo.PersonInfo;
public class ExcelInputTest {

	  
    private HSSFWorkbook wb = null;// book [includes sheet]  
  
    private HSSFSheet sheet = null;  
  
    private HSSFRow row = null;  
  
    private int sheetNum = 0; // 第sheetnum个工作表  
  
    private int rowNum = 0;  
  
    private FileInputStream fis = null;  
  
    private File file = null;  
  
    public ExcelInputTest() {  
    }  
  
    public ExcelInputTest(File file) {  
        this.file = file;  
    }  
  
    public void setRowNum(int rowNum) {  
        this.rowNum = rowNum;  
    }  
  
    public void setSheetNum(int sheetNum) {  
        this.sheetNum = sheetNum;  
    }  
  
    public void setFile(File file) {  
        this.file = file;  
    }  
  
    /** 
     * 读取excel文件获得HSSFWorkbook对象 
     */  
    public void open() throws IOException {  
        fis = new FileInputStream(file);  
        wb = new HSSFWorkbook(new POIFSFileSystem(fis));  
        fis.close();  
    }  
  
    /** 
     * 返回sheet表数目 
     *  
     * @return int 
     */  
    public int getSheetCount() {  
        int sheetCount = -1;  
        sheetCount = wb.getNumberOfSheets();  
        return sheetCount;  
    }  
  
    /** 
     * sheetNum下的记录行数 
     *  
     * @return int 
     */  
    public int getRowCount() {  
        if (wb == null)  
            System.out.println("=============>WorkBook为空");  
        HSSFSheet sheet = wb.getSheetAt(this.sheetNum);  
        int rowCount = -1;  
        rowCount = sheet.getLastRowNum();  
        return rowCount;  
    }  
  
    /** 
     * 读取指定sheetNum的rowCount 
     *  
     * @param sheetNum 
     * @return int 
     */  
    public int getRowCount(int sheetNum) {  
        HSSFSheet sheet = wb.getSheetAt(sheetNum);  
        int rowCount = -1;  
        rowCount = sheet.getLastRowNum();  
        return rowCount;  
    }  
  
    /** 
     * 得到指定行的内容 
     *  
     * @param lineNum 
     * @return String[] 
     */  
    public String[] readExcelLine(int lineNum) {  
        return readExcelLine(this.sheetNum, lineNum);  
    }  
  
    /** 
     * 指定工作表和行数的内容 
     *  
     * @param sheetNum 
     * @param lineNum 
     * @return String[] 
     */  
    public String[] readExcelLine(int sheetNum, int lineNum) {  
        if (sheetNum < 0 || lineNum < 0)  
            return null;  
        String[] strExcelLine = null;  
        try {  
            sheet = wb.getSheetAt(sheetNum);  
            row = sheet.getRow(lineNum);  
  
            int cellCount = row.getLastCellNum();  
            strExcelLine = new String[cellCount + 1];  
            for (int i = 0; i <= cellCount; i++) {  
                strExcelLine[i] = readStringExcelCell(lineNum, i);  
            }  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
        return strExcelLine;  
    }  
  
    /** 
     * 读取指定列的内容 
     *  
     * @param cellNum 
     * @return String 
     */  
    public String readStringExcelCell(int cellNum) {  
        return readStringExcelCell(this.rowNum, cellNum);  
    }  
  
    /** 
     * 指定行和列编号的内容 
     *  
     * @param rowNum 
     * @param cellNum 
     * @return String 
     */  
    public String readStringExcelCell(int rowNum, int cellNum) {  
        return readStringExcelCell(this.sheetNum, rowNum, cellNum);  
    }  
  
    /** 
     * 指定工作表、行、列下的内容 
     *  
     * @param sheetNum 
     * @param rowNum 
     * @param cellNum 
     * @return String 
     */  
    @SuppressWarnings("deprecation")  
    public String readStringExcelCell(int sheetNum, int rowNum, int cellNum) {  
        if (sheetNum < 0 || rowNum < 0)  
            return "";  
        String strExcelCell = "";  
        try {  
            sheet = wb.getSheetAt(sheetNum);  
            row = sheet.getRow(rowNum);  
  
            if (row.getCell((short) cellNum) != null) {  
                // 判断cell的类型并统一转换为string类型  
                switch (row.getCell((short) cellNum).getCellType()) {  
                case HSSFCell.CELL_TYPE_FORMULA:  
                    strExcelCell = "FORMULA ";  
                    break;  
                case HSSFCell.CELL_TYPE_NUMERIC: {  
                    strExcelCell = String.valueOf(row.getCell((short) cellNum)  
                            .getNumericCellValue());  
                }  
                    break;  
                case HSSFCell.CELL_TYPE_STRING:  
                    strExcelCell = row.getCell((short) cellNum)  
                            .getStringCellValue();  
                    break;  
                case HSSFCell.CELL_TYPE_BLANK:  
                    strExcelCell = "";  
                    break;  
                case HSSFCell.CELL_TYPE_BOOLEAN:  
                    strExcelCell = String.valueOf(row.getCell((short) cellNum));  
                default:  
                    strExcelCell = "";  
                    break;  
                }  
            }  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
        return strExcelCell;  
    }  
  
    /**
     * @Description: 赞, OK, 这个是可以的. 
     * @param args
     * void
     * @throws
     */
    public static void main(String args[]) {  
        File file = new File("temp/workbook.xls");  
        ExcelInputTest readExcel = new ExcelInputTest(file);  
        try {  
            readExcel.open();  
        } catch (IOException e) {  
            e.printStackTrace();  
        }  
        readExcel.setSheetNum(0); // 设置读取索引为0的工作表  
        // 总行数  
        int count = readExcel.getRowCount();  
        for (int i = 0; i <= count; i++) {  
            String[] rows = readExcel.readExcelLine(i);  
            for (int j = 0; j < rows.length; j++) {  
                System.out.print(rows[j] + "   ");  
            }  
            System.out.print("\n");  
        }  
    }  
}
