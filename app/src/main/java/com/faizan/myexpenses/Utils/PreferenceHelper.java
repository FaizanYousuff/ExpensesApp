package com.faizan.myexpenses.Utils;

import android.content.Context;
import android.content.SharedPreferences;

public class PreferenceHelper {

    private static SharedPreferences INSTANCE;
    public static SharedPreferences getInstance(Context context){
        if(INSTANCE == null){
           INSTANCE =  context.getSharedPreferences("MySharedPref", Context.MODE_PRIVATE);
        }
        return INSTANCE;
    }
}
