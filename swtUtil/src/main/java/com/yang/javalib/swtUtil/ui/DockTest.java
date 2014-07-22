package com.yang.javalib.swtUtil.ui;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ControlAdapter;
import org.eclipse.swt.events.ControlEvent;
import org.eclipse.swt.events.ControlListener;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseMoveListener;
import org.eclipse.swt.events.MouseTrackListener;
import org.eclipse.swt.events.ShellAdapter;
import org.eclipse.swt.events.ShellEvent;
import org.eclipse.swt.events.ShellListener;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

public class DockTest {
	static boolean isHidden = false;
	static Shell shell = null;
	static Display display = null;

	static ControlListener controlListener = new ControlAdapter() {
		public void controlMoved(ControlEvent e) {
			Rectangle rect = shell.getBounds();
			if (rect.y < 1 && !isHidden) {
				shell.setLocation(rect.x, 5 - rect.height);
				isHidden = true;
			} else if (rect.y > 1 && isHidden) {
				isHidden = false;
			}
		}
	};

	/*
	* 不用MouseTrackListener的mouseEnter()，而是在MouseMoveListener判断鼠标是否进入shell
	* 理由：
	* 假如使用mouseEnter()，那么在该方法内调用Shell的setLocation()
	* 可能造成原来的鼠标位置不在shell内，从而触发mouseExit()，该方法又将shell停靠隐藏。
	* */
	static MouseMoveListener mouseMoveListener = new MouseMoveListener() {
		public void mouseMove(MouseEvent e) {
			Rectangle rect = shell.getBounds();
			Point p = display.getCursorLocation();
			if (isHidden && rect.contains(p) && rect.y < 0) {
				shell.setLocation(rect.x, 0);
				//注意这里
				shell.addMouseTrackListener(mouseTrackListener);
			}
		}
	};
	static MouseTrackListener mouseTrackListener = new MouseTrackListener() {
		public void mouseEnter(MouseEvent e) {
		}

		public void mouseExit(MouseEvent e) {
			if (e.widget == shell && isHidden) {
				Rectangle rect = shell.getBounds();
				shell.setLocation(rect.x, 5 - rect.height);
				//注意这里
				shell.removeMouseTrackListener(mouseTrackListener);
			}
		}

		public void mouseHover(MouseEvent e) {
		}

	};
	//不允许最小化，保证点击任务栏上的"显示桌面"时，shell依然在最前显示
	static ShellListener shellListener = new ShellAdapter() {
		public void shellIconified(ShellEvent e) {
			shell.setMinimized(false);
		}
	};

	public static void main(String[] args) {
		display = new Display();

		//注意SWT.ON_TOP保证shell最前显示
		int style = SWT.ON_TOP | SWT.CLOSE | SWT.TITLE | SWT.RESIZE
				| SWT.BORDER | SWT.TOOL;
		shell = new Shell(display, style);

		shell.setSize(100, 500);
		shell.setText("QQ");

		shell.addControlListener(controlListener);
		shell.addMouseMoveListener(mouseMoveListener);
		shell.addShellListener(shellListener);

		shell.open();

		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}

		display.dispose();
	}
}
