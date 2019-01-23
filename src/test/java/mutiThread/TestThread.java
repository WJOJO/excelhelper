package mutiThread;

import org.junit.Test;

/**
 * @author Javon Wang
 * @description 多线程测试
 * @create 2019-01-09 14:14
 */
public class TestThread {

    public void testRun(final int length, final Thread mainThread){


    }


    public static void main(String[] args) throws Exception{
        TestThread testThread = new TestThread();
        testThread.testRun(8, Thread.currentThread());
//        Thread.sleep(1000);
    }

}
