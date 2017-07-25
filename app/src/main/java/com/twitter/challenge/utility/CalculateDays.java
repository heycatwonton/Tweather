package com.twitter.challenge.utility;

import java.util.Calendar;

/**
 * Created by catwong on 7/21/17.
 */

public class CalculateDays {

    public static String returnDay(int i) {
        Calendar calendar = Calendar.getInstance();
        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
        int futureDay = resetDay(dayOfWeek, i);
        return getDayOfWeek(futureDay);
    }

    private static int resetDay(int dayOfWeek, int addDay) {
        int daysInWeek = 7;
        int future = dayOfWeek + addDay;

        if (future > daysInWeek) {
            future = future - daysInWeek;
        }
        return future;
    }

    private static String getDayOfWeek(int future) {
        String day = "";

        switch (future) {
            case 1:
                day = "SUN";
                break;
            case 2:
                day = "MON";
                break;
            case 3:
                day = "TUE";
                break;
            case 4:
                day = "WED";
                break;
            case 5:
                day = "THU";
                break;
            case 6:
                day = "FRI";
                break;
            case 7:
                day = "SAT";
                break;
        }
        return day;
    }
}
