package com.yang.javalib.swtUtil.ui;

import org.eclipse.swt.SWT;
import org.eclipse.swt.browser.Browser;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

public class TestBrowser {

	private Shell sShell = null;
	private Browser browser = null;

	private void createBrowser() {
		browser = new Browser(sShell, SWT.MOZILLA);
	}

	public static void main(String[] args) {
		Display display = Display.getDefault();
		TestBrowser thisClass = new TestBrowser();
		thisClass.createSShell();
		thisClass.sShell.open();
		thisClass.sShell.setMaximized(true);
		thisClass.browser.setUrl("http://www.google.com/");
		if (thisClass.browser.getWebBrowser() == null)
			throw new RuntimeException("getWebBrowser error.");
		while (!thisClass.sShell.isDisposed()) {
			if (!display.readAndDispatch())
				display.sleep();
		}
		display.dispose();
	}

	private void createSShell() {
		sShell = new Shell();
		sShell.setText("Shell");
		createBrowser();
		sShell.setSize(new Point(600, 400));
		sShell.setLayout(new FillLayout());
	}

}