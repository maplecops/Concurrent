package com.maple.thread;

/**
 * 线程的等待与通知
 * 其中需要特殊说明的是，线程的等待和通知，都是Object级别的方法，所以线程的等待和通知，都必须在同步锁sync下进行
 * 且需要将当前线程信息绑定到当前加锁事物上，进入当前加锁对象的等待队列
 * 需要特别注意的是
 * 1. wait notify notifyAll都需要在对一个事物加锁后进行
 * 2. wait 后释放加锁的资源，在被notify后重新竞争锁
 * 3. wait 后虽然释放了加锁资源，但是线程处于WAITING状态，如果此时被interrupt，还是会抛出异常
 *
 * @author chenzhongzhi
 * @version 1.0
 * @date 2020/12/2 16:54
 */
public class WaitAndNotify {

    private static final Object lockObject = new Object();

    private static class WaitThread extends Thread {
        @Override
        public void run() {
            System.out.println(String.format(">>>WAIT<<< %s-%s", Thread.currentThread().getName(), "start"));
            synchronized (lockObject) {
                try {
                    System.out.println(String.format(">>>WAIT<<< %s-%s", Thread.currentThread().getName(), " waiting for lock"));
                    lockObject.wait();
                    System.out.println(String.format(">>>WAIT<<< %s-%s", Thread.currentThread().getName(), " unlocked."));
                } catch (InterruptedException e) {
                    // _ignore
                    e.printStackTrace();
                }
            }
        }
    }

    private static class NotifyThread extends Thread {
        @Override
        public void run() {
            System.out.println(String.format(">>>NOTIFY<<< %s-%s", Thread.currentThread().getName(), "start"));
            synchronized (lockObject) {
                System.out.println(String.format(">>>NOTIFY<<< %s-%s", Thread.currentThread().getName(), " waiting notify"));
                lockObject.notify();
                // lockObject.notifyAll();
                System.out.println(String.format(">>>NOTIFY<<< %s-%s", Thread.currentThread().getName(), " finish notify."));
            }
        }
    }

    public static void main(String[] args) {
        WaitThread wt1 = new WaitThread();
        WaitThread wt2 = new WaitThread();
        wt1.start();
        wt2.start();

        NotifyThread nt1 = new NotifyThread();
        nt1.start();
    }

}
