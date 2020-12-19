package com.puntogris.whatdoiwear.ui

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.puntogris.whatdoiwear.data.LocationDao
import com.puntogris.whatdoiwear.data.Repository
import com.puntogris.whatdoiwear.model.LastLocation
import com.puntogris.whatdoiwear.model.WeatherBodyApi
import com.puntogris.whatdoiwear.utils.WeatherResult
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class MainFragmentViewModel @ViewModelInject constructor(
    private val room: LocationDao,
    private val repo: Repository
) : ViewModel(){

    val weatherResult = MutableStateFlow<WeatherResult>(WeatherResult.InProgress)

    private val locale = Locale.getDefault()
    private val dateNow = MutableLiveData(Date())

    private val _seekBarPosition = MutableLiveData<Int>()
    val seekBarPosition = _seekBarPosition

    private val _lastLocation = MutableLiveData(LastLocation())
    val lastLocation:LiveData<LastLocation> = _lastLocation

    private var _weather = MutableLiveData<WeatherBodyApi>()
    val weather: LiveData<WeatherBodyApi> = _weather

    val time:LiveData<String> = dateNow.switchMap{
        MutableLiveData(SimpleDateFormat("h:mm a",locale).format(it))
    }
    val date:LiveData<String> = dateNow.switchMap{
        MutableLiveData(SimpleDateFormat("EEE, MMM d",locale).format(it))
    }

    val timeNow: String = SimpleDateFormat("HH", Locale.getDefault()).format(dateNow.value!!)

    init {
        viewModelScope.launch {
            room.getLocation()?.also { location ->
                _lastLocation.value = location
                weatherResult.emitAll(repo.getWeatherApi(location))
            }
        }

        viewModelScope.launch {
            repo.getLocation().asFlow()
                .collect { location ->
                    getLocationName(location).also {
                        if (it != lastLocation.value?.name){
                            viewModelScope.launch { weatherResult.emitAll(repo.getWeatherApi(location)) }
                            location.name = it
                            _lastLocation.value = location
                            room.insert(location)
                    }
                }
            }
        }
    }


    private suspend fun getLocationName(location: LastLocation) = repo.getLocationNameAsync(location).await()

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

    fun isOnEndSeekBar(value: Float): Boolean = timeNow.toFloat() + 24 == value

    fun updateWeather(weather:WeatherBodyApi){
        _weather.value = weather
    }
}