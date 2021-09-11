package com.puntogris.whatdoiwear.ui.main

import androidx.lifecycle.*
import com.puntogris.whatdoiwear.data.repo.Repository
import com.puntogris.whatdoiwear.model.LastLocation
import com.puntogris.whatdoiwear.model.WeatherBodyApi
import com.puntogris.whatdoiwear.utils.SharedPref
import com.puntogris.whatdoiwear.utils.WeatherResult
import com.puntogris.whatdoiwear.utils.update
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

@DelicateCoroutinesApi
@ExperimentalCoroutinesApi
@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: Repository,
    private val sharedPref: SharedPref
) : ViewModel(){

    val username = sharedPref.getUsernamePref()

    private val _isAnimationEnabled = MutableLiveData(sharedPref.getShowAnimationPref())
    val isAnimationEnabled: LiveData<Boolean> = _isAnimationEnabled

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
            repository.getRoomLastLocation()?.also { location ->
                _lastLocation.value = location
                weatherResult.emitAll(repository.getWeatherApi(location))
            }
        }

        viewModelScope.launch {
            repository.getLocation()
                .collect { location ->
                    if (location.name != lastLocation.value?.name){
                        viewModelScope.launch { weatherResult.emitAll(repository.getWeatherApi(location)) }
                        saveLastLocation(location)
                    }
                }
            }
    }

    private suspend fun saveLastLocation(location: LastLocation){
        _lastLocation.value = location
        repository.insertLastLocation(location)
    }

    fun updateSeekBarPosition(value:Int) {
        _seekBarPosition.value = value
    }

    fun updateDate() {
        dateNow.update()
    }

    fun getSeekBarLabel(value:Float):String{
        when {
            value > 24 -> value - 24
            value.toInt() == 24 -> 0F
            else -> value
        }.also { return if(it < 12) "${it.toInt()} AM" else "${it.toInt()} PM" }
    }

    fun isOnEndSeekBar(value: Float) =
        (timeNow.toFloat() + 24 == value)&& !sharedPref.getShowAnimationPref()

    fun updateWeather(weather:WeatherBodyApi){
        _weather.value = weather
    }

    fun enableAnimationPref(){
        sharedPref.enableShowAnimationPref()
        _isAnimationEnabled.value = !_isAnimationEnabled.value!!
    }
}