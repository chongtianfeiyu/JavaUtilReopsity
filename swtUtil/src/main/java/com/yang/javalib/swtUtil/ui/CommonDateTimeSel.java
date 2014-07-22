package com.yang.javalib.swtUtil.ui;

import org.apache.log4j.Logger;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.DateTime;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

import com.yang.javalib.swtUtil.common.vo.PromptStr;
 
/**
 * @Description: 通用　时间选择控件
 * @author yangxiaodon
 * @Email  coder.yang2010@gmail.com
 * @date  2013-2-25 上午8:46:29 
 */
public class CommonDateTimeSel extends Shell {

	private static final Logger log = Logger.getLogger(CommonDateTimeSel.class);

	private PromptStr promptStr;

	/**
	 * Launch the application.
	 * @param args
	 */
	public static void main(String args[]) {
		try {
			Display display = Display.getDefault();
			CommonDateTimeSel shell = new CommonDateTimeSel(display);
			shell.open();
			shell.layout();
			while (!shell.isDisposed()) {
				if (!display.readAndDispatch()) {
					display.sleep();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public PromptStr getPromptStr() {
		return promptStr;
	}

	/**
	 * @Description: 这个方法 一定要有. 
	 * @param promptStr
	 */
	public void setPromptStr(PromptStr promptStr) {
		this.promptStr = promptStr;
	}

	private DateTime date_cla = null;

	private DateTime date_time = null;

	/**
	 * Create the shell.
	 * @param display
	 */
	public CommonDateTimeSel(Display display) {
		super(display, SWT.SHELL_TRIM);

		date_cla = new DateTime(this, SWT.CALENDAR | SWT.BORDER);
		date_cla.setBounds(10, 10, 271, 143);

		date_time = new DateTime(this, SWT.TIME | SWT.SHORT);
		date_time.setBounds(10, 161, 271, 21);

		Button button = new Button(this, SWT.NONE);
		button.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				String temp = "";
				temp = date_cla.getYear() + "-" + (date_cla.getMonth() + 1)
						+ "-" + date_cla.getDay() + " " + date_time.getHours()
						+ ":" + date_time.getMinutes() + ":00";

				log.info(" Calendar date selected (MM/DD/YYYY) =  "
						+ (date_cla.getMonth() + 1) + " / " + date_cla.getDay()
						+ " / " + date_cla.getYear());

				log.info(" Time selected (HH:MM) =  " + date_time.getHours()
						+ " : " + date_time.getMinutes());
				promptStr.setPromptString(temp);
				CommonDateTimeSel.this.close();
			}
		});
		button.setBounds(129, 187, 72, 22);
		button.setText("确定");

		Button button_1 = new Button(this, SWT.NONE);
		button_1.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				CommonDateTimeSel.this.close();
			}
		});
		button_1.setText("取消");
		button_1.setBounds(209, 187, 72, 22);
		createContents();
	}

	/**
	 * Create contents of the shell.
	 */
	protected void createContents() {
		setText("SWT Application");
		setSize(298, 253);
	}

	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}
}
