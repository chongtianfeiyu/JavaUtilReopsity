package com.yang.javalib.swtUtil.ui;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Shell;

public class MenuTest {
	static Display display = new Display();
	static Shell shell = new Shell(display);

	/* 
	 * 创建多级菜单, 采用SWT.CASCADE样式值可以加入子菜单,采用SWT.PUSH样式值不可以加入子菜单 
	 */
	public static void createMenu() {

		shell.setSize(500, 500);
		shell.setText("menu test window");
		//shell.setImage(new Image(display, "images/Home.png"));

		// 通过shell对象和SWT.BAR样式值来创建一个  菜单条  
		Menu menu = new Menu(shell, SWT.BAR); 
		
		// 在菜单条的基础之上创建一个File的菜单  
		MenuItem file = new MenuItem(menu, SWT.CASCADE);
		file.setText("&File");
		
		// 先在Shell上创建一个下拉框，然后将下拉框添加到File菜单上  
		Menu filemenu = new Menu(shell, SWT.DROP_DOWN);
		file.setMenu(filemenu);
		// 在下拉框上创建菜单项Open  
		final MenuItem openItem = new MenuItem(filemenu, SWT.CASCADE);
		openItem.setText("&Open");
		// 在shell上创建一个下拉框,并把它加到open菜单上  
		Menu childmenu = new Menu(shell, SWT.DROP_DOWN);
		openItem.setMenu(childmenu);
		// 在open菜单上创建一个子菜单child  
		final MenuItem childItem = new MenuItem(childmenu, SWT.PUSH);
		childItem.setText("&Child");
		// 在open菜单上创建一个子菜单dialog  
		final MenuItem dialogItem = new MenuItem(childmenu, SWT.PUSH);
		dialogItem.setText("&Dialog");
		// 在菜单项之间创建一个分隔符  
		new MenuItem(filemenu, SWT.SEPARATOR);
		// 在下拉框上创建菜单项Exit  
		MenuItem exitItem = new MenuItem(filemenu, SWT.PUSH);
		exitItem.setText("&Exit");

		// 设置edit菜单  
		MenuItem edit = new MenuItem(menu, SWT.CASCADE);
		edit.setText("&Edit");
		Menu editmenu = new Menu(shell, SWT.DROP_DOWN);
		edit.setMenu(editmenu);
		MenuItem cutItem = new MenuItem(editmenu, SWT.PUSH);
		cutItem.setText("&Cut\tCTRL+X");
		// 设置快捷键  
		cutItem.setAccelerator(SWT.CTRL + 'X');
		// 在菜单项之间创建一个分隔符  
		new MenuItem(editmenu, SWT.SEPARATOR);
		MenuItem copyItem = new MenuItem(editmenu, SWT.PUSH);
		copyItem.setText("&Copy\tCTRL+C");
		// 设置快捷键  
		copyItem.setAccelerator(SWT.CTRL + 'C');
		// 在菜单项之间创建一个分隔符  
		new MenuItem(editmenu, SWT.SEPARATOR);
		MenuItem pasteItem = new MenuItem(editmenu, SWT.PUSH);
		pasteItem.setText("&Paste\tCTRL+V");
		// 设置快捷键  
		pasteItem.setAccelerator(SWT.CTRL + 'V');

		// 设置window菜单  
		MenuItem window = new MenuItem(menu, SWT.CASCADE);
		window.setText("&Window");
		Menu windowmenu = new Menu(shell, SWT.DROP_DOWN);
		window.setMenu(windowmenu);
		final MenuItem maxItem = new MenuItem(windowmenu, SWT.PUSH);
		maxItem.setText("&Max");
		// 在菜单项之间创建一个分隔符  
		new MenuItem(windowmenu, SWT.SEPARATOR);
		MenuItem minItem = new MenuItem(windowmenu, SWT.PUSH);
		minItem.setText("&Min");

		// 设置help菜单  
		MenuItem help = new MenuItem(menu, SWT.CASCADE);
		help.setText("&Help");
		Menu helpmenu = new Menu(shell, SWT.DROP_DOWN);
		help.setMenu(helpmenu);
		MenuItem aboutItem = new MenuItem(helpmenu, SWT.PUSH);
		aboutItem.setText("&About");

		// 添加一个option菜单，用于添加单选菜单和复选菜单  
		MenuItem option = new MenuItem(menu, SWT.CASCADE);
		option.setText("&Option");
		Menu optionmenu = new Menu(shell, SWT.DROP_DOWN);
		option.setMenu(optionmenu);
		MenuItem checkItem1 = new MenuItem(optionmenu, SWT.CHECK);
		checkItem1.setText("Check1");
		MenuItem checkItem2 = new MenuItem(optionmenu, SWT.CHECK);
		checkItem2.setText("Check2");
		// 设置默认为选中  
		checkItem2.setSelection(true);
		// 设置分隔符  
		new MenuItem(optionmenu, SWT.SEPARATOR);
		MenuItem radioItem1 = new MenuItem(optionmenu, SWT.RADIO);
		radioItem1.setText("Radio1");
		// 设置默认为选中  
		radioItem1.setSelection(true);
		MenuItem radioItem2 = new MenuItem(optionmenu, SWT.RADIO);
		radioItem2.setText("Radio2");

		// 为菜单添加相应的事件  
		childItem.addSelectionListener(new SelectionListener() {
 
			public void widgetSelected(SelectionEvent arg0) {
				// TODO Auto-generated method stub  
				Shell parent = (Shell) maxItem.getParent().getParent()
						.getParent();
				Shell child = new Shell(parent, SWT.RESIZE | SWT.MAX);
				child.setText("child item has been clicked");
				child.setImage(new Image(display, "images/Alert.png"));
				child.setSize(400, 100);
				child.open();
			}

		 
			public void widgetDefaultSelected(SelectionEvent arg0) {
				// TODO Auto-generated method stub  
			}
		});

		// 将菜单条添加到shell上  
		shell.setMenuBar(menu);
		// 弹出右键菜单  
		shell.setMenu(createEditPopup());
		shell.open();
		while (!shell.isDisposed()) {
			if (display.readAndDispatch()) {
				display.sleep();
			}
		}
		display.dispose();
	}

