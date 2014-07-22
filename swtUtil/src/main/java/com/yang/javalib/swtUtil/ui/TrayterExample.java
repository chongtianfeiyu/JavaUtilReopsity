package com.yang.javalib.swtUtil.ui;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Tray;
import org.eclipse.swt.widgets.TrayItem;

/**
 * @Description: 托盘工具类.
 * @author yangxiaodon
 * @Email  coder.yang2010@gmail.com
 * @date  2013-3-13 上午11:31:50 
 */
public class TrayterExample {
	protected Shell shell;

	protected Menu menu;

	public static void main(String[] args) {
		try {
			TrayterExample window = new TrayterExample();
			window.open();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void open() {
		final Display display = Display.getDefault();
		createContents();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch())
				display.sleep();
		}
	}

	protected void createContents() {
		shell = new Shell();

		//取得系统托盘对象
		final Tray tray = shell.getDisplay().getSystemTray();
		if (tray != null) {
			// 生成托盘对象
			final TrayItem trayItem = new TrayItem(tray, SWT.NONE);
			// 为托盘对象添加鼠标停留时的文字
			trayItem.setToolTipText("托盘程序测试......");
			// 为托盘对象添加显示用的图标

			//ImageData data = new ImageData("images/icon.gif");
			//Image i = new Image(shell.getDisplay(), data);
			//item.setImage(i);

			// 生成菜单项 
			menu = new Menu(shell, SWT.POP_UP);

			//加入Show子项菜单
			MenuItem w_show = new MenuItem(menu, SWT.PUSH);
			w_show.setText("&Show");
			w_show.addSelectionListener(new SelectionListener() {
				public void widgetSelected(SelectionEvent arg0) {
					shell.setVisible(true);
					shell.setMaximized(true);
				}

				public void widgetDefaultSelected(SelectionEvent arg0) {
				}
			});
			//加入Hidden子项菜单
			MenuItem w_hidden = new MenuItem(menu, SWT.PUSH);
			w_hidden.setText("&Hidden");
			w_hidden.addSelectionListener(new SelectionListener() {
				public void widgetSelected(SelectionEvent arg0) {
					shell.setVisible(false);
					shell.setMaximized(false);
				}

				public void widgetDefaultSelected(SelectionEvent arg0) {
				}
			});

			//加入Exit子项菜单
			MenuItem p_exit = new MenuItem(menu, SWT.PUSH);
			p_exit.setText("&Exit");
			p_exit.addSelectionListener(new SelectionListener() {
				public void widgetSelected(SelectionEvent arg0) {
					shell.close();
				}

				public void widgetDefaultSelected(SelectionEvent arg0) {

				}
			});

			// 为托盘对象添加事件，当右键点击图标时，显示菜单
			trayItem.addListener(SWT.MenuDetect, new Listener() {
				public void handleEvent(Event event) {
					menu.setVisible(true);
				}
			});
		}
	}
}