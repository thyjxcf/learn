package com.hzgg.chapter4.reentrantLock2;

public class Run {

    public static void main(String[] args) {
        MyService myService = new MyService();

        ThreadA threadA = new ThreadA(myService);
        threadA.start();

        ThreadB threadB = new ThreadB(myService);
        threadB.start();

    }
}