	/* 
	 * 创建一个右键菜单 通过样式值SWT.POP_UP来创建一个右键弹出菜单 
	 */
	public static Menu createEditPopup() {
		Menu popMenu = new Menu(shell, SWT.POP_UP);
		MenuItem cutItem = new MenuItem(popMenu, SWT.PUSH);
		cutItem.setText("&Cut");
		MenuItem copyItem = new MenuItem(popMenu, SWT.PUSH);
		copyItem.setText("&Copy");
		MenuItem pasteItem = new MenuItem(popMenu, SWT.PUSH);
		pasteItem.setText("&Paste");
		cutItem.addSelectionListener(new SelectionListener() {

			 
			public void widgetSelected(SelectionEvent arg0) {
				// TODO Auto-generated method stub  
				System.out.println("cut");
			}

		 
			public void widgetDefaultSelected(SelectionEvent arg0) {
				// TODO Auto-generated method stub  

			}
		});
		pasteItem.addSelectionListener(new SelectionListener() {

		 
			public void widgetSelected(SelectionEvent arg0) {
				// TODO Auto-generated method stub  
				System.out.println("paste");
			}

			 
			public void widgetDefaultSelected(SelectionEvent arg0) {
				// TODO Auto-generated method stub  

			}
		});
		copyItem.addSelectionListener(new SelectionListener() {

			public void widgetSelected(SelectionEvent arg0) {
				// TODO Auto-generated method stub  
				System.out.println("copy");
			}

		 
			public void widgetDefaultSelected(SelectionEvent arg0) {
				// TODO Auto-generated method stub  

			}
		});
		return popMenu;
	}

	public static void main(String[] args) {
		MenuTest.createMenu();

	}
}