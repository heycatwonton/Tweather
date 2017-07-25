package com.twitter.challenge.utility;

/**
 * Created by catwong on 7/21/17.
 */

public class WindSpeedConverter {

    public static int metersPerSecondToMPH(float speedInMetersPerSecond) {
        return Math.round(speedInMetersPerSecond * 2.23694f) ;
    }
}
