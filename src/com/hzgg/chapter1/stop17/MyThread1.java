package com.hzgg.chapter1.stop17;

public class MyThread1 extends Thread {

    @Override
    public void run() {

        for(int i=0;i<50000;i++){
            System.out.println("i ==" + (i+1));
        }
    }
}
