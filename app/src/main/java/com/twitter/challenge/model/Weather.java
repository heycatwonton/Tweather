package com.twitter.challenge.model;

/**
 * Created by catwong on 7/18/17.
 */

public class Weather {

    private float temp;
    private int pressure;
    private int humidity;


    public float getTemp() {
        return temp;
    }

    public void setTemp(float temp) {
        this.temp = temp;
    }

    public int getPressure() {
        return pressure;
    }

    public void setPressure(int pressure) {
        this.pressure = pressure;
    }

    public int getHumidity() {
        return humidity;
    }

    public void setHumidity(int humidity) {
        this.humidity = humidity;
    }
}
