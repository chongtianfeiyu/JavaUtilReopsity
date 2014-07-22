package com.yang.javalib.poi;

import static org.junit.Assert.fail;

import java.io.File;
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
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.yang.javalib.poi.pojo.PersonInfo;

/**
 * @Description	: TODO
 *
 * @Version		: V1.0 
 * @author		: yangxiaodong
 * @Email		: coder.yang2010@gmail.com
 * @date  		: 2014-5-23 上午11:28:22 
 */
public class ExecelOutputTest {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testExportExcelStringCollectionOfTOutputStreamString() {
		fail("Not yet implemented");
	}
 
	@Test
	public void testExportExcel2003() {
		fail("Not yet implemented");
	}
	

	  
    // 设置cell编码解决中文高位字节截断  
    // private static short XLS_ENCODING = HSSFCell.ENCODING_UTF_16;  
  
    // 定制浮点数格式  
    private static String NUMBER_FORMAT = "#,##0.00";  
  
    // 定制日期格式  
    private static String DATE_FORMAT = "yyyy-mm-dd hh:mm:ss"; // "m/d/yy h:mm"  
    private OutputStream out = null;  
    private HSSFWorkbook workbook = null;  
    private HSSFSheet sheet = null;  
    private HSSFRow row = null;  
  
    public ExecelOutputTest() {  
    }  
  
    /** 
     * 初始化Excel 
     *  
     */  
    public ExecelOutputTest(OutputStream out) {  
        this.out = out;  
        this.workbook = new HSSFWorkbook();  
        this.sheet = workbook.createSheet();  
    }  
  
    /** 
     * 导出Excel文件 
     *  
     * @throws IOException 
     */  
    public void export() throws FileNotFoundException, IOException {  
        try {  
            workbook.write(out);  
            out.flush();  
            out.close();  
        } catch (FileNotFoundException e) {  
            throw new IOException(" 生成导出Excel文件出错! ", e);  
        } catch (IOException e) {  
            throw new IOException(" 写入Excel文件出错! ", e);  
        }  
  
    }  
  
    /** 
     * 增加一行 
     *  
     * @param index 
     *            行号 
     */  
    public void createRow(int index) {  
        this.row = this.sheet.createRow(index);  
    }  
  
    /** 
     * 获取单元格的值 
     *  
     * @param index 
     *            列号 
     */  
    public String getCell(int index) {  
        @SuppressWarnings("deprecation")  
        HSSFCell cell = this.row.getCell((short) index);  
        String strExcelCell = "";  
        if (cell != null) { // add this condition  
            // judge  
            switch (cell.getCellType()) {  
            case HSSFCell.CELL_TYPE_FORMULA:  
                strExcelCell = "FORMULA ";  
                break;  
            case HSSFCell.CELL_TYPE_NUMERIC:  
                strExcelCell = String.valueOf(cell.getNumericCellValue());  
                break;  
            case HSSFCell.CELL_TYPE_STRING:  
                strExcelCell = cell.getStringCellValue();  
                break;  
            case HSSFCell.CELL_TYPE_BLANK:  
                strExcelCell = "";  
                break;  
            default:  
                strExcelCell = "";  
                break;  
            }  
        }  
        return strExcelCell;  
    }  
  
    /** 
     * 设置单元格 
     *  
     * @param index 
     *            列号 
     * @param value 
     *            单元格填充值 
     */  
    public void setCell(int index, int value) {  
        @SuppressWarnings("deprecation")  
        HSSFCell cell = this.row.createCell((short) index);  
        cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);  
        cell.setCellValue(value);  
    }  
  
    /** 
     * 设置单元格 
     *  
     * @param index 
     *            列号 
     * @param value 
     *            单元格填充值 
     */  
    @SuppressWarnings("deprecation")  
    public void setCell(int index, double value) {  
        HSSFCell cell = this.row.createCell((short) index);  
        cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);  
        cell.setCellValue(value);  
        HSSFCellStyle cellStyle = workbook.createCellStyle(); // 建立新的cell样式  
        HSSFDataFormat format = workbook.createDataFormat();  
        cellStyle.setDataFormat(format.getFormat(NUMBER_FORMAT)); // 设置cell样式为定制的浮点数格式  
        cell.setCellStyle(cellStyle); // 设置该cell浮点数的显示格式  
    }  
  
    /** 
     * 设置单元格 
     *  
     * @param index 
     *            列号 
     * @param value 
     *            单元格填充值 
     */  
    public void setCell(int index, String value) {  
        @SuppressWarnings("deprecation")  
        HSSFCell cell = this.row.createCell((short) index);  
        cell.setCellType(HSSFCell.CELL_TYPE_STRING);  
        // cell.setEncoding(XLS_ENCODING);  
        cell.setCellValue(value);  
    }  
  
    /** 
     * 设置单元格 
     *  
     * @param index 
     *            列号 
     * @param value 
     *            单元格填充值 
     */  
    public void setCell(int index, Calendar value) {  
        @SuppressWarnings("deprecation")  
        HSSFCell cell = this.row.createCell((short) index);  
        // cell.setEncoding(XLS_ENCODING);  
        cell.setCellValue(value.getTime());  
        HSSFCellStyle cellStyle = workbook.createCellStyle(); // 建立新的cell样式  
        cellStyle.setDataFormat(HSSFDataFormat.getBuiltinFormat(DATE_FORMAT)); // 设置cell样式为定制的日期格式  
        cell.setCellStyle(cellStyle); // 设置该cell日期的显示格式  
    }  
  
    public static void main(String[] args) {  
        List<PersonInfo> personInfos = new ArrayList<PersonInfo>();  
        for (int i = 1; i <= 10; i++) {  
            PersonInfo p = new PersonInfo();  
            p.setId(i);  
            p.setName("yang" + i);  
            p.setAge(i + 20);  
            p.setBirthday(new Date());  
            if (i % 2 == 0) {  
                p.setSex("male");  
            } else {  
                p.setSex("female");  
            }  
            personInfos.add(p);  
        }  
  
        System.out.println(" 开始导出Excel文件 ");  
        File f = new File("temp/workbook.xls");  
        ExecelOutputTest e = new ExecelOutputTest();  
  
        try {  
            // 传一个输出流给构造函数  
            e = new ExecelOutputTest(new FileOutputStream(f));  
        } catch (FileNotFoundException e1) {  
            e1.printStackTrace();  
        }  
  
        e.createRow(0);  
        e.setCell(0, "编号 ");  
        e.setCell(1, "姓名");  
        e.setCell(2, "年龄");  
        e.setCell(3, "性别");  
        e.setCell(4, "出生日期");  
  
        for (int i = 1; i <= personInfos.size(); i++) {
            e.createRow(i);  
            e.setCell(0, personInfos.get(i - 1).getId());  
            e.setCell(1, personInfos.get(i - 1).getName());  
            e.setCell(2, personInfos.get(i - 1).getAge());  
            e.setCell(3, personInfos.get(i - 1).getSex());  
            SimpleDateFormat sdf = new SimpleDateFormat(  
                    "yyyy年MM月dd日 HH时mm分ss秒 E");  
            String date = sdf.format(personInfos.get(i - 1).getBirthday());  
            e.setCell(4, date);  
        }  
  
        try {  
            e.export();  
            System.out.println(" 导出Excel文件[成功] ");  
        } catch (IOException ex) {  
            System.out.println(" 导出Excel文件[失败] ");  
            ex.printStackTrace();  
        }  
    }  
	

}
