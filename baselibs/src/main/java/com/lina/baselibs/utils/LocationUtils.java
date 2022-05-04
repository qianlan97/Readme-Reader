package com.lina.baselibs.utils;

import java.text.DecimalFormat;

public class LocationUtils {

    public static String getDistance(double longitude, double latitue, double longitude2, double latitue2) {
        double lat1 = rad(latitue);
        double lat2 = rad(latitue2);
        double a = lat1 - lat2;
        double b = rad(longitude) - rad(longitude2);
        double s = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a / 2), 2) + Math.cos(lat1) * Math.cos(lat2) * Math.pow(Math.sin(b / 2), 2)));
        s = s * 6378137.0;
        s = Math.round(s * 10000) / 10000;
        return trans(s);
    }

    private static double rad(double d) {
        return d * Math.PI / 180.0;
    }

    private static String trans(double distance) {
        boolean isBig = false;
        if (distance >= 1000) {
            distance /= 1000;
            isBig = true;
        }
        return (new DecimalFormat(".00").format(distance)) + (isBig ? "千米" : "米");
    }

}
