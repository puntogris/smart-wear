package com.puntogris.whatdoiwear.domain.repository

import com.puntogris.whatdoiwear.domain.model.Location
import com.puntogris.whatdoiwear.common.WeatherResult
import com.puntogris.whatdoiwear.data.data_source.remote.dto.WeatherDto
import kotlinx.coroutines.flow.MutableStateFlow

interface WeatherRepository {
    suspend fun getWeather(location: Location) : WeatherDto
}