package com.puntogris.whatdoiwear.data.repo

import com.puntogris.whatdoiwear.BuildConfig
import com.puntogris.whatdoiwear.model.WeatherResponse
import io.ktor.client.*
import io.ktor.client.features.*
import io.ktor.client.request.*
import javax.inject.Inject

class WeatherService @Inject constructor(
    private val client: HttpClient
): IWeatherService{

    override suspend fun getWeather(): WeatherResponse {
        return client.get {
            url("https://api.openweathermap.org/data/2.5/onecall?lat=33.44&lon=-94.04&exclude=minutely&appid=${BuildConfig.OPEN_WEATHER_API_KEY}")
        }
    }

}