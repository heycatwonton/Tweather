package com.twitter.challenge.network;

import com.twitter.challenge.model.Forecast;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by catwong on 7/18/17.
 */

public interface WeatherService {

    // http://twitter-code-challenge.s3-website-us-east-1.amazonaws.com/current.json
    // http://twitter-code-challenge.s3-website-us-east-1.amazonaws.com/future_1.json

    @GET("current.json")
    Call<Forecast> getCurrentWeatherTemps();

    @GET("future_{day}.json")
    Call<Forecast> getFiveDayForecast(@Path("day") int day);
}
