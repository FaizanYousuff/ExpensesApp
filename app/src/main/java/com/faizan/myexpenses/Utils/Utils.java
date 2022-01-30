package com.faizan.myexpenses.Utils;

import android.text.TextUtils;

import java.util.Date;

public class Utils {

    public static boolean isNullOrEmpty(String string) {
        return string == null || string.trim().isEmpty();
    }

    public static String getCurrentMonthString() {
        return (String) android.text.format.DateFormat.format("MMMM yyy", new Date());
    }

    public static int getCurrentMonthNumber() {
        return Integer.parseInt((String) android.text.format.DateFormat.format("MMyy", new Date()));
    }

    public static String getMonthStr(int i) {
         String monthYearString = String.valueOf(i);
        int monthDigit = 1;
        int YearDigit = 22;
         if(!TextUtils.isEmpty(monthYearString) && monthYearString.length()>2){
            monthDigit = Integer.parseInt(monthYearString.substring(0,monthYearString.length()-2));
            YearDigit = Integer.parseInt(monthYearString.substring(monthYearString.length()-2));
        }
         String value = "";
        switch (monthDigit) {
            case 1:
                value= "January";
                break;
            case 2:
                value="February";
                break;
            case 3:
                value= "March";
                break;
            case 4:
                value= "April";
                break;
            case 5:
                value= "May";
                break;
            case 6:
                value= "June";
                break;
            case 7:
                value= "July";
                break;
            case 8:
                value= "August";
                break;
            case 9:
                value= "September";
                break;
            case 10:
                value= "October";
                break;
            case 11:
                value= "November";
                break;
            case 12:
                value= "December";
                break;
            default:
                value= "JANUARY";
        }
      return   value+ " "+YearDigit;
    }

    public static String getMaskedString(String str) {
        // return (String)android.text.format.DateFormat.format("MMMM", new Date());
        return "****";

    }
}
