package com.maple.thread;

/**
 * @author chenzhongzhi
 * @version 1.0
 * @date 2020/12/2 17:29
 */
public class ThreadGroupT extends Thread {

    public static void main(String[] args) {
        ThreadGroup group = new ThreadGroup("TestGroup");
        Thread t1 = new Thread(group, new ThreadGroupT());
        Thread t2 = new Thread(group, new ThreadGroupT());

        t1.start();
        t2.start();

        System.out.println(group.activeCount());
        group.list();
    }


    @Override
    public void run() {
        String currentThreadGroupName = Thread.currentThread().getThreadGroup().getName();
        System.out.println(String.format("%s-%s-%s", currentThreadGroupName, Thread.currentThread().getName(),
                "RUNNING"));
        try {
            Thread.sleep(5 * 1000l);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
