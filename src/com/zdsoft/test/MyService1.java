package com.zdsoft.test;

public class MyService1 implements Runnable {

    private volatile boolean flag = false;


    @Override
    public void run() {
        int i=1;
        while(!flag){
            i++;
            System.out.println("hello" + i);
        }
    }

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }
}
