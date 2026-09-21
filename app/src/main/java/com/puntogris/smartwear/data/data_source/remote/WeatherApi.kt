package com.puntogris.smartwear.data.data_source.remote

import com.puntogris.smartwear.data.data_source.remote.dto.WeatherDto
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApi {

    @GET("v1/forecast")
    suspend fun getWeather(
        @Query("latitude") lat: Double,
        @Query("longitude") lon: Double,
        @Query("current") current: String = "temperature_2m,weather_code,is_day",
        @Query("hourly") hourly: String = "temperature_2m,relative_humidity_2m,precipitation_probability,wind_speed_10m",
        @Query("daily") daily: String = "temperature_2m_min,temperature_2m_max",
        @Query("forecast_hours") forecastHours: Int = 8,
        @Query("timezone") timezone: String = "auto",
        @Query("temperature_unit") temperatureUnit: String,
        @Query("wind_speed_unit") windSpeedUnit: String
    ): WeatherDto
}
