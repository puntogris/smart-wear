package com.puntogris.whatdoiwear.data

import androidx.lifecycle.LiveData
import com.puntogris.whatdoiwear.model.LastLocation
import com.puntogris.whatdoiwear.utils.WeatherResult
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

interface IRepository {
    fun getLocationNameAsync(location: LastLocation) : Deferred<String>
    fun getWeatherApi(location: LastLocation) : MutableStateFlow<WeatherResult>
    fun getLocation(): LiveData<LastLocation>
}