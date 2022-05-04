package com.lina.baselibs.utils;

import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;
import android.text.TextUtils;
import android.util.Log;

import java.io.File;

public class MediaUtils {

    /**
     * 获取媒体文件时长
     *
     * @param path 文件路径
     */
    public static int getDuration(String path) {
        if (TextUtils.isEmpty(path)) {
            return 0;
        }
        return getDuration(new File(path));
    }

    /**
     * 获取媒体文件时长
     *
     * @param file 文件
     */
    public static int getDuration(File file) {
        if (file == null || !file.exists()) {
            return 0;
        }
        MediaMetadataRetriever retriever = new MediaMetadataRetriever();
        retriever.setDataSource(file.getAbsolutePath());
        String s = retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION);
        retriever.release();
        return Integer.parseInt(s) / 1000;
    }

    /**
     * 获取视频文件某帧的图片
     *
     * @param path 文件路径
     * @param time 时间，单位：ms
     * @return
     */
    public static Bitmap getBitmap(String path, int time) {
        if (TextUtils.isEmpty(path)) {
            return null;
        }
        return getBitmap(new File(path), time);
    }

    /**
     * 获取视频文件某帧的图片
     *
     * @param file 文件
     * @param time 时间，单位：ms
     * @return
     */
    public static Bitmap getBitmap(File file, int time) {
        MediaMetadataRetriever retriever = new MediaMetadataRetriever();
        retriever.setDataSource(file.getAbsolutePath());
        //获得第10帧图片 这里的第一个参数以微秒为单位
        Bitmap bitmap = retriever.getFrameAtTime(time * 1000, MediaMetadataRetriever.OPTION_CLOSEST_SYNC);
        retriever.release();
        return bitmap;
    }

    /**
     * 获取视频文件的首帧
     *
     * @param path 文件路径
     * @return
     */
    public static Bitmap getFirstFrame(String path) {
        if (TextUtils.isEmpty(path)) {
            return null;
        }
        return getFirstFrame(new File(path));
    }

    /**
     * 获取视频文件的首帧
     *
     * @param file 文件
     * @return
     */
    public static Bitmap getFirstFrame(File file) {
        MediaMetadataRetriever retriever = new MediaMetadataRetriever();
        retriever.setDataSource(file.getAbsolutePath());
        Bitmap bitmap = retriever.getFrameAtTime();
        retriever.release();
        return bitmap;
    }

}
