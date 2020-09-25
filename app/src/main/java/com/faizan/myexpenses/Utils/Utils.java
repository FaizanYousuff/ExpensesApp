package com.faizan.myexpenses.Utils;

import java.util.Date;

public class Utils {

    public static boolean isNullOrEmpty(String string) {
        return string == null || string.trim().isEmpty();
    }

    public static String getCurrentMonthString() {
        return (String) android.text.format.DateFormat.format("MMMM", new Date());
    }

    public static int getCurrentMonthNumber() {
        return Integer.parseInt((String) android.text.format.DateFormat.format("M", new Date()));
    }

    public static String getMonthStr(int i) {

        switch (i) {
            case 1:
                return "JANUARY";
            case 2:
                return "FEBRUARY";
            case 3:
                return "MARCH";
            case 4:
                return "APRIL";
            case 5:
                return "MAY";
            case 6:
                return "JUNE";
            case 7:
                return "JULY";
            case 8:
                return "AUGUST";
            case 9:
                return "SEPTEMBER";
            case 10:
                return "OCTOBER";
            case 11:
                return "NOVEMBER";
            case 12:
                return "DECEMBER";
            default:
                return "JANUARY";
        }
    }

    public static String getMaskedString(String str) {
        // return (String)android.text.format.DateFormat.format("MMMM", new Date());
        return "****";

    }
}
