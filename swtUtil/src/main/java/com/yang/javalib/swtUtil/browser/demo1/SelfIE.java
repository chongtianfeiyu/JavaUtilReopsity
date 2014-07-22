package com.yang.javalib.swtUtil.browser.demo1;

import org.apache.log4j.Logger;
import org.eclipse.swt.SWT;
import org.eclipse.swt.browser.Browser;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.events.ShellAdapter;
import org.eclipse.swt.events.ShellEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Tray;
import org.eclipse.swt.widgets.TrayItem;

/**
 * @Description: 自定义的测试浏览器
 * @author yangxiaodon
 * @Email  coder.yang2010@gmail.com
 * @date  2014-3-25 下午9:55:49 
 */
public class SelfIE extends Shell {
	
	private static final Logger log = Logger.getLogger(SelfIE.class);

	private TrayItem trayItem = null;
	private Browser browser = null;

	/**
	 * Launch the application.
	 * @param args
	 */
	public static void main(String args[]) {
		try {
			Display display = Display.getDefault();
			SelfIE shell = new SelfIE(display);
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
	public SelfIE(Display display) {
		super(display, SWT.SHELL_TRIM);

		 browser =new Browser(this, SWT.NONE) ; 
		/**/
		if (false) {
			browser = new Browser(this, SWT.NONE);
		} else {
			String path = System.getProperty("user.dir")
					+ java.io.File.separator + "xulrunner"; 
			System.setProperty("org.eclipse.swt.browser.XULRunnerPath", path); 
			browser = new Browser(this, SWT.MOZILLA);
		}
		 
		browser.setBounds(10, 38, 719, 447);
		createContents();
		addListener2Browser();
		createTrayMenus();//托盘对象
	}

	/**
	 * Create contents of the shell.
	 */
	protected void createContents() {

		this.addShellListener(new ShellAdapter() {
			@Override
			public void shellClosed(ShellEvent e) {
				//super.shellClosed(e);
				e.doit = false;
				SelfIE.this.setVisible(false);
				trayItem.setVisible(true);
			}

			@Override
			public void shellIconified(ShellEvent e) {
				super.shellIconified(e);
				System.out.println("最小化了.");
			}

		});

		this.setLocation(Display.getCurrent().getClientArea().width / 2
				- this.getShell().getSize().x / 2, Display.getCurrent()
				.getClientArea().height / 2 - this.getSize().y / 2);
		this.setVisible(false);
		
		setText("SWT Application");
		setSize(775, 529);

	}
	

	protected void createTrayMenus() {
		//取得系统托盘对象
		final Tray tray = Display.getDefault().getSystemTray();

		if (tray != null) {
			// 生成托盘对象
			trayItem = new TrayItem(tray, SWT.NONE);
			// 为托盘对象添加鼠标停留时的文字
			trayItem.setToolTipText("监控平台事件检测程序");
			// 为托盘对象添加显示用的图标

			// 托盘单击事件
			trayItem.addListener(SWT.Selection, new Listener() {

				public void handleEvent(Event event) {
					//trayItem.setVisible(false);
					SelfIE.this.setVisible(true);
				}
			});

			ImageData data = new ImageData("images/icon.gif");
			Image i = new Image(trayItem.getDisplay(), data);
			trayItem.setImage(i);
			trayItem.setVisible(true);

			// 生成菜单项 
			final Menu menu = new Menu(SelfIE.this, SWT.POP_UP);
			//加入Show子项菜单
			/*	MenuItem home = new MenuItem(menu, SWT.PUSH);
				home.setText("管理中心");*/

			MenuItem msg_setUP = new MenuItem(menu, SWT.CASCADE);
			msg_setUP.setText("通知设置");

			{ //添加 消息设置
				Menu msg_col_menu = new Menu(SelfIE.this,
						SWT.DROP_DOWN);
				msg_setUP.setMenu(msg_col_menu);

				MenuItem sound = new MenuItem(msg_col_menu, SWT.CHECK);
				sound.setText("屏蔽声音");
				/*
								MenuItem auto_close = new MenuItem(msg_col_menu, SWT.CHECK);
								auto_close.setText("自动关闭消息");
								auto_close.setSelection(MsgService.auto_close_msg);
				　
								auto_close.addSelectionListener(new SelectionListener() {
									@Override
									public void widgetSelected(SelectionEvent arg0) {
										MenuItem w_show = (MenuItem) arg0.getSource();
										if (w_show.getSelection()) {
											MsgService.turnOnMsgPrompt();
											log.info("选中消息窗口延时关闭");
										} else {
											MsgService.turnOffMsgPrompt();
											log.info("取消消息窗口延时关闭");
										}
									}

									@Override
									public void widgetDefaultSelected(SelectionEvent arg0) {

									}
								});

				*/
				/*
				 * MenuItem flick_pic = new MenuItem(msg_col_menu, SWT.CHECK);
				flick_pic.setText("闪动图标");*/

				sound.addSelectionListener(new SelectionListener() {
					 
					public void widgetSelected(SelectionEvent arg0) {
						MenuItem w_show = (MenuItem) arg0.getSource();
						if (w_show.getSelection()) {
							//MediaUtil.turnoffWarnSound();
							log.info("选中屏蔽声音");
						} else {
							//MediaUtil.turnonWarnSound();
							log.info("不屏蔽不屏蔽声音");
						}
					}
 
					public void widgetDefaultSelected(SelectionEvent arg0) {

					}
				});
			}
			/*
						MenuItem msg = new MenuItem(menu, SWT.PUSH);
						msg.setText("消息盒子 ");
						//加入Hidden子项菜单
						MenuItem w_hidden = new MenuItem(menu, SWT.PUSH);
						w_hidden.setText("&Hidden");
						
						w_hidden.addSelectionListener(new SelectionListener() {
							public void widgetSelected(SelectionEvent arg0) {
								sShell.setVisible(false);
								sShell.setMaximized(false);
								trayItem.setVisible(true);
							}

							public void widgetDefaultSelected(SelectionEvent arg0) {
							}
						});

			*/

			/*if (AppConfig.frame_menu_isShow) {
				MenuItem w_show = new MenuItem(menu, SWT.PUSH);
				w_show.setText("&Show");

				w_show.addSelectionListener(new SelectionListener() {
					public void widgetSelected(SelectionEvent arg0) {
						SelfIE.this.setVisible(true);
						
						trayItem.setVisible(false);
						
						//RealTimeBrowser.this.setMinimized(true);//方法,最小化窗口
					}

					public void widgetDefaultSelected(SelectionEvent arg0) {
					}
				});
			}*/

			//加入Exit子项菜单
			MenuItem p_exit = new MenuItem(menu, SWT.PUSH);
			p_exit.setText("退出");

			p_exit.addSelectionListener(new SelectionListener() {
				public void widgetSelected(SelectionEvent arg0) {
					trayItem.setVisible(false);
					System.exit(0);
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
			trayItem.addListener(SWT.MouseDoubleClick, new Listener() {
				public void handleEvent(Event event) {
					//RealTimeBrowser.this.setVisible(true);
					//trayItem.setVisible(false);
				}
			});

		}
	}
	
	public void executeJS(String javaScript){
		browser.execute(javaScript);
	}
	
	 
	//初始化浏览器   --添加事件
	private void addListener2Browser() {

		//注册浏览器状态改变事件  
		browser.addStatusTextListener(new org.eclipse.swt.browser.StatusTextListener() {
			public void changed(org.eclipse.swt.browser.StatusTextEvent e) {
				String temp = e.text;
				if (temp.contains("opetartorType")) {
					log.info("状态栏信息改变为为：" + temp);
					/*JSONObject object = null;
					try {
						object = JsonUtil.str2JSONObject(temp);
					} catch (Exception e2) {
						log.warn("解析字符串时出错：" + temp);
						log.warn("", e2);
						return;
					}
					EventVO eventVO = new EventVO();

					eventVO.setAlarmLevel(object.getInt("alarmLevel"));
					eventVO.setAlarmTime(object.getString("alarmTime"));

					eventVO.setIsconfirm(object.getInt("isconfirm") == 1);

					eventVO.setAlarmDataSource(object
							.getString("alarmDataSource"));
					eventVO.setAlarmDesc(object.getString("alarmDesc"));
					eventVO.setAlarmName(object.getString("alarmName"));
					eventVO.setAlarmSource(object.getString("alarmSource"));
					eventVO.setAlarmType(object.getString("alarmType"));

					String temp_ = object.getString("opetartorType");

					if (StringUtils.isBlank(temp_)) {
						log.info("操作类型为空,不处理");
					} else if ("add".equalsIgnoreCase(temp_)) {
						if (!EventManagerService.isContain(eventVO)) {
							if (eventVO.getAlarmLevel() >= 3) {
								EventManagerService.addEventVO(eventVO);

								if (!RealTimeBrowser.this.getVisible()) {
									RealTimeBrowser.this.setVisible(true);//有新的告警
									trayItem.setVisible(false);
								}

								if (RealTimeBrowser.this.getMinimized()) {
									RealTimeBrowser.this.setMinimized(false);
								}

								log.info("新添加事件:" + object.toString());
							} else {
								log.info("事件级别过低,不处理:" + object.toString());
							}
						} else {
							log.info("已存在重复事件不再添加" + object.toString());
						}
					} else if ("del".equalsIgnoreCase(temp_)) {
						if (EventManagerService.isContain(eventVO)) {
							EventManagerService.delEventVO(eventVO);
							log.info("删除事件:" + object.toString());
						} else {
							log.info("事件不存在,无法删除,忽略:" + object.toString());
						}
					} else if ("update".equalsIgnoreCase(temp_)) {

					} else if ("confirm".equalsIgnoreCase(temp_)) {

					}*/
				};
			}
		});
	}

	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}
}
