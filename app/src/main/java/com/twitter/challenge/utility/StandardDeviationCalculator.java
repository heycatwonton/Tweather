package com.twitter.challenge.utility;

/**
 * Created by catwong on 7/21/17.
 */

public class StandardDeviationCalculator {

    public static float findStandardDeviationCelsius(int[] tempList) {

        float[] tempArr = new float[tempList.length];
        int numData = tempList.length - 1;
        int numDataSample = tempList.length - 2;
        float sum = 0;
        float sumOfDeviation = 0;
        float mean;
        float difference;
        float variance;
        float result;

        for (int i = 1; i < tempList.length; i++) {
            sum += tempList[i];
        }
        mean = sum / numData;

        for (int i = 1; i < tempList.length; i++) {
            difference = (float) Math.pow((tempList[i] - mean), 2);
            tempArr[i] = difference;
            sumOfDeviation += tempArr[i];
        }
        variance = sumOfDeviation / numDataSample;
        result = (float) Math.sqrt(variance);
        return result;
    }

    public static float stdDeviationCelsiusToFahr(float stdDevInCelsius) {
        return stdDevInCelsius * 1.8f;
    }


}
