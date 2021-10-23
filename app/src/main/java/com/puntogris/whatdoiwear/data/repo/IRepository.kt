package com.puntogris.whatdoiwear.data.repo

import androidx.lifecycle.LiveData
import com.puntogris.whatdoiwear.model.LastLocation
import com.puntogris.whatdoiwear.utils.SimpleResult
import com.puntogris.whatdoiwear.utils.WeatherResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

interface IRepository {
    fun getWeatherApi(location: LastLocation) : MutableStateFlow<WeatherResult>
    suspend fun updateLastLocation(): SimpleResult
    fun getLocalLastLocation(): LiveData<LastLocation?>
    suspend fun insertLastLocation(lastLocation: LastLocation)
}