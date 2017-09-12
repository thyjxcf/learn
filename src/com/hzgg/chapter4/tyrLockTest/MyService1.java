package com.hzgg.chapter4.tyrLockTest;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

public class MyService1 {
    private ReentrantLock lock = new ReentrantLock();

    public void waitMethod(){
        try {
            if(lock.tryLock(3, TimeUnit.SECONDS)){

                System.out.println("  " + Thread.currentThread().getName() + " get lock time ==" + System.currentTimeMillis());
            }else{
                System.out.println(" not get this lock");
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
