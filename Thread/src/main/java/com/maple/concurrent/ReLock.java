package com.maple.concurrent;

import java.util.concurrent.locks.ReentrantLock;

/**
 * 重入锁
 *  重入锁用于保护临界区资源，可以加在线程的任意位置，但是需要保证每一次的lock，都需要与之对应的unlock(lock与unlock都需要开发人员手动指定)
 *
 *
 * @author chenzhongzhi
 * @version 1.0
 * @date 2020/12/3 20:31
 */
public class ReLock {

    private static ReentrantLock reentrantLock = new ReentrantLock();

    private static Integer i = 0;

    public static void main(String[] args) throws InterruptedException {
        /*TestThread t1 = new TestThread();
        TestThread t2 = new TestThread();

        t1.start();
        t2.start();
        t1.join();
        t2.join();

        System.out.println(i);*/
        TestLockInterruptThread thread = new TestLockInterruptThread();
        thread.start();
        Thread.sleep(1000l);
        thread.interrupt();

    }

    private static class TestThread extends Thread {

        @Override
        public void run() {
            for (int j = 0; j < 10000; j++) {
                reentrantLock.lock();
                try {
                    i++;
                }finally {
                    reentrantLock.unlock();
                }
            }

        }
    }

    private static class TestLockInterruptThread extends Thread {

        @Override
        public void run() {
            try {
                System.out.println("in interrupt-thread....");
                reentrantLock.lockInterruptibly();
                Thread.sleep(3 * 1000l);
                reentrantLock.unlock();
                System.out.println("out interrupt-thread....");
            } catch (InterruptedException e) {
                e.printStackTrace();
                System.out.println("exception in interrupt-thread....");
            }finally {
                if(reentrantLock.isHeldByCurrentThread()){
                    System.out.println("finally get lock status is " + reentrantLock.isHeldByCurrentThread());
                    reentrantLock.unlock();
                }
                System.out.println("out of interrupt-thread....");
            }
        }
    }

}
