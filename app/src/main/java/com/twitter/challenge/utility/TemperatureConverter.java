package com.twitter.challenge.utility;

public class TemperatureConverter {
    /**
     * Converts temperature in Celsius to temperature in Fahrenheit.
     *
     * @param temperatureInCelsius Temperature in Celsius to convert.
     * @return Temperature in Fahrenheit.
     */

    public static int celsiusToFahrenheit(float temperatureInCelsius) {
        float result = temperatureInCelsius * 1.8f + 32;
        return Math.round(result);
    }


}
