package com.yang.javalib.swtUtil.ui;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import com.yang.javalib.swtUtil.SWTResourceManager;
import com.yang.javalib.swtUtil.common.vo.PromptStr;

public class PromptSimpleUi extends Shell {
	private Text text;
	private PromptStr promptStr;

	/**
	 * @Description: 就靠这个传递对象
	 * @return
	 */
	public PromptStr getPromptStr() {
		return promptStr;
	}

	public void setPromptStr(PromptStr promptStr) {
		this.promptStr = promptStr;
	}

	/**
	 * Launch the application.
	 * @param args
	 */
	public static void main(String args[]) {
		try {
			Display display = Display.getDefault();
			PromptSimpleUi shell = new PromptSimpleUi(display);
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
	 * @wbp.parser.constructor
	 */
	public PromptSimpleUi(Display display) {
		super(display, SWT.SHELL_TRIM);

		text = new Text(this, SWT.BORDER);
		text.setFont(SWTResourceManager.getFont("宋体", 12, SWT.NORMAL));
		text.setBounds(26, 21, 189, 27);

		Button button = new Button(this, SWT.NONE);
		button.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if (promptStr == null)
					return;
				promptStr.setPromptString(text.getText());
				PromptSimpleUi.this.close();

			}
		});
		button.setBounds(105, 67, 72, 22);
		button.setText("确定");

		Button button_1 = new Button(this, SWT.NONE);
		button_1.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				PromptSimpleUi.this.close();
			}
		});
		button_1.setBounds(180, 67, 72, 22);
		button_1.setText("取消");
		createContents();
	}

	/**
	 * Create the shell.
	 * @param display
	 */
	public PromptSimpleUi(Display display, String title) {
		this(display);
		setText(title);
		this.setLocation(Display.getCurrent().getClientArea().width / 2
				- this.getShell().getSize().x / 2, Display.getCurrent()
				.getClientArea().height / 2 - this.getSize().y / 2);

	}

	/**
	 * Create contents of the shell.
	 */
	protected void createContents() {
		setText("输入对话框");
		setSize(303, 136);

	}

	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}
}
