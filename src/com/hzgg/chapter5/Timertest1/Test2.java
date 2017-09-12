package com.hzgg.chapter5.Timertest1;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class Test2 {

    public static void main(String[] args) {
        Timer timer = new Timer();

        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                System.out.println("is running not time is ++===" + new Date());
                System.out.println( "daemon === " +Thread.currentThread().isDaemon());
            }
        };
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateStr = "2017-08-14 11:55:00";
        System.out.println(" now time is now is aaa" + new Date());
        System.out.println( "main  daemon === " +Thread.currentThread().isDaemon());
        try {
            Date date = format.parse(dateStr);

            timer.schedule(timerTask,date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}
