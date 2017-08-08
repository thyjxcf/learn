package com.hzgg.chapter1.stop17;

public class MyThread4 extends Thread {

    @Override
    public void run() {

//        for(int i=0;i<500000;i++){
//
//            if(this.interrupted()){
//                System.out.println("thread is stop ===");
//                break;
//            }
//            System.out.println("i ===" + (i+1));
//        }
//        System.out.println("end  ");

        try{
            for(int i=0;i<500000;i++){

                if(this.interrupted()){
                    System.out.println(" this thread is exit");
                    throw new InterruptedException();
                }
            }
            System.out.println("for loop is exit");
        }catch ( InterruptedException e){
            System.out.println("main catch ");
            e.printStackTrace();
        }
    }
}
