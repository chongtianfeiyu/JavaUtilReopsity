package com.yang.javalib.swtUtil.ui;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

/**
 * @Description: 托盘程序
 * @author yangxiaodon
 * @Email  coder.yang2010@gmail.com
 * @date  2013-3-13 上午11:30:21 
 */
public class TrayTest extends Shell {

	/**
	 * Launch the application.
	 * @param args
	 */
	public static void main(String args[]) {
		try {
			Display display = Display.getDefault();
			TrayTest shell = new TrayTest(display);
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

	/**
	 * Create the shell.
	 * @param display
	 */
	public TrayTest(Display display) {
		super(display, SWT.SHELL_TRIM);
		createContents();
	}

	/**
	 * Create contents of the shell.
	 */
	protected void createContents() {
		setText("SWT Application");
		setSize(450, 300);

	}

	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}

}
