package com.puntogris.whatdoiwear.data.repo

import com.puntogris.whatdoiwear.BuildConfig
import com.puntogris.whatdoiwear.model.LastLocation
import com.puntogris.whatdoiwear.model.WeatherResponse
import com.puntogris.whatdoiwear.utils.HttpRoutes
import io.ktor.client.*
import io.ktor.client.request.*
import javax.inject.Inject

class WeatherService @Inject constructor(
    private val client: HttpClient
): IWeatherService{

    override suspend fun getWeather(lastLocation: LastLocation): WeatherResponse {
        return client.get {
            url(HttpRoutes.WEATHER)
            parameter("appid", BuildConfig.OPEN_WEATHER_API_KEY)
            parameter("lat", lastLocation.latitude)
            parameter("lon", lastLocation.longitude)
            parameter("units", "metric")
            parameter("exclude", "minutely")
        }
    }

}