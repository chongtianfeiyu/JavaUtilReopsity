package com.yang.javalib.threadUtil.ReentrantLock;

import static org.junit.Assert.*;

import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * @Description: 锁库测试１　．
 * @author yangxiaodon
 * @Email coder.yang2010@gmail.com
 * @date 2014-4-13 下午10:24:42
 */
public class ReentrantLockRepoTestCase1 {/*
* 
* private LockUnitReentrant
* reentrantLockRepo;
* 
* @Before public void setUp() throws
* Exception { reentrantLockRepo = new
* LockUnitReentrant();
* reentrantLockRepo.putOne(1);
* //对这三个帐户　分别配一个锁．
* reentrantLockRepo.putOne(2);
* reentrantLockRepo.putOne(3); }
* 
* @After public void tearDown() throws
* Exception {
* 
* }
*/
	/**
	 * 
	 * @Description: 首先运行下看看结果 .
	 */
	/*
	 * @Test public void testCase1() {
	 * 
	 * //创建并发访问的账户 MyCount myCount = new MyCount("95599200901215522", 1, 10000,
	 * reentrantLockRepo); //创建一个线程池 ExecutorService pool =
	 * Executors.newFixedThreadPool(2); Thread t1 = new SaveThread("张三",
	 * myCount, 2000); Thread t2 = new SaveThread("李四", myCount, 3600); Thread
	 * t3 = new DrawThread("王五", myCount, 2700); Thread t4 = new
	 * SaveThread("老张", myCount, 600); Thread t5 = new DrawThread("老牛", myCount,
	 * 1300); Thread t6 = new DrawThread("胖子", myCount, 800);
	 * 
	 * //最后应该是 //执行各个线程 pool.execute(t1); pool.execute(t2); pool.execute(t3);
	 * pool.execute(t4); pool.execute(t5); pool.execute(t6);
	 * 
	 * //关闭线程池 pool.shutdown();
	 * 
	 * 张三存款2000，当前余额为12000 王五取款2700，当前余额为9300 老张存款600，当前余额为9900
	 * 老牛取款1300，当前余额为8600 胖子取款800，当前余额为7800 李四存款3600，当前余额为11400
	 * 
	 * 
	 * }
	 *//**
		* @throws InterruptedException
		* @Description: 测试 单个锁, 对单个对象的锁定行为 进入测试模式, 开两个save线程, 一个进去debug , 然后
		*               看另一个的反应 1.测试进入一个锁, 看另一个线程 在指定时间得不到锁后的结果
		*               用tryLock的话,若得不到会退出的. 2.测试进入一个锁,看另一个线程 在指定时间内得到锁后的结果
		*               得到锁后会执行的.
		*/
	/*
	 * @SuppressWarnings("static-access")
	 * 
	 * @Test public void testCase2() throws InterruptedException {
	 * 
	 * //创建并发访问的账户 MyCount myCount = new MyCount("95599200901215522", 1, 10000,
	 * reentrantLockRepo); //创建一个线程池 ExecutorService pool =
	 * Executors.newFixedThreadPool(2); Thread t1 = new SaveThread("张三",
	 * myCount, 2000); Thread t2 = new SaveThread("李四", myCount, 3600);
	 * 
	 * //最后应该是 //执行各个线程 pool.execute(t1); pool.execute(t2);
	 * Thread.currentThread().sleep(50000); //关闭线程池 pool.shutdown();
	 * 
	 * }
	 *//**
		* @Description: 测试两个帐户之间操作的影响, 即操作帐户A , 看看帐户B 可不同时操作. 先进帐户A , 再进帐户B．　
		* 
		* @throws InterruptedException
		*/
	/*
	 * @SuppressWarnings("static-access")
	 * 
	 * @Test public void testCase3() throws InterruptedException {
	 * 
	 * //创建并发访问的账户 MyCount myCount = new MyCount("95599200901215522", 1, 10000,
	 * reentrantLockRepo); //创建一个线程池 ExecutorService pool =
	 * Executors.newFixedThreadPool(2); Thread t1 = new SaveThread("张三",
	 * myCount, 2000);
	 * 
	 * MyCount myCount2 = new MyCount("95599200901215522", 2, 10000,
	 * reentrantLockRepo);
	 * 
	 * Thread t2 = new SaveThread("李四", myCount2, 3600);
	 * 
	 * //最后应该是 //执行各个线程 pool.execute(t1); pool.execute(t2);
	 * Thread.currentThread().sleep(50000); //关闭线程池 pool.shutdown();
	 * 
	 * }
	 */
}

/**
 * 存款线程类
 */
class SaveThread extends Thread {
	private String name; // 操作人
	private MyCount myCount; // 账户
	private int x; // 存款金额

	SaveThread(String name, MyCount myCount, int x) {
		this.name = name;
		this.myCount = myCount;
		this.x = x;
	}

	public void run() {
		try {
			myCount.saving(x, name);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}

/**
 * 取款线程类
 */
class DrawThread extends Thread {
	private String name; // 操作人
	private MyCount myCount; // 账户
	private int x; // 存款金额

	DrawThread(String name, MyCount myCount, int x) {
		this.name = name;
		this.myCount = myCount;
		this.x = x;
	}

	public void run() {
		try {
			myCount.drawing(x, name);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}

/**
 * 普通银行账户，不可透支
 */
class MyCount {
	private String oid; // 账号
	private Integer accountCode; // 账户编号
	private int cash; // 账户余额
	private LockUnitReentrant reentrantLockRepo; // 锁库对象

	MyCount(String oid, Integer accountCode, int cash, LockUnitReentrant reentrantLockRepo) {
		this.oid = oid;
		this.accountCode = accountCode;
		this.cash = cash;
		this.reentrantLockRepo = reentrantLockRepo;
	}

	/**
	 * 存款
	 * 
	 * @param x
	 *            操作金额
	 * @param name
	 *            操作人
	 * @throws InterruptedException
	 */
	public void saving(int x, String name) throws InterruptedException {
		if (x > 0) {
			if (!reentrantLockRepo.getOne(accountCode).tryLock(20, TimeUnit.SECONDS)) {
				System.out.println("20秒内,没有得到锁对象 . ");
				return;
			}

			cash += x; // 存款
			System.out.println(name + "存款" + x + "，当前余额为" + cash);

			// notifyAll(); 加这个会出错的. //唤醒所有等待线程。

			reentrantLockRepo.getOne(accountCode).unlock();

		}
	}

	/**
	 * 取款
	 * 
	 * @param x
	 *            操作金额
	 * @param name
	 *            操作人
	 * @throws InterruptedException
	 */
	public void drawing(int x, String name) throws InterruptedException {

		if (!reentrantLockRepo.getOne(accountCode).tryLock(20, TimeUnit.SECONDS)) {
			System.out.println("20秒内,没有得到锁对象 . ");
			return;
		}

		if (cash - x < 0) {
			try {
				wait();
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
		} else {
			cash -= x; // 取款
			System.out.println(name + "取款" + x + "，当前余额为" + cash);
		}
		reentrantLockRepo.getOne(accountCode).unlock();

		// 唤醒所有存款操作
	}
}
