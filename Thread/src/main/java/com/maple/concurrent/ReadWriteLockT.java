package com.maple.concurrent;

import java.util.Random;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 读写锁使用重入读写锁
 *
 * @author chenzhongzhi
 * @version 1.0
 * @date 2020/12/3 22:34
 */
public class ReadWriteLockT {

    private static final ReadWriteLock readWriteLock = new ReentrantReadWriteLock();

    private static final Lock readLock = readWriteLock.readLock();

    private static final Lock writeLock = readWriteLock.writeLock();

    private static int i = 0;

    public static void main(String[] args) {
        for (int j = 0; j < 20; j++) {
            if( j % 7 == 0) {
                new WriteThread().start();
            }else {
                new ReadThread().start();
            }

        }
    }

    public static class ReadThread extends Thread {
        @Override
        public void run() {
            readLock.lock();
            try {
                System.out.println("read thread get value " + i);
                Thread.sleep(1 * 1000l);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                readLock.unlock();
            }
        }
    }

    public static class WriteThread extends Thread {
        @Override
        public void run() {
            writeLock.lock();
            try {
                i = new Random().nextInt();
                System.out.println("write thread modify i to " + i);
                Thread.sleep(1000l);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                writeLock.unlock();
            }
        }
    }

}
