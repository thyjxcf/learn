package com.hzgg.chapter4.ReentrantLock;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class MyService3 {
    private Lock lock = new ReentrantLock();
    private Condition condition = lock.newCondition();

    public void await(){


            try {
                lock.lock();
                System.out.println("now time is " +System.currentTimeMillis());
                condition.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }finally {
                lock.unlock();
            }
    }
    public void single(){
        try{
            lock.lock();
            condition.signal();
            System.out.println("this time is " + System.currentTimeMillis());
        }finally {
            lock.unlock();
        }

    }


}
