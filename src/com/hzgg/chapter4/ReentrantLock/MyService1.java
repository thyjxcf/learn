package com.hzgg.chapter4.ReentrantLock;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class MyService1 {

    private Lock lock = new ReentrantLock();

    public void methodA(){
        try{
            lock.lock();
            System.out.println("methodA begin threadName =" + Thread.currentThread().getName() + " time = " + System.currentTimeMillis());

            Thread.sleep(1000);
            System.out.println("methodA begin threadName =" + Thread.currentThread().getName() + " time = " + System.currentTimeMillis());
        }catch (InterruptedException e){
            e.printStackTrace();
        }finally {
            lock.unlock();
        }
    }

    public void methodB(){
        try{
            lock.lock();
            System.out.println("methodB begin threadName =" + Thread.currentThread().getName() + " time = " + System.currentTimeMillis());

            Thread.sleep(1000);
            System.out.println("methodB begin threadName =" + Thread.currentThread().getName() + " time = " + System.currentTimeMillis());
        }catch (InterruptedException e){
            e.printStackTrace();
        }finally {
            lock.unlock();
        }
    }
}
