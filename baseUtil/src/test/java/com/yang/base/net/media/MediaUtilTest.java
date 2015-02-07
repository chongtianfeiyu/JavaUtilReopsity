package com.yang.base.net.media;
 
import java.io.IOException;
import java.net.MalformedURLException;

import javax.media.NoPlayerException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class MediaUtilTest {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	/**
	 * @throws IOException 
	 * @throws NoPlayerException 
	 * @throws InterruptedException 
	 * @throws MalformedURLException 
	 * @Description: 播放下音乐 , 难道只能播放WAV格式的.
	 * void
	 * @throws
	 */
	@Test
	public void testPlayOneSound() throws InterruptedException, NoPlayerException, IOException { 
		MediaUtil.playOneSound("baseUtil/sounds/warn.wav");//本地运行，所以相当于是又进了一层目录
        //F:\javaProjects\JavaUtilReopsity\sounds\warn.wav
    }

	@Test
	public void testPlayOneSoundUseCase1() throws InterruptedException, NoPlayerException, IOException { 
		MediaUtil.playOneSound("sounds/催OA.wav");  
	}

}
