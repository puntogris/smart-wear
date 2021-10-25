package com.puntogris.whatdoiwear.domain.repository

import com.puntogris.whatdoiwear.data.data_source.remote.dto.WeatherDto
import com.puntogris.whatdoiwear.domain.model.Location

interface WeatherRepository {
    suspend fun getWeather(location: Location) : WeatherDto
}