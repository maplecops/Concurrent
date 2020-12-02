package com.maple.thread;

/**
 * 中止线程
 *  Thread.stop()在结束线程时，会直接中止线程，并且立即释放这个线程所持有的锁
 *
 * @author chenzhongzhi
 * @version 1.0
 * @date 2020/12/2 10:11
 */
public class StopThread {

    private static User user = new User();

    public static void main(String[] args) throws InterruptedException {
        new Thread(new ReadUserThread()).start();
        //new Thread(new ChangeUserThread()).start();
        while (true) {
            Thread changeThread = new Thread(new ChangeUserThread());
            changeThread.start();
            Thread.sleep(150l);
            changeThread.stop();
        }
    }

    private static class User {
        private String id;

        private String userName;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        @Override
        public String toString() {
            return "User{" +
                    "id='" + id + '\'' +
                    ", userName='" + userName + '\'' +
                    '}';
        }
    }

    public static class ChangeUserThread implements Runnable {

        @Override
        public void run() {
            while (true) {
                synchronized (user) {
                    String currentTime = String.valueOf(System.currentTimeMillis());
                    user.setId(String.valueOf(currentTime));
                    try {
                        Thread.sleep(100l);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    user.setUserName(String.valueOf(currentTime));
                }
                Thread.yield();
            }
        }
    }

    public static class ReadUserThread implements Runnable {

        @Override
        public void run() {
            while (true) {
                synchronized (user) {
                    System.out.println(user);
                }
                Thread.yield();
            }
        }
    }
}
