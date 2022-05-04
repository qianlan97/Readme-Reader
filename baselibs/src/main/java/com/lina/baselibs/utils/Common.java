package com.lina.baselibs.utils;

import android.annotation.SuppressLint;

import java.text.SimpleDateFormat;

public interface Common {


    @SuppressLint("SimpleDateFormat")
    interface DateFormat {

        SimpleDateFormat WITH_HMS = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat WITH_HMS_SCREEN = new SimpleDateFormat("yyyyMMddHHmmss");
        SimpleDateFormat WITHOUT_HMS = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat WITHOUT_HMS_CHINA = new SimpleDateFormat("yyyy年MM月dd日 HH时mm分ss秒");
        SimpleDateFormat WITHOUT_HMS_00 = new SimpleDateFormat("yyyy-MM-dd 00:00:00");
        SimpleDateFormat HHMM = new SimpleDateFormat("HH:mm");
        SimpleDateFormat HMM = new SimpleDateFormat("H:mm");
        SimpleDateFormat MMDDHHmm = new SimpleDateFormat("MM-dd HH:mm");
        SimpleDateFormat CN_M_D = new SimpleDateFormat("M月d日");
        SimpleDateFormat CN_MM_DD = new SimpleDateFormat("MM月dd日");
        SimpleDateFormat CN_MD_H_m = new SimpleDateFormat("M月d日 H时m分");
        SimpleDateFormat CN_WITHOUT_HMS = new SimpleDateFormat("yyyy年MM月dd日");
        SimpleDateFormat CN_WITHOUT_HM = new SimpleDateFormat("yyyy年MM月dd日 HH时mm分");
        SimpleDateFormat WITHOUT_HMS_59 = new SimpleDateFormat("yyyy-MM-dd 23:59:59");
        SimpleDateFormat HH_MM_SS = new SimpleDateFormat("HH时mm分ss秒");
    }
    

}
