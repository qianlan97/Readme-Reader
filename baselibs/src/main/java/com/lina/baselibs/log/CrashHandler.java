package com.lina.baselibs.log;

import android.util.Log;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;

public class CrashHandler implements Thread.UncaughtExceptionHandler {
    private static final String TAG = "CrashHandler";
    private Thread.UncaughtExceptionHandler mDefaultHandler;
    private static CrashHandler instance;

    private CrashHandler() {
        init();
    }

    public static synchronized CrashHandler getInstance() {
        if (instance == null) {
            instance = new CrashHandler();
        }
        return instance;
    }

    private void init() {
        Log.d(TAG, "CrashHandler init");
        //获取系统默认的UncaughtException处理器
        mDefaultHandler = Thread.getDefaultUncaughtExceptionHandler();
        //设置该CrashHandler为程序的默认处理器
        Thread.setDefaultUncaughtExceptionHandler(this);
    }

    @Override
    public void uncaughtException(Thread thread, Throwable ex) {
        if (null == ex) {
            return;
        }

        try {
            handleException(ex);
        } catch (Exception e) {
            Log.e(TAG, "uncaughtException Exception : e = " + e.toString());
        } finally {
            if (mDefaultHandler != null) {
                mDefaultHandler.uncaughtException(thread, ex);
            } else {
                System.exit(0);
            }
        }

    }

    private boolean handleException(Throwable ex) {
        Log.d(TAG, "CrashHandler handleException");
        if (ex == null) {
            return false;
        }
        Writer writer = new StringWriter();
        PrintWriter printWriter = new PrintWriter(writer);
        ex.printStackTrace(printWriter);
        Throwable cause = ex.getCause();
        while (cause != null) {
            cause.printStackTrace(printWriter);
            cause = cause.getCause();
        }
        printWriter.close();
        LogCache.newInstance().writeMainLogToFile("crash -----> start");
        LogCache.newInstance().writeMainLogToFile(writer.toString());
        LogCache.newInstance().writeMainLogToFile("crash -----> end");
        return true;
    }
}
