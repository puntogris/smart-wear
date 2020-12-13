package com.puntogris.whatdoiwear.ui

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.puntogris.whatdoiwear.data.LocationDao
import com.puntogris.whatdoiwear.data.Repository
import com.puntogris.whatdoiwear.model.LastLocation
import com.puntogris.whatdoiwear.model.Result
import com.puntogris.whatdoiwear.model.WeatherBodyApi
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import java.text.SimpleDateFormat
import java.util.*

class MainFragmentViewModel @ViewModelInject constructor(
    private val room: LocationDao,
    private val repo: Repository
) :ViewModel(){

    val weatherResult = MutableStateFlow<Result>(Result.InProgress)

    private val _lastLocation = MutableLiveData(LastLocation())
    val lastLocation:LiveData<LastLocation> = _lastLocation

    private var _weather = MutableLiveData<WeatherBodyApi>()
    val weather:LiveData<WeatherBodyApi> = _weather

    init {
        viewModelScope.launch {
            val initialLocation = room.getLocation()
            if (!initialLocation.isNullOrEmpty()) {
                _lastLocation.value = initialLocation.first()
                weatherResult.emitAll(repo.getWeatherApi(initialLocation.first()))
            }
        }

        viewModelScope.launch {
            repo.getLocation().asFlow().collect {

                val locationName = repo.getLocationNameAsync(it).await()
                if (lastLocation.value!!.name != locationName) {
                    viewModelScope.launch { weatherResult.emitAll(repo.getWeatherApi(it)) }
                    val newLocation = LastLocation(
                        name = locationName,
                        longitude = it.longitude,
                        latitude = it.latitude
                    )
                    _lastLocation.value = newLocation
                    room.insert(newLocation)
                }
            }
        }
    }

    private val locale = Locale.getDefault()
    private val dateNow = MutableLiveData(Date())

    private val _seekBarPosition = MutableLiveData<Int>()
    val seekBarPosition = _seekBarPosition

    val time:LiveData<String> = dateNow.switchMap{
        MutableLiveData(SimpleDateFormat("h:mm a",locale).format(it))
    }
    val date:LiveData<String> = dateNow.switchMap{
        MutableLiveData(SimpleDateFormat("EEE, MMM d",locale).format(it))
    }

    val timeNow: String = SimpleDateFormat("HH", Locale.getDefault()).format(dateNow.value!!)

    fun updateSeekBarPosition(value:Int) {
        _seekBarPosition.value = value
    }

    fun updateDate() = dateNow.postValue(Date())

    fun getSeekBarLabel(value:Float):String{
        when {
            value > 24 -> value - 24
            value.toInt() == 24 -> 0F
            else -> value
        }.also { return if(it < 12) "${it.toInt()} AM" else "${it.toInt()} PM" }
    }

    fun updateWeather(weather:WeatherBodyApi){
        _weather.value = weather
    }
}