package com.puntogris.smartwear.domain.repository

import com.puntogris.smartwear.data.data_source.remote.dto.WeatherDto
import com.puntogris.smartwear.domain.model.Location

interface WeatherRepository {
    suspend fun getWeather(location: Location) : WeatherDto
}