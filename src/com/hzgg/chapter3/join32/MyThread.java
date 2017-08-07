package com.hzgg.chapter3.join32;

public class MyThread extends  Thread {

    @Override
    public void run() {

        int secondValue = (int) (Math.random()*1000);

        try {
            Thread.sleep(secondValue);
            System.out.println("this thread is " + Thread.currentThread().getName());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
