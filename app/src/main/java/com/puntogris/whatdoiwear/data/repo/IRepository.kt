package com.puntogris.whatdoiwear.data.repo

import com.puntogris.whatdoiwear.model.LastLocation
import com.puntogris.whatdoiwear.utils.WeatherResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

interface IRepository {
    fun getWeatherApi(location: LastLocation) : MutableStateFlow<WeatherResult>
    fun getLocation(): Flow<LastLocation>
}