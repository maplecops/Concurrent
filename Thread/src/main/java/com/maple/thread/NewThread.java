package com.maple.thread;

/**
 * 新建线程
 * @author chenzhongzhi
 * @version 1.0
 * @date 2020/12/2 10:03
 */
public class NewThread {

    public static void main(String[] args) {
        String currentThreadName = Thread.currentThread().getName();
        System.out.println("current thread is " + currentThreadName);
        Thread thread = new Thread(()->{
            String ctn = Thread.currentThread().getName();
            System.out.println("inside thread is " + ctn);
            System.out.println("This is a new Thread.");
        });
        thread.run();
        System.out.println(">>>> run()");
        thread.start();
        System.out.println(">>>> start()");
        /**
         * 不要使用run()来开启新线程，run()只会在当前线程中，串行的执行run()中的代码
         *
         * 使用start()方法，会新建一个线程，并且让这个新建的线程执行run()中的代码
         */
    }

}
