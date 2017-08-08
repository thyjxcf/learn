package com.hzgg.chapter1.stop17;

public class Run4 {

    public static void main(String[] args) {
        MyThread4 myThread4 = new MyThread4();
        myThread4.start();
        try {
            Thread.sleep(2000);
            myThread4.interrupt();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
