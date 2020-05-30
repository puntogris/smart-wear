package com.puntogris.whatdoiwear.data

import android.location.Location
import androidx.lifecycle.LiveData
import com.puntogris.whatdoiwear.model.WeatherBodyApi

interface IRepository {
    fun getWeatherApi(location: Location) : LiveData<WeatherBodyApi>
    fun getLocation():LocationLiveData
    fun getLocationName(location: Location):LiveData<String>
}