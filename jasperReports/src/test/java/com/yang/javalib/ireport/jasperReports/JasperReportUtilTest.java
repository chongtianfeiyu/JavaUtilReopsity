package com.yang.javalib.ireport.jasperReports;

import static org.junit.Assert.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperPrint;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.yang.javalib.ireport.jasperReports.dto.TestJavaBean;

public class JasperReportUtilTest {
	
	private List<TestJavaBean> list ;

	@Before
	public void setUp() throws Exception {
	  list = new ArrayList<TestJavaBean>();
		for (int i = 0; i < 10; i++) {
			TestJavaBean bean = new TestJavaBean();
			bean.setNo("" + i);
			bean.setName("test" + i);
			bean.setAge(i + 18);
			list.add(bean);
		}
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void test预览集合数据源() throws FileNotFoundException, JRException {
		 
		String jasperTempFilePath ="doc/teach1.jasper";
		
		JasperPrint jasperPrint =	JasperReportUtil.fullJRBeanCollectionDataSource(jasperTempFilePath, list);
		JasperReportUtil.JasperViewer(jasperPrint);
	}



	/**
	 * @Description: OK, 这个可以的. html返回string类型的
	 * @throws FileNotFoundException
	 * @throws JRException
	 * void
	 * @throws
	 */
	@Test
	public void test预览HTMLSty() throws FileNotFoundException, JRException {
		 
		String jasperTempFilePath ="doc/teach1.jasper"; 
		JasperPrint jasperPrint =	JasperReportUtil.fullJRBeanCollectionDataSource(jasperTempFilePath, list);
		 
		String tt =JasperReportUtil.JasperViewerHtml(jasperPrint);
		System.out.println(tt);
		//JasperReportUtil.JasperViewer(jasperPrint);
	}
	

	/**
	 * @Description: ok , 可以的 赞一个. 哈哈
	 * @throws JRException
	 * @throws IOException
	 * void
	 * @throws
	 */
	@Test
	public void test预览pdfFile() throws JRException, IOException {
		 
		String jasperTempFilePath ="doc/teach1.jasper"; 
		JasperPrint jasperPrint =	JasperReportUtil.fullJRBeanCollectionDataSource(jasperTempFilePath, list); 
		File tt =JasperReportUtil.exportPdfReportFile(jasperPrint,"temp/tt.pdf"); 
		System.out.println(tt);
		//JasperReportUtil.JasperViewer(jasperPrint);
	}
	

	/**
	 * @Description: OK, 已经可以 了.
	 * @throws JRException
	 * @throws IOException
	 * void
	 * @throws
	 */
	@Test
	public void test预览pdfFile参数传递() throws JRException, IOException {
		 
		String jasperTempFilePath ="doc/teach1.jasper"; 
		
		Map<String, Object> params =new HashMap<String, Object>();	
		params.put("num", 22222);
		
		JasperPrint jasperPrint =	JasperReportUtil.fullJRBeanCollectionDataSource(jasperTempFilePath, list,params); 
		File tt =JasperReportUtil.exportPdfReportFile(jasperPrint,"temp/tt.pdf"); 
		System.out.println(tt);
		//JasperReportUtil.JasperViewer(jasperPrint);
	}
	
	@Test
	public void test预览pdfFile参数传递conn() throws JRException, IOException, ClassNotFoundException, SQLException {
		

		Class.forName("com.mysql.jdbc.Driver");
		Connection conn = DriverManager.getConnection("jdbc:mysql://192.168.1.102:3306/testqb", "root", "123123");

		
		String jasperTempFilePath ="doc/teach1c参数connsubSet.jasper"; 
		
		Map<String, Object> params =new HashMap<String, Object>();	
		params.put("num", 101);
		params.put("REPORT_CONNECTION", conn);
		
		JasperPrint jasperPrint =	JasperReportUtil.fullJRBeanCollectionDataSource(jasperTempFilePath, list,params); 
		File tt =JasperReportUtil.exportPdfReportFile(jasperPrint,"temp/teach1c参数connsubSet.pdf"); 
		System.out.println(tt);
		//JasperReportUtil.JasperViewer(jasperPrint);
	}
	

	@Test
	public void test预览pdfFile参数传递datasource() throws JRException, IOException {
		 // $P{REPORT_CONNECTION}
		
		String jasperTempFilePath ="doc/teach1.jasper"; 
		
		Map<String, Object> params =new HashMap<String, Object>();	
		params.put("num", 22222);
		
		JasperPrint jasperPrint =	JasperReportUtil.fullJRBeanCollectionDataSource(jasperTempFilePath, list,params); 
		File tt =JasperReportUtil.exportPdfReportFile(jasperPrint,"temp/tt.pdf"); 
		System.out.println(tt);
		//JasperReportUtil.JasperViewer(jasperPrint);
	}
	
	/**
	 * @Description: OK. 成功了 赞. 
	 * @throws JRException
	 * @throws IOException
	 * void
	 * @throws
	 */
	@Test
	public void test预览ExcelFile() throws JRException, IOException {
		 
		String jasperTempFilePath ="doc/teach1.jasper"; 
		JasperPrint jasperPrint =	JasperReportUtil.fullJRBeanCollectionDataSource(jasperTempFilePath, list); 
		File tt =JasperReportUtil.exportExcelReportFile(jasperPrint,"temp/tt.xls"); 
		System.out.println(tt);
		//JasperReportUtil.JasperViewer(jasperPrint);
	}
	
	
	/**
	 * @Description: 可以, 传入的是Connection,然后执行模板中的SQL.(测试传SQL的方法)
	 * TODO 还要测试外部传入参数, 和外部传入执行SQL
	 * @throws JRException
	 * @throws IOException
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 * void
	 * @throws
	 */
	@Test
	public void testFulljdbc() throws JRException, IOException, ClassNotFoundException, SQLException {

		String jasperTempFilePath = "doc/任务收益排行(日表).jasper";

		Class.forName("com.mysql.jdbc.Driver");
		Connection conn = DriverManager.getConnection("jdbc:mysql://192.168.1.102:3306/testqb", "root", "123123");

		JasperPrint jasperPrint = JasperReportUtil.fullJDBCConnection(jasperTempFilePath, conn);
		File tt = JasperReportUtil.exportPdfReportFile(jasperPrint, "temp/tt1.pdf");
		System.out.println(tt);
	}
	
	
	

	@Test
	public void test编译模板() throws FileNotFoundException, JRException {
		JasperReportUtil.getJasperReportBycompileFile("doc/teach1.jrxml");
	}
}
