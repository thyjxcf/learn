package com.hzgg.chapter4.ReentrantLock;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class MyService {

    private Lock lock = new ReentrantLock();

    public void testMethod(){
        try{
            lock.lock();

            for(int i=0;i<5;i++){
                System.out.println("this thread is " + Thread.currentThread().getName() + (i+1));
            }
        }finally {
            lock.unlock();
        }
    }
}
