package com.puntogris.whatdoiwear.model

import com.google.gson.GsonBuilder
import okhttp3.Response

class WeatherBodyApi(
    val currently: CurrentWeather,
    val hourly: HourlyWeather
    ){

    companion object{
        fun fromResponse(response: Response): WeatherBodyApi{
            val body = response.body?.string()
            val gson = GsonBuilder().create()
            return gson.fromJson(body, WeatherBodyApi::class.java)
        }
    }
}
