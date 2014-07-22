package com.yang.javalib.threadUtil.testcase1syn;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @Description: 同步块测试
 * @author yangxiaodon
 * @Email  coder.yang2010@gmail.com
 * @date  2014-4-13 下午9:36:43 
 */
/** 
* Java线程：改为同步代码块 
* 
* @author leizhimin 2009-11-5 10:57:29 
*/
public class SynThreadTestCase {

        public static void main(String[] args) { 
                //创建并发访问的账户 
                MyCount myCount = new MyCount("95599200901215522", 10000); 
                //创建一个线程池 
                ExecutorService pool = Executors.newFixedThreadPool(2); 
                Thread t1 = new SaveThread("张三", myCount, 2000); 
                Thread t2 = new SaveThread("李四", myCount, 3600); 
                Thread t3 = new DrawThread("王五", myCount, 2700); 
                Thread t4 = new SaveThread("老张", myCount, 600); 
                Thread t5 = new DrawThread("老牛", myCount, 1300); 
                Thread t6 = new DrawThread("胖子", myCount, 800); 
                //执行各个线程 
                pool.execute(t1); 
                pool.execute(t2); 
                pool.execute(t3); 
                pool.execute(t4); 
                pool.execute(t5); 
                pool.execute(t6); 
                //关闭线程池 
                pool.shutdown(); 
        } 
} 

/** 
* 存款线程类 
*/ 
class SaveThread extends Thread { 
        private String name;                //操作人 
        private MyCount myCount;        //账户 
        private int x;                            //存款金额 

        SaveThread(String name, MyCount myCount, int x) { 
                this.name = name; 
                this.myCount = myCount; 
                this.x = x; 
        } 

        public void run() { 
                myCount.saving(x, name); 
        } 
} 

/** 
* 取款线程类 
*/ 
class DrawThread extends Thread { 
        private String name;                //操作人 
        private MyCount myCount;        //账户 
        private int x;                            //存款金额 

        DrawThread(String name, MyCount myCount, int x) { 
                this.name = name; 
                this.myCount = myCount; 
                this.x = x; 
        } 

        public void run() { 
                myCount.drawing(x, name); 
        } 
} 


/** 
* 普通银行账户，不可透支 
*/ 
class MyCount { 
        private String oid;                         //账号 
        private int cash;                             //账户余额 

        MyCount(String oid, int cash) { 
                this.oid = oid; 
                this.cash = cash; 
        } 

        /** 
         * 存款 
         * 
         * @param x        操作金额 
         * @param name 操作人 
         */ 
        public void saving(int x, String name) { 
                if (x > 0) { 
                        synchronized (this) { 
                                cash += x;                    //存款 
                                System.out.println(name + "存款" + x + "，当前余额为" + cash); 
                                notifyAll();            //唤醒所有等待线程。 
                        } 
                } 
        } 

        /** 
         * 取款 
         * 
         * @param x        操作金额 
         * @param name 操作人 
         */ 
        public synchronized void drawing(int x, String name) { 
                synchronized (this) { 
                        if (cash - x < 0) { 
                                try { 
                                        wait(); 
                                } catch (InterruptedException e1) { 
                                        e1.printStackTrace(); 
                                } 
                        } else { 
                                cash -= x;                     //取款 
                                System.out.println(name + "取款" + x + "，当前余额为" + cash); 
                        } 
                } 
                notifyAll();             //唤醒所有存款操作 
        } 
}
 