package com.puntogris.smartwear.domain.repository

import com.puntogris.smartwear.domain.model.Location
import com.puntogris.smartwear.domain.model.Weather

interface WeatherRepository {
    suspend fun getWeather(location: Location) : Weather
}