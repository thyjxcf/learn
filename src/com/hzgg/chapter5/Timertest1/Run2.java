package com.hzgg.chapter5.Timertest1;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class Run2 {
    private  Timer timer = new Timer();

    static public class TimerTaskA extends TimerTask{

        @Override
        public void run() {

            try {
                System.out.println("A run time is " +  new Date());
                Thread.sleep(5000);
                System.out.println("A end time is " + new Date());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    static public class TimerTaskB extends TimerTask{

        @Override
        public void run() {

            try {
                System.out.println("A run time is " +  new Date());
                Thread.sleep(5000);
                System.out.println("A end time is " + new Date());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    } static public class TimerTaskC extends TimerTask{

        @Override
        public void run() {

            try {
                System.out.println("A run time is " +  new Date());
                Thread.sleep(5000);
                System.out.println("A end time is " + new Date());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    static public class TimerTaskD extends TimerTask{

        @Override
        public void run() {
            try {
                System.out.println("A run time is " +  new Date());
                Thread.sleep(5000);
                System.out.println("A end time is " + new Date());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    public static void main(String[] args) {
        TimerTaskA timerTask = new TimerTaskA();
        TimerTaskB timerTaskB = new TimerTaskB();
        TimerTaskC timerTaskC = new TimerTaskC();
        TimerTaskD timerTaskD = new TimerTaskD();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        int  k = 4;
        int j = k >> 1;
        System.out.println(j);
        try {
            Date date1 = format.parse("2017-08-12  10:58:00");
            Date date2 = format.parse("2017-08-14  10:58:00");
            Date date3 = format.parse("2017-08-11  10:58:00");
            Date date4 = format.parse("2017-08-13  10:58:00");

            Timer timer = new Timer();
            timer.schedule(timerTask,date1);
            timer.schedule(timerTaskB,date2);
            timer.schedule(timerTaskC,date3);
            timer.schedule(timerTaskD,date4);
            System.out.println("hello");
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}
