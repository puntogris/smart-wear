package com.puntogris.whatdoiwear.domain.repository

import androidx.lifecycle.LiveData
import com.puntogris.whatdoiwear.domain.model.LastLocation
import com.puntogris.whatdoiwear.utils.SimpleResult
import com.puntogris.whatdoiwear.utils.WeatherResult
import kotlinx.coroutines.flow.MutableStateFlow

interface Repository {
    fun getWeatherApi(location: LastLocation) : MutableStateFlow<WeatherResult>
    suspend fun updateLastLocation(): SimpleResult
    fun getLocalLastLocation(): LiveData<LastLocation?>
    suspend fun insertLastLocation(lastLocation: LastLocation)
}