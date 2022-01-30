package com.faizan.myexpenses.logger;

import android.util.Log;

public class Logger {

    private static final String APPLICATION_TAG = "MFY APPLICATION :: ";
     private static final Boolean isShow = false;

    private Logger() {
    }

    public static void info(String tag, String msg) {
        Log.i(APPLICATION_TAG + tag, msg);
    }

    public static void info(String msg) {
        Log.i(APPLICATION_TAG, msg);
    }

    public static void debug(String tag, String msg) {

        if(!isShow){
            return;
        }
        StackTraceElement[]  stackTraceElement = Thread.currentThread().getStackTrace();
        int index =4;
        String className = stackTraceElement[index].getFileName();
        String methodName = stackTraceElement[index].getMethodName();
        int lineNumber = stackTraceElement[index].getLineNumber();


        String stringBuilder = "[(" + className + ":" + lineNumber + ")(" + methodName + ")]" +
                msg;
        Log.d(APPLICATION_TAG + tag, stringBuilder);
    }

    public static void debug(String msg) {
        if(!isShow){
            return;
        }
        StackTraceElement[]  stackTraceElement = Thread.currentThread().getStackTrace();
        int index =4;
        String className = stackTraceElement[index].getFileName();
        String methodName = stackTraceElement[index].getMethodName();
        int lineNumber = stackTraceElement[index].getLineNumber();


        String stringBuilder = "[(" + className + ":" + lineNumber + ")(" + methodName + ")]" +
                msg;
        Log.d(APPLICATION_TAG , stringBuilder);

    }

    public static void error(String tag, String msg) {
        if(!isShow){
            return;
        }
        StackTraceElement[]  stackTraceElement = Thread.currentThread().getStackTrace();
        int index =4;
        String className = stackTraceElement[index].getFileName();
        String methodName = stackTraceElement[index].getMethodName();
        int lineNumber = stackTraceElement[index].getLineNumber();


        String stringBuilder = "[(" + className + ":" + lineNumber + ")(" + methodName + ")]" +
                msg;
        Log.e(APPLICATION_TAG + tag, stringBuilder);
    }

    public static void severe(String msg) {
        Log.e(APPLICATION_TAG, msg);
    }
}