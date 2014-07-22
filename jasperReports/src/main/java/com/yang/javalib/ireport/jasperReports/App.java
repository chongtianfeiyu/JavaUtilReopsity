package com.yang.javalib.ireport.jasperReports;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;

import com.yang.javalib.ireport.jasperReports.dto.TestJavaBean;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) {
    	
    	
    	/*
    	 *File fi =new File("doc/teach1.jasper"); 
    	 *if(fi.exists()){
    		System.out.println("文件存在.!");
    		return; 
    	}*/
    	
    	
		JasperPrint print = null;

		try {
			InputStream is = new FileInputStream("doc/teach1.jasper");
			JasperReport jasperReport = (JasperReport) JRLoader.loadObject(is);
			HashMap<String, Object> parameters1 = new HashMap<String, Object>();
			List<TestJavaBean> list = new ArrayList<TestJavaBean>();
			for (int i = 0; i < 10; i++) {
				TestJavaBean bean = new TestJavaBean();

				bean.setNo("" + i);
				bean.setName("test" + i);
				bean.setAge(i + 18);
				list.add(bean);
			}

			//       chatListsub.add(chat10); chatListsub.add(chat11); chatListsub.add(chat12);   chatListsub.add(chats1);

			//        chatListsub.add(chat20); chatListsub.add(chat21); chatListsub.add(chat22); chatListsub.add(chats2);

			parameters1.put("SUBREPORT_DIR", "temp");
			//parameters1.put("MyDatasource", new JRBeanCollectionDataSource(chatListsub));
			print = JasperFillManager.fillReport(jasperReport, parameters1,
			new JRBeanCollectionDataSource(list));

			JasperViewer.viewReport(print, false);
		} catch (Exception ex) {
			ex.printStackTrace();
		}

	}
}
