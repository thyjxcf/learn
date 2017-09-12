package com.hzgg.chapter4.reentrantLock2;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class MyService {

    private ReentrantLock lock = new ReentrantLock();
    private Condition condition = lock.newCondition();
    private boolean hasValue = false;

    public void set(){
        try{
            lock.lock();
            while(hasValue == true){
                    condition.await();
            }
            System.out.println("print 11");
            hasValue = true;
            condition.signal();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    public void get(){
        try{
            lock.lock();
            while(hasValue == false){
                condition.await();
            }
            System.out.println("print 22");
            hasValue = false;
            condition.signal();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
