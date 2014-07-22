package com.yang.javalib.swtUtil.ui;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import com.yang.javalib.swtUtil.SWTResourceManager;
import com.yang.javalib.swtUtil.SwtUtil;
import com.yang.javalib.swtUtil.common.interF.ILoginInterFace;

/**
 * @Description: 通用登录框 
 * @author yangxiaodon
 * @Email  coder.yang2010@gmail.com
 * @date  2013-2-24 下午6:12:07 
 */
public class CommonLoginForm extends Shell {

	private static final Logger log = Logger.getLogger(CommonLoginForm.class);

	private Text txt_userName;

	private Text txt_pwd;

	/**
	 * @Fields : 验证主要类
	 */
	private ILoginInterFace iLoginInterFace;

	/**
	 * Launch the application.
	 * @param args
	 */
	public static void main(String args[]) {
		try {
			Display display = Display.getDefault();
			CommonLoginForm shell = new CommonLoginForm(display);
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

	public CommonLoginForm() {
		this(Display.getDefault());
	}

	public CommonLoginForm(Display display, ILoginInterFace iLoginInterFace) {
		this(Display.getDefault());
		if (iLoginInterFace == null)
			log.info("1111111111");
		this.iLoginInterFace = iLoginInterFace;
	}

	public ILoginInterFace getiLoginInterFace() {
		return iLoginInterFace;
	}

	/**
	 * @Description: 一定要做
	 * @param iLoginInterFace
	 */
	public void setiLoginInterFace(ILoginInterFace iLoginInterFace) {
		this.iLoginInterFace = iLoginInterFace;
	}

	/**
	 * Create the shell.
	 * @param display
	 */
	public CommonLoginForm(Display display) {
		super(display, SWT.SHELL_TRIM);
		setToolTipText("登录窗口");

		Label label = new Label(this, SWT.NONE);
		label.setFont(SWTResourceManager.getFont("宋体", 11, SWT.NORMAL));
		label.setBounds(35, 40, 58, 22);
		label.setText("用户名:");

		Label label_1 = new Label(this, SWT.NONE);
		label_1.setText("密  码:");
		label_1.setFont(SWTResourceManager.getFont("宋体", 11, SWT.NORMAL));
		label_1.setBounds(35, 72, 58, 22);

		txt_userName = new Text(this, SWT.BORDER);
		txt_userName.setFont(SWTResourceManager.getFont("宋体", 11, SWT.NORMAL));
		txt_userName.setBounds(96, 36, 166, 22);

		txt_pwd = new Text(this, SWT.BORDER | SWT.PASSWORD);
		txt_pwd.setBounds(96, 69, 166, 22);

		Button btn_confirm = new Button(this, SWT.NONE);
		btn_confirm.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				String userName = txt_userName.getText();
				String passWord = txt_pwd.getText();
				if (StringUtils.isBlank(userName)
						&& StringUtils.isBlank(passWord)) {
					SwtUtil.alertMsg(CommonLoginForm.this, "请填写用户名和密码!");
					return;
				}

				if (iLoginInterFace == null) {
					log.info("NULL iLoginInterFace");
				}

				if (iLoginInterFace.loginCheck(userName, passWord)) {
					log.info("登录 成功~!~");
					CommonLoginForm.this.close();
					iLoginInterFace.loginSucc();
				} else {
					log.info("登录失败,请核对用户名和密码~!~");
					SwtUtil.alertMsg(CommonLoginForm.this, "登录失败,请核对用户名和密码!");

					iLoginInterFace.loginFail();
				}
			}
		});
		btn_confirm.setBounds(163, 113, 63, 22);
		btn_confirm.setText("确定");

		Button btn_cancel = new Button(this, SWT.NONE);
		btn_cancel.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				CommonLoginForm.this.close();
			}
		});
		btn_cancel.setText("取消");
		btn_cancel.setBounds(243, 113, 63, 22);
		createContents();
	}

	/**
	 * Create contents of the shell.
	 */
	protected void createContents() {
		setText("登录窗口");
		setSize(357, 196);
		this.setLocation(Display.getCurrent().getClientArea().width / 2
				- this.getShell().getSize().x / 2, Display.getCurrent()
				.getClientArea().height / 2 - this.getSize().y / 2);

	}

	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}
}
