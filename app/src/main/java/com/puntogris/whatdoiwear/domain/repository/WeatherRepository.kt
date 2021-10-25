package com.puntogris.whatdoiwear.domain.repository

import com.puntogris.whatdoiwear.domain.model.Location
import com.puntogris.whatdoiwear.utils.WeatherResult
import kotlinx.coroutines.flow.MutableStateFlow

interface WeatherRepository {
    fun getWeatherApi(location: Location) : MutableStateFlow<WeatherResult>
}