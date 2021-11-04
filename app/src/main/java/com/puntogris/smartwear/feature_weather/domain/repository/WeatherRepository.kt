package com.puntogris.smartwear.feature_weather.domain.repository

import com.puntogris.smartwear.feature_weather.domain.model.Location
import com.puntogris.smartwear.feature_weather.domain.model.WeatherResult

interface WeatherRepository {
    suspend fun getWeather(location: Location) : WeatherResult
}