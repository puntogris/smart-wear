package com.puntogris.whatdoiwear.data

import com.puntogris.whatdoiwear.model.LastLocation
import com.puntogris.whatdoiwear.model.Result
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.flow.MutableStateFlow

interface IRepository {
    fun getLocation():LocationLiveData
    fun getLocationNameAsync(location: LastLocation) : Deferred<String>
    fun getWeatherApi(location: LastLocation) : MutableStateFlow<Result>

}