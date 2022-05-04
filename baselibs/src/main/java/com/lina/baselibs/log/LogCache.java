package com.lina.baselibs.log;

import android.text.TextUtils;
import android.util.Log;

import com.lina.baselibs.utils.FileUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.LinkedBlockingQueue;

public class LogCache {

    public final SimpleDateFormat FORMAT_LOG = new SimpleDateFormat("dd-HH:mm:ss.SSS");
    public final SimpleDateFormat FORMAT_FILE_NAME = new SimpleDateFormat("yyyyMMdd");
    private String logCacheRootPath = "";

    private LinkedBlockingQueue<String> logQueue;
    private CacheLogThread clt;

    private static class SingletonHolder {
        public static LogCache instance = new LogCache();
    }

    private LogCache() {
        logQueue = new LinkedBlockingQueue<>();
        clt = new CacheLogThread();
        clt.start();
    }

    public static LogCache newInstance() {
        return SingletonHolder.instance;
    }

    public void setLogCacheRootPath(String path) {
        this.logCacheRootPath = path;
    }

    public synchronized void putLog(String level, String tag, String log) {
        try {
            String content = buildLog(level, tag, log);
            if (logQueue != null) {
                logQueue.put(content);
            } else {
                logQueue = new LinkedBlockingQueue<>();
                logQueue.put(content);
            }
        } catch (InterruptedException e) {
            Log.e("suyuan", "putLog : e = " + e.toString());
            Writer writer = new StringWriter();
            PrintWriter printWriter = new PrintWriter(writer);
            e.printStackTrace(printWriter);
            printWriter.close();
            LogCache.newInstance().writeMainLogToFile(writer.toString());
        }
        if (clt == null || !clt.isAlive()) {
            clt = new CacheLogThread();
            clt.start();
        }
    }

    class CacheLogThread extends Thread {

        @Override
        public void run() {
            while (true) {
                try {
                    String content = logQueue.take();
                    writeToFile(content);
                } catch (InterruptedException e) {
                    Log.e("suyuan", "CacheLogThread : e = " + e.toString());
                }
            }
        }

    }

    private void writeToFile(String content) {
        if (TextUtils.isEmpty(logCacheRootPath)) {
            logCacheRootPath = "/sdcard/everydaypractise/log";
        }
        String logCacheFilePath = logCacheRootPath + File.separator + "commonLog-" + FORMAT_FILE_NAME.format(new Date()) + ".txt";

        File file = FileUtils.createFile(logCacheFilePath);

        FileOutputStream out = null;
        try {
            out = new FileOutputStream(file, true);
            out.write(content.getBytes());
            out.flush();
        } catch (IOException e) {
            Log.e("suyuan", "writeToFile IOException : e = " + e.toString());
            Writer writer = new StringWriter();
            PrintWriter printWriter = new PrintWriter(writer);
            e.printStackTrace(printWriter);
            printWriter.close();
            LogCache.newInstance().writeMainLogToFile(writer.toString());
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
            } catch (IOException e) {
                Log.e("TAG_AM", "writeToFile IOException : e = " + e.toString());
            }

        }
    }

    private String buildLog(String level, String tag, String content) {
        String s = FORMAT_LOG.format(new Date()) + " " + level + " " + tag + " : " + content + "\n";
        return s;
    }

    public void writeMainLogToFile(String content) {
        if (TextUtils.isEmpty(logCacheRootPath)) {
            logCacheRootPath = "/sdcard/suyuan/log";
        }

        String buildContent = FORMAT_LOG.format(new Date()) + " " + content + "\n";

        String logCacheFilePath = logCacheRootPath + File.separator + "mainLog-" + FORMAT_FILE_NAME.format(new Date()) + ".txt";

        File file = FileUtils.createFile(logCacheFilePath);

        FileOutputStream out = null;
        try {
            out = new FileOutputStream(file, true);
            out.write(buildContent.getBytes());
            out.flush();
        } catch (IOException e) {
            Log.e("suyuan", "writeMainLogToFile IOException : e = " + e.toString());
            Writer writer = new StringWriter();
            PrintWriter printWriter = new PrintWriter(writer);
            e.printStackTrace(printWriter);
            printWriter.close();
            LogCache.newInstance().writeMainLogToFile(writer.toString());
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
            } catch (IOException e) {
                Log.e("suyuan", "writeMainLogToFile IOException : e = " + e.toString());
            }
        }
    }

}
