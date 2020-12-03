package com.maple.concurrent;

import java.util.Random;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * @author chenzhongzhi
 * @version 1.0
 * @date 2020/12/3 23:02
 */
public class CyclicBarrierT {

    public static void main(String[] args) throws InterruptedException {
        CyclicBarrier cb = new CyclicBarrier(10, new BarrierThread());
        for (int i = 0; i < 10; i++) {
            new TestThread(String.valueOf(i), cb).start();
        }
        new TestThread("0", cb).start();
        Thread t1 = new TestThread("1", cb);
        t1.start();
        Thread.sleep(1000l);
        t1.interrupt();
        System.out.println("<finish>");

    }

}
class TestThread extends Thread {

    private String name;

    private CyclicBarrier cb;

    public TestThread(String name, CyclicBarrier cb) {
        this.name = name;
        this.cb = cb;
    }

    @Override
    public void run() {
        System.out.println(String.format("%s - finish init....", name));
        try {
            cb.await();
            System.out.println(String.format("%s - 1st run...", name));
            Thread.sleep(new Random().nextInt(10) +1);
            System.out.println(String.format("%s - 1st finish", name));
            cb.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (BrokenBarrierException e) {
            e.printStackTrace();
        }
    }
}

class BarrierThread extends Thread {

    @Override
    public void run() {
        System.out.println(">>>> finish " + this.hashCode());
    }
}