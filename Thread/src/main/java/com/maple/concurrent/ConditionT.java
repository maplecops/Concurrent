package com.maple.concurrent;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Condition 是配合 Lock 使用的线程等待或者恢复的类
 * @author chenzhongzhi
 * @version 1.0
 * @date 2020/12/3 21:10
 */
public class ConditionT {

    private static ReentrantLock lock = new ReentrantLock();

    private static Condition condition = lock.newCondition();

    public static void main(String[] args) throws InterruptedException {
        LockAndWaitThread lockAndWaitThread = new LockAndWaitThread();
        AcquiredLockThread acquiredLockThread = new AcquiredLockThread();

        lockAndWaitThread.start();
        acquiredLockThread.start();
        acquiredLockThread.join();
        System.out.println("MainThread join finish...");
        lock.lock();
        condition.signal();
        lock.unlock();
    }

    private static class LockAndWaitThread extends Thread {

        @Override
        public void run() {
            System.out.println("thread start with lock");
            lock.lock();
            System.out.println("thread get lock " + lock.getHoldCount());
            try {
                Thread.sleep(2 * 1000L);
                System.out.println("thread start with await");
                condition.await();
                System.out.println("thread stop with await");
                Thread.sleep(10 * 1000L);
            } catch (InterruptedException e) {
                System.out.println("thread await with exception");
            } finally {
                System.out.println("thread finish and unlock");
                lock.unlock();
            }
        }
    }

    private static class AcquiredLockThread extends Thread {

        boolean flag = false;

        @Override
        public void run() {
            try {
                Thread.sleep(2 * 1000L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            while (!flag) {
                flag = lock.tryLock();
            }
            System.out.println("AcquiredThread try to get lock result is >>> " + flag);
            try {
                System.out.println("AcquiredThread get lock");
                Thread.sleep(3 * 1000L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                System.out.println("AcquiredThread finish");
                lock.unlock();
            }
        }
    }

}
