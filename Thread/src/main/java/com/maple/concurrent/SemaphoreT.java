package com.maple.concurrent;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

/**
 * 信号量 - 指定了同时有多少个线程可以访问某一资源
 * @author chenzhongzhi
 * @version 1.0
 * @date 2020/12/3 22:16
 */
public class SemaphoreT {

    private static final Semaphore semap = new Semaphore(3);

    private static class TestThread extends Thread {

        @Override
        public void run() {
            try {
                semap.acquire();
                Thread.sleep(2 * 1000L);
                System.out.println(Thread.currentThread().getId() + " DONE" + " SEMAP COUNT is " + semap.availablePermits());
                semap.release();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Thread t = new TestThread();
        ExecutorService es = Executors.newFixedThreadPool(10);
        for (int i = 0; i < 20; i++) {
            es.execute(t);
        }
        es.shutdown();
    }

}
