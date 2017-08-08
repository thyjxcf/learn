package com.hzgg.chapter1.stop17;

public class Run1 {

    public static void main(String[] args) {

        try {
            MyThread1 thread1 = new MyThread1();
            thread1.start();
            Thread.sleep(1000);
            thread1.interrupt();
            System.out.println(" is stop 1 ==" + thread1.interrupted());
            System.out.println(" is stop 2 ==" + thread1.interrupted());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
