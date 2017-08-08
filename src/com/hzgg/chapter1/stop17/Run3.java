package com.hzgg.chapter1.stop17;

public class Run3 {

    public static void main(String[] args) {
        try {
            MyThread1 thread1 = new MyThread1();
            thread1.start();
            Thread.sleep(1000);
            thread1.interrupted();
            System.out.println(" is stop 1 ==" + thread1.isInterrupted());
            System.out.println("thhhd");
            System.out.println(" is stop 2 ==" + thread1.isInterrupted());
            System.out.println("thhhd");
        } catch (InterruptedException e) {
            System.out.println("main catch ");
            e.printStackTrace();
        }
        System.out.println("end");
    }
}
