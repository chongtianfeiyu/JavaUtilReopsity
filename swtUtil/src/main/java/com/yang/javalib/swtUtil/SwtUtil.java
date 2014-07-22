package com.yang.javalib.swtUtil;

import org.apache.log4j.Logger;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;

import com.yang.javalib.swtUtil.common.vo.PromptStr;
import com.yang.javalib.swtUtil.ui.CommonDateTimeSel;
import com.yang.javalib.swtUtil.ui.PromptSimpleUi;

/**
 * @Description: 
 * @author yangxiaodon
 * @Email  coder.yang2010@gmail.com
 * @date  2013-2-24 下午8:18:53 
 */
public class SwtUtil {

	private static final Logger log = Logger.getLogger(SwtUtil.class);

	/**
	 * @Description: TODO
	 * @param args
	 */
	public static void main(String[] args) {
		//		/SwtUtil.prompt("输入测试 ! 对话框 ");
		//SwtUtil.alertMsg(new Shell(), "desfsd");
		SwtUtil.chooseCalTime(100, 200);

	}

	public static int alertMsg(String msgContent) {

		Shell shell = new Shell();
		int result = alertMsg(shell, msgContent);
		shell.dispose();
		return result;
	}

	/**
	 * @Description: 显示信息提示框
	 */
	public static int alertMsg(Shell parent, String msgContent) {
		MessageBox msgBox = new MessageBox(parent, SWT.TITLE);
		msgBox.setText("提示信息");
		msgBox.setMessage(msgContent); //(操作完成后此窗口自动关闭,请勿手功关闭!!)
		return msgBox.open();
	}

	/**
	 * @Description: 弹出输入框,并返回 输入信息. 
	 * 与此同时程序等待输出 结果.
	 * @return
	 */
	public static String prompt(final String title) {
		final PromptStr promptStr = new PromptStr();
		Display.getDefault().syncExec(new Runnable() {
			public void run() {
				Display display = Display.getDefault();
				PromptSimpleUi promptSimpleUi = new PromptSimpleUi(display,
						title);
				promptSimpleUi.setPromptStr(promptStr);
				promptSimpleUi.open();
				promptSimpleUi.layout();
				while (!promptSimpleUi.isDisposed()) {
					if (!display.readAndDispatch()) {
						display.sleep();
					}
				}
			}
		});
		log.info(promptStr.getPromptString());
		return promptStr.getPromptString();

	}

	/**
	 * @Description: 弹出时间选择控件 时间格式 :2013-2-25 10:57:00
	 * @param x 控件显示位置
	 * @param y 控件显示位置
	 * @return
	 */
	public static String chooseCalTime(final int x, final int y) {
		final PromptStr promptStr = new PromptStr();

		Display.getDefault().syncExec(new Runnable() {
			public void run() {
				Display display = Display.getDefault();
				CommonDateTimeSel CommonDateTimeSel = new CommonDateTimeSel(
						display);
				CommonDateTimeSel.setLocation(x, y);
				CommonDateTimeSel.setPromptStr(promptStr);
				CommonDateTimeSel.open();
				CommonDateTimeSel.layout();
				while (!CommonDateTimeSel.isDisposed()) {
					if (!display.readAndDispatch()) {
						display.sleep();
					}
				}
			}
		});

		log.info("得到的时间值为:" + promptStr.getPromptString());

		return promptStr.getPromptString(); //TODO. 
	}

}
