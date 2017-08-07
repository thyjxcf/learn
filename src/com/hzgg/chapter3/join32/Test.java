package com.hzgg.chapter3.join32;

public class Test {

    public static void main(String[] args) {

        MyThread myThread = new MyThread();
        myThread.start();
        try {
            myThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("want mythrad is stop ");
    }
}
