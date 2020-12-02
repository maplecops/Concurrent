package com.maple.thread;

/**
 * 线程中断并不会使线程立即退出，而是给线程发送一个通知，告知目标线程有人希望你退出。
 * 但是目标线程接收到通知后如何处理，完全由目标线程自行决定
 *          //中断线程
 *          Thread.currentThread().interrupt();
 *          // 判断当前线程是否被中断
 *          Thread.currentThread().isInterrupted();
 *          // 判断当前线程是否被中断：并清除中断标记
 *          Thread.interrupted();
 * @author chenzhongzhi
 * @version 1.0
 * @date 2020/12/2 10:35
 */
public class InterruptThread {

    public static void main(String[] args) throws InterruptedException {

        Thread interruptThead = new Thread(()->{
            while( true) {
                if( Thread.currentThread().isInterrupted()) {
                    System.out.println(String.format("%s-%s",Thread.currentThread().getName()," current thread been " +
                            "called" +
                            " interrupted!"));
                    return;
                }
                System.out.println(String.format("%s-Run Success", Thread.currentThread().getName()));
                try {
                    Thread.sleep(1000l);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    System.out.println(String.format("%s-%s",Thread.currentThread().getName()," current thread been " +
                            "called" +
                            " interrupted when sleep!"));
                    // 重新打interrupt标记，原因为
                    //The interrupted status of the current thread is cleared when this exception is thrown.
                    Thread.currentThread().interrupt();
                }
            }
        });

        interruptThead.start();
        Thread.sleep(600l);
        interruptThead.interrupt();
    }

}
