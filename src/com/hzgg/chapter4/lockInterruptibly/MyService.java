package com.hzgg.chapter4.lockInterruptibly;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class MyService {
    private ReentrantLock lock = new ReentrantLock();
    private Condition condition =lock.newCondition();

    public void waitMethod(){
        try{
//            lock.lock();

                lock.lockInterruptibly();
            System.out.println("lock begin " + Thread.currentThread().getName());
            for(int i=0;i<Integer.MAX_VALUE/10;i++){
                String str = new String();
                double l = Math.random();
            }
            System.out.println("lock end " + Thread.currentThread().getName());
        }catch (InterruptedException e) {
            e.printStackTrace();
        }
        finally {
            if(lock.isHeldByCurrentThread()){
                lock.unlock();
            }
        }
    }
}
