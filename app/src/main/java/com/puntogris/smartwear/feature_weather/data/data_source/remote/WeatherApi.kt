package com.puntogris.smartwear.feature_weather.data.data_source.remote

import com.puntogris.smartwear.feature_weather.data.data_source.remote.dto.WeatherDto
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApi {

    @GET("/data/2.5/onecall")
    suspend fun getWeather(
        @Query("lat") lat: Double,
        @Query("lon") lon: Double,
        @Query("units") units: String,
        @Query("lang") lang: String,
        @Query("exclude") exclude: String = "minutely"
    ): WeatherDto
}