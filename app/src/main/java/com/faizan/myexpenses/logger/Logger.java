package com.faizan.myexpenses.logger;

import android.util.Log;

public class Logger {

    private Logger(){

    }

    private static final String APPLICATION_TAG = "MFY APPLICATION :: ";

    public static void info(String tag, String msg) {
        Log.i(APPLICATION_TAG +tag, msg);
    }

    public static void info(String msg) {
        Log.i(APPLICATION_TAG , msg);
    }

    public static void debug(String tag, String msg) {
        Log.d(APPLICATION_TAG +tag, msg);
    }
    public static void debug( String msg) {
        Log.d(APPLICATION_TAG , msg);
    }
    public static void error(String tag, String msg) {
        Log.e(APPLICATION_TAG +tag, msg);
    }

    public static void severe( String msg) {
        Log.e(APPLICATION_TAG , msg);
    }
}