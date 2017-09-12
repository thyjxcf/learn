package com.hzgg.chapter5.Timertest1;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class Run1 {
    private static Timer timer = new Timer(true);
    static public class MyTask extends TimerTask{

        @Override
        public void run() {
            System.out.println(" now time is ==" + new Date());
        }
    }

    public static void main(String[] args) {
        MyTask myTask = new MyTask();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String str = "2017-08-10 10:40:00";
        try {
            Date date = format.parse(str);
            System.out.println(" tstr is " + new Date());

            timer.schedule(myTask,date);
        } catch (ParseException e) {
            e.printStackTrace();
        }

    }
}
