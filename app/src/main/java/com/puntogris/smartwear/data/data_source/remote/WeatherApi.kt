package com.puntogris.smartwear.data.data_source.remote

import com.puntogris.smartwear.BuildConfig
import com.puntogris.smartwear.common.constants.HttpRoutes
import com.puntogris.smartwear.data.data_source.remote.dto.WeatherDto
import com.puntogris.smartwear.domain.model.Location
import io.ktor.client.*
import io.ktor.client.request.*
import javax.inject.Inject

class WeatherApi @Inject constructor(
    private val client: HttpClient
) {

    suspend fun getWeather(location: Location, units: String, language: String): WeatherDto {
        return client.get {
            url(HttpRoutes.WEATHER)
            parameter("appid", BuildConfig.OPEN_WEATHER_API_KEY)
            parameter("lat", location.latitude)
            parameter("lon", location.longitude)
            parameter("units", units)
            parameter("exclude", "minutely")
            parameter("lang", language)
        }
    }

}