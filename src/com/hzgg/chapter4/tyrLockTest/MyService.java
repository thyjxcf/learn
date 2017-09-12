package com.hzgg.chapter4.tyrLockTest;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class MyService {
    private Lock lock = new ReentrantLock();

    public void waitMethod(){
        if(lock.tryLock()){
            System.out.println("get this lock " + Thread.currentThread().getName());
        }else{
            System.out.println(" not get thisdfds lock" + Thread.currentThread().getName());
        }
    }
}
