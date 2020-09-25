package com.faizan.myexpenses.Utils;

import android.app.Activity;

public class ContextProvider {

    private static ContextProvider instance;

    private Activity activity;

    public static ContextProvider getInstance() {

        if (instance == null) {
            instance = new ContextProvider();
        }
        return instance;
    }

    public Activity getActivity() {
        return activity;
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }
}
