package com.hzgg.chapter4.ReentrantLock;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class MyService6 {
    private Lock lock = new ReentrantLock();
    private Condition conditionA = lock.newCondition();
    private Condition conditionB = lock.newCondition();
    public void awaitA(){
        try {
            lock.lock();
            System.out.println("begin awaitA time is ==" + System.currentTimeMillis() + "thread name is ==" + Thread.currentThread().getName());

            conditionA.await();
            System.out.println("end  awaitA time is ==" + System.currentTimeMillis() + "thread name is ==" + Thread.currentThread().getName());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            lock.unlock();
        }
    }
    public void awaitB(){
        try {
            lock.lock();
            System.out.println("begin awaitB time is ==" + System.currentTimeMillis() + "thread name is ==" + Thread.currentThread().getName());

            conditionB.await();
            System.out.println("end  awaitB time is ==" + System.currentTimeMillis() + "thread name is ==" + Thread.currentThread().getName());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            lock.unlock();
        }
    }

    public void singleAll_A(){
        try{
            lock.lock();
            System.out.println("singleAll time is ==" + System.currentTimeMillis());
            conditionA.signalAll();
        }finally {
            lock.unlock();
        }
    }
    public void singleAll_B(){
        try{
            lock.lock();
            System.out.println("singleAll time is ==" + System.currentTimeMillis());
            conditionB.signalAll();
        }finally {
            lock.unlock();
        }
    }
}
