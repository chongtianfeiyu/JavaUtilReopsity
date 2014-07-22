package com.yang.javalib.ireport.jasperReports;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.HtmlExporter;
import net.sf.jasperreports.engine.export.JRHtmlExporter;
import net.sf.jasperreports.engine.export.JRHtmlExporterParameter;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.export.JRXlsExporterParameter;
import net.sf.jasperreports.engine.util.FileBufferedWriter;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;

import com.yang.javalib.ioUtil.rwopt.BufferWriterUtil;
import com.yang.javalib.ireport.jasperReports.dto.TestJavaBean;

public class JasperReportUtil {
	private static final String SUBREPORT_DIR="temp";


	/**
	 * @Description: 
	 * @param jasperTempFilePath jasper模板文件位置 
	 * @param collection 集合数据
	 * @return 返回JasperPrint,
	 * @throws FileNotFoundException
	 * @throws JRException
	 * JasperPrint
	 * @throws
	 */
	public static JasperPrint fullJRBeanCollectionDataSource(String jasperTempFilePath,Collection<?> beanCollection ) throws FileNotFoundException, JRException {
		return fullJRBeanCollectionDataSource(jasperTempFilePath,beanCollection,null);
	}

	public static JasperPrint fullJRBeanCollectionDataSource(String jasperTempFilePath,Collection<?> beanCollection ,Map<String, ? extends Object> params) throws FileNotFoundException, JRException {

		HashMap<String, Object> parameters1 = new HashMap<String, Object>();
		//======================环境参数
		parameters1.put("SUBREPORT_DIR", SUBREPORT_DIR);
		
		//======================自定义参数
		if(params!=null && params.size()>0)
			parameters1.putAll(params); 
		 
		JasperReport jasperReport = getJasperReportByFile(jasperTempFilePath);
		 
		//       chatListsub.add(chat10); chatListsub.add(chat11); chatListsub.add(chat12);   chatListsub.add(chats1);
		//        chatListsub.add(chat20); chatListsub.add(chat21); chatListsub.add(chat22); chatListsub.add(chats2);

		//parameters1.put("MyDatasource", new JRBeanCollectionDataSource(chatListsub));
		JasperPrint	print = JasperFillManager.fillReport(jasperReport, parameters1, new JRBeanCollectionDataSource(beanCollection));
		return print;
	}
	
	
	public static JasperPrint fullJDBCDataSource(String jasperTempFilePath, JRDataSource dataSource)
			throws FileNotFoundException, JRException {
		return fullJDBCDataSource(jasperTempFilePath, dataSource, null);
	}

	public static JasperPrint fullJDBCDataSource(String jasperTempFilePath, JRDataSource dataSource,
			Map<String, ? extends Object> params) throws FileNotFoundException, JRException {
		HashMap<String, Object> parameters1 = new HashMap<String, Object>();
		//======================环境参数
		parameters1.put("SUBREPORT_DIR", SUBREPORT_DIR);
		//======================自定义参数
		if (params != null && params.size() > 0)
			parameters1.putAll(params);

		JasperReport jasperReport = getJasperReportByFile(jasperTempFilePath);
		JasperPrint print = JasperFillManager.fillReport(jasperReport, parameters1, dataSource);

		return print;
	}


	public static JasperPrint fullJDBCConnection(String jasperTempFilePath, Connection conn) throws FileNotFoundException,
			JRException {
		return fullJDBCConnection(jasperTempFilePath, conn, null);

	}
	

	public static JasperPrint fullJDBCConnection(String jasperTempFilePath, Connection conn, Map<String, ? extends Object> params)
			throws FileNotFoundException, JRException {
		HashMap<String, Object> parameters1 = new HashMap<String, Object>();
		//======================环境参数
		parameters1.put("SUBREPORT_DIR", SUBREPORT_DIR);
		//======================自定义参数
		if (params != null && params.size() > 0)
			parameters1.putAll(params);

		JasperReport jasperReport = getJasperReportByFile(jasperTempFilePath);
		JasperPrint print = JasperFillManager.fillReport(jasperReport, parameters1, conn);
		return print;

	}
   
	
	/**
	 * @throws JRException 
	 * @Description: 编译模板jrxml文件变成类 
	 * @return
	 * JasperReport
	 * @throws
	 */
	public static JasperReport getJasperReportBycompileFile(String jrxmlFilePath) throws JRException {
		// 编译模板文件变成类   PersonAction.class.getResource("/").toString().substring(6) +"person.jrxml"
		JasperReport jasperReport = (JasperReport) JasperCompileManager.compileReport(jrxmlFilePath); 
		return jasperReport;

	}
	
	/**
	 * @Description: 加载 jasper模板文件(已经编译过的.)
	 * @param jasperTempFilePath
	 * @return
	 * @throws FileNotFoundException
	 * @throws JRException
	 * JasperReport
	 * @throws
	 */
	public static JasperReport getJasperReportByFile(String jasperTempFilePath) throws FileNotFoundException, JRException { 
		InputStream is = new FileInputStream(jasperTempFilePath);
		JasperReport jasperReport = (JasperReport) JRLoader.loadObject(is); 
		return jasperReport;
	}
	
   
/**
 * @Description: 使用jasper预览器, 查看报表(有UI界面的.)
 * @param print
 * void
 * @throws
 */
public static void JasperViewer(JasperPrint print){

		JasperViewer.viewReport(print, false); 
   }

