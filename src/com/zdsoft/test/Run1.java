package com.zdsoft.test;

import com.hzgg.chapter1.stop17.MyThread1;

public class Run1 {

//    private

    public static void main(String[] args) {
        MyService1 myService1 = new MyService1();
        Thread thread1 = new Thread(myService1);
        Thread thread = new Thread(myService1);

        thread1.start();

        try {
            Thread.sleep(10);

            myService1.setFlag(true);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
