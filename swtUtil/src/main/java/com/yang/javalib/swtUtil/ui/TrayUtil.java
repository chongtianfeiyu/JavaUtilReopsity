package com.yang.javalib.swtUtil.ui;

import org.eclipse.swt.widgets.TrayItem;

import com.yang.javalib.swtUtil.SWTResourceManager;

/**
 * @Description: 托盘工具类
 * @author yangxiaodon
 * @Email  coder.yang2010@gmail.com
 * @date  2013-3-13 下午4:41:22 
 */
public class TrayUtil {

	/**
	 * @Description:  闪烁图标
	 * @param trayItem
	 * @param picPaths 第一个是最后保留的.
	 */
	public static void flickerIcon(TrayItem trayItem, String[] picPaths) {
		try {
			//闪动消息的空白时间
			for (String string : picPaths) {
				trayItem.setImage(SWTResourceManager.getImage(TrayUtil.class,
						string));
				Thread.sleep(500);
			}
			trayItem.setImage(SWTResourceManager.getImage(TrayUtil.class,
					picPaths[0]));

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