	/**
	 * @Description: 生成html的信息
	 * @param print
	 * @return
	 * @throws JRException
	 * byte[]
	 * @throws
	 */
	public byte[] generateHtml(JasperPrint print) throws JRException {
		JRHtmlExporter exporter = new JRHtmlExporter();
		ByteArrayOutputStream oStream = new ByteArrayOutputStream();
	 
			exporter.setParameter(JRHtmlExporterParameter.IS_USING_IMAGES_TO_ALIGN, Boolean.FALSE);
			exporter.setParameter(JRExporterParameter.JASPER_PRINT, print);
			exporter.setParameter(JRExporterParameter.CHARACTER_ENCODING, "GBK");
			exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, oStream);
			exporter.exportReport();
			byte[] bytes = oStream.toByteArray();
			return bytes; 
	}
	

	public byte[] generateHtmlStr(JasperPrint print) throws JRException {
		   ByteArrayOutputStream oStream = new ByteArrayOutputStream(); 
			exportHtmlReport(print,oStream); 
			byte[] bytes = oStream.toByteArray();
			return bytes; 
	}
	
	

	/**
	 * @Description: 以html形式显示  
	 * @param jasperPrint
	 * @throws JRException
	 * void
	 * @throws
	 */
	public static String JasperViewerHtml(JasperPrint jasperPrint) throws JRException { 
		StringBuffer sb = new StringBuffer(); 
		exportHtmlReport(jasperPrint,sb);
		return sb.toString();
	}
	
	/**
	 * @Description: 生成html的供其他调用
	 * @param jasperPrint
	 * @param OUTPUT_STRING_BUFFER
	 * @throws JRException
	 * void
	 * @throws
	 */
	private static void exportHtmlReport(JasperPrint jasperPrint,Object OUTPUT_STRING_BUFFER) throws JRException{
		JRHtmlExporter exporter = new JRHtmlExporter();
		exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
		exporter.setParameter(JRExporterParameter.OUTPUT_STRING_BUFFER, OUTPUT_STRING_BUFFER);
		//exporter.setParameter(JRHtmlExporterParameter.IMAGES_URI, "../servlets/image?image=");
		//报表边框图片设置IS_USING_IMAGES_TO_ALIGN,Boolean.FALSE，不使用图片  
		exporter.setParameter(JRHtmlExporterParameter.IS_USING_IMAGES_TO_ALIGN, Boolean.FALSE);
		exporter.setParameter(JRExporterParameter.CHARACTER_ENCODING, "UTF-8"); //"GBK"
		/*exporter.setParameter(JRHtmlExporterParameter.HTML_HEADER, ""); 
		exporter.setParameter(JRHtmlExporterParameter.BETWEEN_PAGES_HTML, ""); 
		exporter.setParameter(JRHtmlExporterParameter.HTML_FOOTER, "");*/
		exporter.exportReport();
	}
	
   /**
 * @Description: 产生PDF文件. 把生成 的byte进行封装.
 * @param jasperPrint
 * @param outputPdfFilePath
 * @return
 * @throws JRException
 * @throws IOException
 * File
 * @throws
 */
public static File exportPdfReportFile(JasperPrint jasperPrint,String outputPdfFilePath) throws JRException, IOException{
	   File fi =new File(outputPdfFilePath);
	   byte[]  bytes =JasperExportManager.exportReportToPdf(jasperPrint);
	   FileOutputStream fileOutputStream =new FileOutputStream(fi) ;
	   fileOutputStream.write(bytes);  
	   fileOutputStream.flush();
	   fileOutputStream.close();
	   return fi; 
   }

	/**
	 * @Description:  生成pdf报表
	 * @param jasperPrint
	 * @param OUTPUT_STRING_BUFFER
	 * @return
	 * @throws JRException
	 * byte[]
	 * @throws
	 */
	public static byte[] exportPdfReport(JasperPrint jasperPrint) throws JRException{ 
		   return JasperExportManager.exportReportToPdf(jasperPrint);
	}
	
	
	/**
	 * @Description: 产生xls文件. 把生成 的byte进行封装.
	 * @param jasperPrint
	 * @param outputPdfFilePath
	 * @return
	 * @throws JRException
	 * @throws IOException
	 * File
	 * @throws
	 */
	public static File exportExcelReportFile(JasperPrint jasperPrint, String outputPdfFilePath) throws JRException, IOException {
		File fi = new File(outputPdfFilePath);
		byte[] bytes = exportExcelReport(jasperPrint);
		FileOutputStream fileOutputStream = new FileOutputStream(fi);
		fileOutputStream.write(bytes);
		fileOutputStream.flush();
		fileOutputStream.close();

		return fi;
	}
	/**
	 * @Description: 生成excel报表
	 * @param jasperPrint
	 * @return
	 * @throws JRException
	 * byte[]
	 * @throws
	 */
	public static byte[] exportExcelReport(JasperPrint jasperPrint) throws JRException {

		JRXlsExporter exporter = new JRXlsExporter(); // Excel
		ByteArrayOutputStream oStream = new ByteArrayOutputStream();

		exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
		exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, oStream);
		exporter.setParameter(JRXlsExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS, Boolean.TRUE);
		exporter.setParameter(JRXlsExporterParameter.IS_ONE_PAGE_PER_SHEET, Boolean.FALSE);
		exporter.setParameter(JRXlsExporterParameter.IS_WHITE_PAGE_BACKGROUND, Boolean.FALSE);
		exporter.exportReport();
		byte[] bytes = oStream.toByteArray();
		return bytes;
	}

}
