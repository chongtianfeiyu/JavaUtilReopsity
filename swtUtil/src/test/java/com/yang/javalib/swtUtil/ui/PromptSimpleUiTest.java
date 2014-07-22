package com.yang.javalib.swtUtil.ui;

import static org.junit.Assert.*;

import org.eclipse.swt.widgets.Display;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.yang.javalib.swtUtil.common.vo.PromptStr;

public class PromptSimpleUiTest {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	/**
	 * @Description: 参数输入界面类
	 *  可以考虑加一个 事件监听当选择成功时,执行.
	 */
	/**
	 * @Description: TODO
	 */
	@Test
	public void testUserCase1() { 
		Display display = Display.getDefault();
	 
		final PromptStr promptStr = new PromptStr();
		 //同步执行控制
		Display.getDefault().syncExec(new Runnable() {
			public void run() { 
				Display display = Display.getDefault();
				PromptSimpleUi shell = new PromptSimpleUi(display); 
				//TemplateList templateList = new TemplateList(display);
				shell.setPromptStr(promptStr);
				shell.open();
				shell.layout(); 
				while (!shell.isDisposed()) {
					if (!display.readAndDispatch()) {
						display.sleep();
					}
				} 
			}
		}); 
		 
		String _tempKey = promptStr.getPromptString();
		System.out.println("输出结果!"+_tempKey);
		
	}

}
