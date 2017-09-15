package net.zdsoft.office.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class IndexUtilsV4 {

    private IndexUtilsV4() {
    }

    public static String getDateName() {
        Date date = new Date();
        SimpleDateFormat format = new SimpleDateFormat("HH");
        int time = Integer.parseInt(format.format(date));
        /**
         * 00-03(拂晓) 03-06(黎明) 06-09(清晨) 09-12(上午) 12-15(中午) 15-18(下午) 18-21(傍晚) 21-00(深夜/午夜)
         */
        String current = "";
        if (0 <= time && time <= 8) {
            current = "早上好";
        }
        else if (9 <= time && time <= 11) {
            current = "上午好";
        }
        else if (12 <= time && time <= 14) {
            current = "中午好";
        }
        else if (15 <= time && time <= 17) {
            current = "下午好";
        }
        else if (18 <= time && time <= 23) {
            current = "晚上好";
        }
        return current;
    }

    public static Date addMonth(Date date, int month) {
        if (date == null) {
            return null;
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MONTH, month);
        return calendar.getTime();

    }

    public static void main(String[] args) {

        System.out.println(addMonth(new Date(), -3));

    }

}
