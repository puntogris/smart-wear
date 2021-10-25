package com.puntogris.whatdoiwear.data.data_source.remote

import com.puntogris.whatdoiwear.BuildConfig
import com.puntogris.whatdoiwear.data.data_source.remote.dto.WeatherDto
import com.puntogris.whatdoiwear.common.constants.HttpRoutes
import com.puntogris.whatdoiwear.domain.model.Location
import io.ktor.client.*
import io.ktor.client.request.*
import javax.inject.Inject

class WeatherApi @Inject constructor(
    private val client: HttpClient
) {

    suspend fun getWeather(location: Location): WeatherDto {
        return client.get {
            url(HttpRoutes.WEATHER)
            parameter("appid", BuildConfig.OPEN_WEATHER_API_KEY)
            parameter("lat", location.latitude)
            parameter("lon", location.longitude)
            parameter("units", "metric")
            parameter("exclude", "minutely")
        }
    }
}