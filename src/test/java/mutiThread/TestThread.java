package mutiThread;

import org.junit.Test;

/**
 * @author Javon Wang
 * @description 多线程测试
 * @create 2019-01-09 14:14
 */
public class TestThread {

    public void testRun(final int length, final Thread mainThread){
        new Thread(()-> {
            try {
                System.out.println("线程开始等待");
                Thread.sleep(100);
                System.out.println("main线程是否存活：" + mainThread.isAlive());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("第一个线程处理数字长度" + length);
        }).start();

    }


    public static void main(String[] args) throws Exception{
        TestThread testThread = new TestThread();
        testThread.testRun(8, Thread.currentThread());
//        Thread.sleep(1000);
    }

}
