package com.faizan.myexpenses.Utils;

import android.os.Handler;
import android.os.Looper;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadPoolService {

    private static ThreadPoolService instance;
    private ExecutorService executorService;
    private Handler handler;

    private ThreadPoolService() {
        executorService = Executors.newFixedThreadPool(10);
        handler = new Handler(Looper.getMainLooper());
    }

    public static ThreadPoolService getInstance() {

        if (instance == null) {
            instance = new ThreadPoolService();
        }
        return instance;
    }

    public ExecutorService getExecutorService() {
        return executorService;
    }

    public void runOnUiThread(Runnable runnable) {

        if (handler == null) {
            handler = new Handler(Looper.getMainLooper());
        }
        handler.post(runnable);
    }

    public Handler getHandler() {
        return handler;
    }
}
