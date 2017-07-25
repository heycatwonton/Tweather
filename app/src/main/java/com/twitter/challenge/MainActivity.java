package com.twitter.challenge;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.twitter.challenge.model.Forecast;
import com.twitter.challenge.network.WeatherService;
import com.twitter.challenge.utility.CalculateDays;
import com.twitter.challenge.utility.StandardDeviationCalculator;
import com.twitter.challenge.utility.TemperatureConverter;
import com.twitter.challenge.utility.WindSpeedConverter;

import java.io.IOException;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();
    private final String WEATHER_BASE_URL = "http://twitter-code-challenge.s3-website-us-east-1.amazonaws.com/";
    int[] tempList = new int[6];
    int[] cloudList = new int[6];
    @BindView(R.id.tv_curr_city)
    TextView currentCity;
    @BindView(R.id.curr_faren_temp)
    TextView currentTempF;
    @BindView(R.id.curr_cels_temp)
    TextView currentTempC;
    @BindView(R.id.curr_wind_speed)
    TextView currentWind;
    @BindView(R.id.curr_clouds)
    ImageView currentConditions;
    @BindView(R.id.bt_getForecast)
    Button getFiveDayForecast;
    @BindView(R.id.day1_temp_c)
    TextView day1C;
    @BindView(R.id.day2_temp_c)
    TextView day2C;
    @BindView(R.id.day3_temp_c)
    TextView day3C;
    @BindView(R.id.day4_temp_c)
    TextView day4C;
    @BindView(R.id.day5_temp_c)
    TextView day5C;
    @BindView(R.id.day1_temp_f)
    TextView day1F;
    @BindView(R.id.day2_temp_f)
    TextView day2F;
    @BindView(R.id.day3_temp_f)
    TextView day3F;
    @BindView(R.id.day4_temp_f)
    TextView day4F;
    @BindView(R.id.day5_temp_f)
    TextView day5F;
    @BindView(R.id.day1_clouds)
    ImageView day1Clouds;
    @BindView(R.id.day2_clouds)
    ImageView day2Clouds;
    @BindView(R.id.day3_clouds)
    ImageView day3Clouds;
    @BindView(R.id.day4_clouds)
    ImageView day4Clouds;
    @BindView(R.id.day5_clouds)
    ImageView day5Clouds;
    @BindView(R.id.day1_day)
    TextView day1Day;
    @BindView(R.id.day2_day)
    TextView day2Day;
    @BindView(R.id.day3_day)
    TextView day3Day;
    @BindView(R.id.day4_day)
    TextView day4Day;
    @BindView(R.id.day5_day)
    TextView day5Day;
    @BindView(R.id.std_dev_cel)
    TextView standardDeviationCelsius;
    @BindView(R.id.std_dev_fahr)
    TextView standardDeviationFahr;
    private WeatherService service;
    private int totalCalls = 5;
    private int callbackCount;
    private int day1TempC;
    private int day1TempF;
    private int day2TempC;
    private int day2TempF;
    private int day3TempC;
    private int day3TempF;
    private int day4TempC;
    private int day4TempF;
    private int day5TempC;
    private int day5TempF;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        callRetrofit(WEATHER_BASE_URL);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    public void callRetrofit(String baseUrl) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .client(new OkHttpClient())
                .build();
        service = retrofit.create(WeatherService.class);
        runWeatherService(service);
    }

    private void runWeatherService(WeatherService service) {
        service.getCurrentWeatherTemps().enqueue(new Callback<Forecast>() {
            @Override
            public void onResponse(Call<Forecast> call, Response<Forecast> response) {
                try {
                    if (response.isSuccessful()) {
                        Forecast forecast = response.body();

                        Log.d(TAG, "Success: " + "Current City: " + forecast.getName());
                        Log.d(TAG, "Success: " + "Current Temp: " + forecast.getWeather().getTemp());
                        Log.d(TAG, "Success: " + "Current Wind Speed: " + forecast.getWind().getSpeed());
                        Log.d(TAG, "Success: " + "Current Cloudiness: " + forecast.getClouds().getCloudiness());

                        getCurrentForecast(forecast);
                    } else {
                        Log.d(TAG, "Error" + response.errorBody().string());
                    }
                } catch (IOException e) {
                    Log.e(TAG, e.getMessage());
                }
            }

            @Override
            public void onFailure(Call<Forecast> call, Throwable t) {
                Log.d(TAG, "CHECK ERROR: " + t);

            }
        });

    }

    private void getCurrentForecast(Forecast forecast) {
        int currentClouds = forecast.getClouds().getCloudiness();
        int convertToC = Math.round(forecast.getWeather().getTemp());
        int convertToF = Math.round(TemperatureConverter.celsiusToFahrenheit(convertToC));
        float wind = forecast.getWind().getSpeed();
        int windInMPH = WindSpeedConverter.metersPerSecondToMPH(wind);
        String tempF = String.valueOf(convertToF + "°F");
        String tempC = String.valueOf(convertToC + "°C");
        String currCity = forecast.getName();
        String windSpeed = String.valueOf(windInMPH);

        setCurrentForecast(currentClouds, currCity, tempF, tempC, windSpeed);
    }

    private void setCurrentForecast(int currentClouds, String currCity, String tempF, String tempC, String windSpeed) {
        currentCity.setText(currCity);
        currentTempF.setText(tempF);
        currentTempC.setText(tempC);
        currentWind.setText(windSpeed);
        if (currentClouds > 50) {
            currentConditions.setImageResource(R.drawable.ic_cloudy);
        } else currentConditions.setImageResource(R.drawable.ic_clear);
    }

    public void getFiveDayForecast(View v) {
        runFiveDayWeatherService(service);
    }

    private void runFiveDayWeatherService(WeatherService service) {
        int day;
        for (int i = 1; i <= totalCalls; i++) {
            day = i;
            final int finalDay = day;
            service.getFiveDayForecast(day).enqueue(new Callback<Forecast>() {
                @Override
                public void onResponse(Call<Forecast> call, Response<Forecast> response) {
                    try {
                        if (response.isSuccessful()) {
                            Forecast forecast = response.body();
                            Log.d(TAG, "Success: " + "5-Day Temps: [" + finalDay + "] " + forecast.getWeather().getTemp());
                            Log.d(TAG, "Success: " + "5-Day Temps: [" + finalDay + "] " + forecast.getClouds().getCloudiness());

                            int doW = Math.round(forecast.getWeather().getTemp());
                            tempList[finalDay] = doW;
                            Log.d(TAG, "Success: " + "ADDED TEMP: [" + finalDay + "] " + doW);

                            int cloud = forecast.getClouds().getCloudiness();
                            cloudList[finalDay] = cloud;
                            Log.d(TAG, "Success: " + "ADDED CLOUDS: [" + finalDay + "] " + cloud);

                            for (int i = 1; i < tempList.length; i++) {
                                Log.d(TAG, "TEMP LIST: " + tempList[i]);
                            }

                            for (int i = 1; i < cloudList.length; i++) {
                                Log.d(TAG, "CLOUD LIST: " + cloudList[i]);
                            }

                            callbackCount += 1;
                            Log.d(TAG, "CALLBACK COUNT : " + callbackCount);


                            if (callbackCount == totalCalls) {
                                setFiveDays();
                                convertFiveDayTemps();
                                setFiveDayTempsViews();
                                setFiveDayCloudiness();
                                setStandardDeviation();
                                callbackCount = 0;
                            }


                        } else {
                            Log.d(TAG, "Error" + response.errorBody().string());
                        }
                    } catch (IOException e) {
                        Log.e(TAG, e.getMessage());
                    }
                }

                @Override
                public void onFailure(Call<Forecast> call, Throwable t) {
                    Log.d(TAG, "CHECK ERROR: " + t);
                }
            });

        }

    }

    private void convertFiveDayTemps() {
        day1TempC = tempList[1];
        day1TempF = TemperatureConverter.celsiusToFahrenheit(day1TempC);
        day2TempC = tempList[2];
        day2TempF = TemperatureConverter.celsiusToFahrenheit(day2TempC);
        day3TempC = tempList[3];
        day3TempF = TemperatureConverter.celsiusToFahrenheit(day3TempC);
        day4TempC = tempList[4];
        day4TempF = TemperatureConverter.celsiusToFahrenheit(day4TempC);
        day5TempC = tempList[5];
        day5TempF = TemperatureConverter.celsiusToFahrenheit(day5TempC);
    }

    private void setFiveDayTempsViews() {
        String celsius = "°C";
        String fahr = "°F";
        setFiveDayTemps(day1C, celsius, day1TempC);
        setFiveDayTemps(day1F, fahr, day1TempF);
        setFiveDayTemps(day2C, celsius, day2TempC);
        setFiveDayTemps(day2F, fahr, day2TempF);
        setFiveDayTemps(day3C, celsius, day3TempC);
        setFiveDayTemps(day3F, fahr, day3TempF);
        setFiveDayTemps(day4C, celsius, day4TempC);
        setFiveDayTemps(day4F, fahr, day4TempF);
        setFiveDayTemps(day5C, celsius, day5TempC);
        setFiveDayTemps(day5F, fahr, day5TempF);
    }

    private void setFiveDayTemps(TextView tv, String s, int temp) {
        String dayTemp = temp + s;
        tv.setText((dayTemp));
    }

    private void setFiveDayCloudiness() {
        setFiveDayCloudinessViews(day1Clouds, 1);
        setFiveDayCloudinessViews(day2Clouds, 2);
        setFiveDayCloudinessViews(day3Clouds, 3);
        setFiveDayCloudinessViews(day4Clouds, 4);
        setFiveDayCloudinessViews(day5Clouds, 5);
    }

    private void setFiveDayCloudinessViews(ImageView image, int i) {
        int cloudiness = 50;
        if (cloudList[i] > cloudiness) {
            image.setImageResource(R.drawable.ic_cloudy);
        } else image.setImageResource(R.drawable.ic_clear);
    }

    private void setFiveDays() {
        String day1 = CalculateDays.returnDay(1);
        String day2 = CalculateDays.returnDay(2);
        String day3 = CalculateDays.returnDay(3);
        String day4 = CalculateDays.returnDay(4);
        String day5 = CalculateDays.returnDay(5);

        day1Day.setText(day1);
        day2Day.setText(day2);
        day3Day.setText(day3);
        day4Day.setText(day4);
        day5Day.setText(day5);
    }

    private void setStandardDeviation() {
        float stdDevInC = StandardDeviationCalculator.findStandardDeviationCelsius(tempList);
        float stdDevInF = StandardDeviationCalculator.stdDeviationCelsiusToFahr(stdDevInC);
        standardDeviationCelsius.setText("Standard Deviation: " + String.format(Locale.getDefault(), "%.2f", stdDevInC) + "°C");
        standardDeviationFahr.setText("Standard Deviation: " + String.format(Locale.getDefault(), "%.2f", stdDevInF) + "°F");

    }
}