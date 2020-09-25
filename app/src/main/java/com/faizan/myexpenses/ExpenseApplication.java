package com.faizan.myexpenses;

import android.app.Application;
import android.content.Context;

import com.facebook.stetho.Stetho;

public class ExpenseApplication extends Application {

    private static Context context;

    public static Context getAppContext() {
        return context;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
        Stetho.initializeWithDefaults(this);
    }
}
