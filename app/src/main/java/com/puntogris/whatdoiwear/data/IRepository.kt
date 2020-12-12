package com.puntogris.whatdoiwear.data

import android.location.Location
import androidx.lifecycle.LiveData
import com.puntogris.whatdoiwear.model.Result
import com.puntogris.whatdoiwear.model.WeatherBodyApi
import kotlinx.coroutines.flow.MutableStateFlow

interface IRepository {
    fun getLocation():LocationLiveData
    fun getLocationName(location: Location):LiveData<String>
    fun getWeatherApi(location: Location) : MutableStateFlow<Result>

}