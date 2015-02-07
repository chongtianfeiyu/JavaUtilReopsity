package com.yang.base.net.media;

import java.applet.Applet;
import java.applet.AudioClip;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import javax.media.Manager;
import javax.media.MediaLocator;
import javax.media.NoPlayerException;
import javax.media.Player;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;

/**
 * 公用方法集合
 * @author Jinson Chen
 */

public abstract class MediaUtil {
	
	private static final Logger log =Logger.getLogger(MediaUtil.class) ;

	private static AudioClip plaer = null;
	private static byte[] b = new byte[] {};
	public static String splitStr = "==========================================================================================";
	public static boolean isTurnoffSound = false;

	/*
	*//**
		* 返回Date的毫秒值
		* @param date
		* @return
		*/
	/*
	public static long getCurrentLongTime() {
	 Calendar c = Calendar.getInstance();
	 c.setTime(new Date());
	 return c.getTimeInMillis();
	}
	
	*//**
		* 返回随机字符串
		* @return
		*/
	/*
	public static String getRandom(){
	String s = String.valueOf(Math.random());
	return s.substring(s.indexOf(".")+1);
	}
	*/
	/*
	*//**
		* 获取文件内容，文件大小不能超过最大整型字节数
		* @param file
		* @return
		*/
	/*
	public static byte[] getFileContent(File file){
	if(!file.exists()){
		log.error("Util.getFileContent 文件不存在");
		return new byte[]{-1};
	}
	FileInputStream input = null;
	try{
		input = new FileInputStream(file);
		int len = (int)file.length();
		int maxLen = 1024*1024;
		if(len > maxLen){
			log.error("文件超过处理限制大小：" + maxLen + "字节");
			return new byte[]{-2};
		}
		byte[] b = new byte[len];
		input.read(b);
		return b;
	}catch(Exception e){
		log.error("获取文件内容失败", e);
		return new byte[]{-3};
	}finally{
		if(input != null){
			try{
				input.close();
			}catch(Exception e){
				log.error("", e);
			}
		}
	}
	}
	
	public static Properties readPropertyFile(String fileName) {
	InputStream input = null;
	Properties prop = new Properties();
	try {
		input = new FileInputStream(fileName);
		prop.load(input);
	} catch (Exception e) {
		log.error("读取配置文件失败", e);
	}  finally {
		try {
			input.close();
		} catch (IOException ex) {
			log.error("", ex);
		}
	}
	return prop;
	}
	*/
	/*
	*//**
		* 启动监控
		*/
	/*
	public static void startMonitoring(final SopTray sopTray,final TextArea ta,final TrayIcon trayIcon,final ImageIcon iconOk ){
	try {
		long time = AppConfig.getInstance().getSopLooptime();
		if(time<=0l){
			time = 60;
		}
		log.info("事件控制台轮询间隔(秒)："+time);
		
		Timer timer = new Timer();
		timer.schedule(new TimerTask(){
			public void run() {
				log.info("轮询事件处理......");
				String status = SopPostClient.getSopAllAlarmEventsContent();
				if("0".equals(status)){
					status = "SOP运行正常，无系统指标告警。";
					trayIcon.setImage(iconOk.getImage());
					stopWarnSound();
					hiddenWarnWindow(sopTray,false);
				}else if("-1".equals(status)){
					status = "系统异常。";
					//playWarnSound();
					//flickerIcon(trayIcon, iconOk);
					//showWarnWindow(sopTray);
					trayIcon.displayMessage("SOP系统消息", "与SOP系统链接异常！", TrayIcon.MessageType.WARNING);    					
				}else{
					log.warn("SOP存在系统指标告警：\n"+status);
					playWarnSound();
					flickerIcon(trayIcon, iconOk);
					showWarnWindow(sopTray);   					
				}
				ta.setText(Util.splitJointTheText(ta.getText(), status));
			}
			
		}, 0,time*1000);
	
		
	} catch (Exception e) {
		log.error(e.getMessage(),e);
	}
	
	
	}
	*/
	/**
	 * 播放系统声音
	 */
	public synchronized static void playWarnSound() {
		try {
			synchronized (b) {
				if (plaer == null) {
					plaer = Applet
							.newAudioClip(new URL("file:sounds/warn.wav"));
				}
				if (!isTurnoffSound) {
					plaer.play();
				}
			}

		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
	} 

	public synchronized static void playLoopWarnSound() {
		try {
			synchronized (b) {
				if (plaer == null) {
					plaer = Applet
							.newAudioClip(new URL("file:sounds/warn.wav"));
				}
				if (!isTurnoffSound) {
					plaer.loop();
				}
			}

		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 停止系统声音
	 */
	public synchronized static void stopWarnSound() {
		try {
			synchronized (b) {
				if (plaer != null) {
					plaer.stop();
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 屏蔽系统声音
	 */
	public synchronized static void turnoffWarnSound() {
		try {
			synchronized (b) {
				if (plaer != null) {
					plaer.stop();
					isTurnoffSound = true;
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 打开系统声音
	 */
	public synchronized static void turnonWarnSound() {
		try {
			synchronized (b) {
				if (plaer != null) {
					isTurnoffSound = false;
					//plaer.loop();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 闪烁图标
	 * @param trayIcon
	 */
	/*
	public synchronized static void flickerIcon(TrayIcon trayIcon,ImageIcon iconOk){
	try {
		// 闪动消息的空白时间
		trayIcon.setImage(new ImageIcon("").getImage());
		Thread.sleep(500);
		// 闪动消息的提示图片
		trayIcon.setImage(iconOk.getImage());
		Thread.sleep(500);
	} catch (Exception e) {
		e.printStackTrace();
	}
	}*/
	/*
	*//**
		* 显示窗口
		* @param sopTray
		*/
	/*
	public static void showWarnWindow(SopTray sopTray){
	if(AppConfig.getInstance().isActivePopup()){
		sopTray.setExtendedState(JFrame.NORMAL);
		sopTray.setVisible(true); // 显示窗口
		sopTray.toFront();
	}
	}
	*/
	/*  *//**
			* 隐藏窗口
			* @param sopTray
			*/
	/*
	public static void hiddenWarnWindow(SopTray sopTray,boolean flag){
	if(flag || AppConfig.getInstance().isAutoShrink()){
		sopTray.setVisible(false); // 隐藏窗口
	}
	}
	*/
	/*
	*//**
		* 拼接显示字符串
		* @param text
		* @param warnContent
		* @return
		*/
	/*
	public static String splitJointTheText(String text, String warnContent){
	StringBuffer msg = new StringBuffer();
	if(!text.equals("")){
		String[] splitTextArr = text.split(splitStr);
		int textAreaShowMaxCount = AppConfig.getInstance().getTextAreaShowMaxCount();
		if(splitTextArr!=null&&splitTextArr.length>textAreaShowMaxCount){
			int startIdx = splitTextArr.length -textAreaShowMaxCount;
			for (int i = startIdx; i < splitTextArr.length; i++) {
				if(i==startIdx){
					msg.append("\n");
				}
				msg.append(splitStr);
				msg.append(splitTextArr[i-1]);
			}
			text = msg.toString();
		}
	}		
	 msg = new StringBuffer(); 
	msg.append("\n").append(splitStr)
	.append("\n").append(warnContent)
	.append(" \n接收时间："+ new Date().toLocaleString()).toString();
	msg.append(text);
	
	
	return msg.toString();
	}
	*/
	/*
	public static String getSopStatusDesc(String status) {
	if("0".equals(status)){
		status = "SOP运行正常，无系统指标告警。";
	}else if("-1".equals(status)){
		status = "系统异常。";
	}
	return status;
	}	
	
	*/
	// JMF的播放器
	private static Player player;

	/**
	 * @Description: TODO. 这个弄一下. 试试 
	 */
	public static void createPlayer() {
 
		if (player != null) {
			// 关闭已经存在JMF播放器对象
			player.close();
		}
		try {
			// 创建一个打开选择文件的播放器                                             
			player = Manager.createPlayer(new MediaLocator(
					"file:sounds/warn.wav"));
		} catch (java.io.IOException e2) {
			System.out.println(e2);
			return;
		} catch (NoPlayerException e2) {
			System.out.println("不能找到播放器.");
			return;
		}
		if (player == null) {
			System.out.println("无法创建播放器.");
			return;
		}
		// 播放器的控制事件处理
		//player.addControllerListener(this);
		// 预读文件内容
		player.prefetch();

		//createPlayer();
		// 如果允许循环，则重新开始播放
		////player.setMediaTime(new Time(0));
		//player.start();
	}
	
	/**
	 * @throws IOException 
	 * @throws NoPlayerException 
	 * @Description: 播放一首歌曲 使用JMF框架的东西 .
	 * @param filePath
	 * @throws MalformedURLException
	 * void
	 * @throws
	 */
	public static void playOneSoundByJMF(String filePath) throws NoPlayerException, IOException{
		 File fi =new File(filePath);
		  //fi.toURL()
		
		 if(fi.exists()){
			Player player=Manager.createPlayer(new MediaLocator(fi.toURL()));
			player.prefetch();
			player.start();
		 }
		 else {
			log.info("音频文件不存在 "+filePath);
		}
		
	}
	 
	public static void playOneSound(String filePath) throws NoPlayerException, IOException{
		 File fi =new File(filePath);
        System.out.println(fi.getAbsolutePath());
        if(fi.exists()){
				AudioClip plaer =  Applet.newAudioClip( fi.toURL()); 
				plaer.play(); 
		 }
		 else {
			log.info("音频文件不存在 "+filePath);
		}
		
	}
	
	 


}
