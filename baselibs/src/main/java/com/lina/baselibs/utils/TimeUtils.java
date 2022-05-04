package com.lina.baselibs.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeUtils {

    public static String getDateStr(Date date, String format) {
        if (format == null || format.isEmpty()) {
            format = "yyyy-MM-dd HH:mm:ss";
        }
        SimpleDateFormat formatter = new SimpleDateFormat(format);
        return formatter.format(date);
    }

    public static String timeStamp2Date(long time, String format) {
        if (time < 10000000000L) {
            time = time * 1000;
        }
        if (format == null || format.isEmpty()) {
            format = "yyyy-MM-dd HH:mm:ss";
        }
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(new Date(time));
    }

    public static String date2TimeStamp(String date, String format) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            return String.valueOf(sdf.parse(date).getTime() / 1000);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    //毫秒转秒
    public static String long2String(long time){

        //毫秒转秒
        int sec = (int) time / 1000 ;
        int min = sec / 60 ;	//分钟
        sec = sec % 60 ;		//秒
        if(min < 10){	//分钟补0
            if(sec < 10){	//秒补0
                return "0"+min+":0"+sec;
            }else{
                return "0"+min+":"+sec;
            }
        }else{
            if(sec < 10){	//秒补0
                return min+":0"+sec;
            }else{
                return min+":"+sec;
            }
        }
    }

    //时间差
    public static long timeDiffer(String timeLong,String timeStr){
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        long diff = 0;
        try {
            diff = (format.parse(timeStr).getTime() - format.parse(timeStamp2Date(Long.parseLong(timeLong),"yyyy-MM-dd HH:mm:ss")).getTime()) / 60000;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return diff;
    }
}
