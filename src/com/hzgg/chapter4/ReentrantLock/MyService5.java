package com.hzgg.chapter4.ReentrantLock;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class MyService5 {

    private Lock lock = new ReentrantLock();
    private Condition condition = lock.newCondition();

    public void awaitA(){
        try {
            lock.lock();
            System.out.println("begin awaitA time is ==" + System.currentTimeMillis() + "thread name is ==" + Thread.currentThread().getName());

            condition.await();
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

            condition.await();
            System.out.println("end  awaitB time is ==" + System.currentTimeMillis() + "thread name is ==" + Thread.currentThread().getName());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            lock.unlock();
        }
    }

    public void singleAll(){
        try{
            lock.lock();
            System.out.println("singleAll time is ==" + System.currentTimeMillis());
            condition.signalAll();
        }finally {
            lock.unlock();
        }
    }
}
