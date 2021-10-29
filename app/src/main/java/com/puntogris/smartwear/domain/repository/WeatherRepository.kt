package com.puntogris.smartwear.domain.repository

import com.puntogris.smartwear.domain.model.Location
import com.puntogris.smartwear.domain.model.WeatherResult

interface WeatherRepository {
    suspend fun getWeather(location: Location) : WeatherResult
}