package com.puntogris.whatdoiwear.data.data_source.remote

import com.puntogris.whatdoiwear.BuildConfig
import com.puntogris.whatdoiwear.domain.model.LastLocation
import com.puntogris.whatdoiwear.domain.model.WeatherResponse
import com.puntogris.whatdoiwear.utils.constants.HttpRoutes
import io.ktor.client.*
import io.ktor.client.request.*
import javax.inject.Inject

class WeatherApi @Inject constructor(
    private val client: HttpClient
) {

    suspend fun getWeather(lastLocation: LastLocation): WeatherResponse {
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