package com.yang.javalib.swtUtil.ui;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.TraverseEvent;
import org.eclipse.swt.events.TraverseListener;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import com.yang.javalib.swtUtil.SWTResourceManager;

/**
 * @Description: 
final MsgDialog msgDialog = new MsgDialog();
		msgDialog.setMsg("\r\n监控平台提醒:\r\n" + message);
		msgDialog.open();
		
 * @author yangxiaodon
 * @Email  coder.yang2010@gmail.com
 * @date  2013-3-18 下午2:00:51 
 */
public class MsgDialog extends Shell {
	private Text text;

	//final Clipboard clipboard = new Clipboard(this.getDisplay());

	/**
	 * Launch the application.
	 * @param args
	 */
	public static void main(String args[]) {
		try {
			Display display = Display.getDefault();
			MsgDialog shell = new MsgDialog(display);
			shell.open();
			shell.setMsg("11111111111111111111111");
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

	public MsgDialog() {
		this(Display.getDefault());
	}

	/**
	 * Create the shell.
	 * @param display
	 */
	public MsgDialog(Display display) {
		super(display, SWT.ON_TOP | SWT.CLOSE | SWT.TITLE);
		setToolTipText("消息盒子");
		text = new Text(this, SWT.BORDER | SWT.WRAP | SWT.V_SCROLL | SWT.MULTI);
		text.setFont(SWTResourceManager.getFont("宋体", 12, SWT.NORMAL));
		text.setBounds(0, 0, 467, 261);
		text.addKeyListener(new KeyListener() {

			public void keyReleased(KeyEvent arg0) {
			}

			public void keyPressed(KeyEvent arg0) {
				if ((arg0.stateMask & SWT.CTRL) != 0 && arg0.keyCode == 97) { //
					text.selectAll();
					//这里写事件要实现的代码  
				}
			}
		});
		text.addTraverseListener(new TraverseListener() {

			public void keyTraversed(TraverseEvent e) {
				System.out.println(e.keyCode);
				if ((e.stateMask & SWT.CTRL) != 0) { //&& e.keyCode ==SWT.ALPHA
					System.out.println(e.keyCode);
					//这里写事件要实现的代码  
				}
			}
		});

		Button button = new Button(this, SWT.NONE);
		button.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				text.selectAll();

			}
		});
		button.setBounds(275, 267, 72, 22);
		button.setText("全选");

		Button button_1 = new Button(this, SWT.NONE);
		button_1.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				String textData = text.getSelectionText();
				if (textData.length() > 0) {
					text.copy();
					/*	TextTransfer textTransfer = TextTransfer.getInstance();
						clipboard.setContents(new Object[] { textData },
								new Transfer[] { textTransfer });
					*/}
			}
		});
		button_1.setBounds(353, 267, 72, 22);
		button_1.setText("复制");
		createContents();
	}

	/**
	 * Create contents of the shell.
	 */
	protected void createContents() {
		setText("消息盒子");
		setSize(475, 323);
		init();
	}

	public void init() {

		this.setLocation(Display.getCurrent().getClientArea().width / 2
				- this.getShell().getSize().x / 2, Display.getCurrent()
				.getClientArea().height / 2 - this.getSize().y / 2);

		/*	System.out.println(text.getMenu().getItemCount());
			MenuItem[] tt = text.getMenu().getItems();

			for (MenuItem menuItem : tt) {
				System.out.println(menuItem.getText());
			}
			*/
	}

	@Override
	protected void checkSubclass() {

	}

	public void setMsg(String message) {
		text.setText(message);
		//text.setText(string)
	}
}
