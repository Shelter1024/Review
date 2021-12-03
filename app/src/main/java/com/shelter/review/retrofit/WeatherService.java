package com.shelter.review.retrofit;

import okhttp3.Call;

/**
 * @author: Shelter
 * Create time: 2021/11/18, 10:19.
 */
public interface WeatherService {
    @GET("/v3/weather/weatherInfo")
    Call getWeather(@Query("city")String city, @Query("key")String key);

    @POST("/v3/weather/weatherInfo")
    Call postWeather(@Field("city") String city, @Field("key")String key);
}
