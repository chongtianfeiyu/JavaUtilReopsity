package com.yang.javalib.swtUtil.ui;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

public class LoginForm {
	Display display = new Display();
	Shell shell = new Shell(display);
	Label label1, label2;
	Text username;
	Text password;
	Text text;

	public LoginForm() {
		shell.setLayout(new GridLayout(2, false));
		shell.setText("Login form");
		label1 = new Label(shell, SWT.NULL);
		label1.setText("User Name: ");

		username = new Text(shell, SWT.SINGLE | SWT.BORDER);
		username.setText("");
		username.setTextLimit(30);

		label2 = new Label(shell, SWT.NULL);
		label2.setText("Password: ");

		password = new Text(shell, SWT.SINGLE | SWT.BORDER);
		System.out.println(password.getEchoChar());
		password.setEchoChar('*');
		password.setTextLimit(30);

		Button button = new Button(shell, SWT.PUSH);
		button.setText("Submit");
		button.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event event) {
				String selected = username.getText();
				String selected1 = password.getText();

				if (selected == "") {
					MessageBox messageBox = new MessageBox(shell, SWT.OK
							| SWT.ICON_WARNING | SWT.CANCEL);
					messageBox.setMessage("Enter the User Name");
					messageBox.open();
				}
				if (selected1 == "") {
					MessageBox messageBox = new MessageBox(shell, SWT.OK
							| SWT.ICON_WARNING | SWT.CANCEL);
					messageBox.setMessage("Enter the Password");
					messageBox.open();
				} else {
					MessageBox messageBox = new MessageBox(shell, SWT.OK
							| SWT.CANCEL);
					messageBox.setText("Login Form");
					messageBox.setMessage("Welcome:" + username.getText());
					messageBox.open();
				}
			}
		});
		username.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		password.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

		shell.pack();
		shell.open();

		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
		display.dispose();
	}

	public static void main(String[] args) {
		new LoginForm();
	}
}