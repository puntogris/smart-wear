package com.puntogris.whatdoiwear.ui

import androidx.lifecycle.*
import com.puntogris.whatdoiwear.data.Repository
import com.puntogris.whatdoiwear.model.Result
import com.puntogris.whatdoiwear.model.WeatherBodyApi
import com.puntogris.whatdoiwear.utils.MySharedPreferences
import kotlinx.coroutines.flow.*
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

class MainFragmentViewModel @Inject constructor(
    private val repo: Repository,
    sharedPref: MySharedPreferences
) :ViewModel(){

    private val locale = Locale.getDefault()
    private val dateNow = MutableLiveData(Date())
    private val _seekBarPosition = MutableLiveData<Int>()
    private var location = repo.getLocation()
    val seekBarPosition = _seekBarPosition

    private var _weather = MutableLiveData<WeatherBodyApi>()
    val weather:LiveData<WeatherBodyApi> = _weather


    val time:LiveData<String> = dateNow.switchMap{
        MutableLiveData(SimpleDateFormat("h:mm a",locale).format(it))
    }
    val date:LiveData<String> = dateNow.switchMap{
        MutableLiveData(SimpleDateFormat("EEE, MMM d",locale).format(it))
    }

    val timeNow: String = SimpleDateFormat("HH", Locale.getDefault()).format(dateNow.value!!)

    val name = sharedPref.getData()

    val weatherBody = location.switchMap { location ->
        liveData { emitSource(repo.getWeatherApi(location).asLiveData())}
    }

    val locationName = location.switchMap { location ->
        liveData { emitSource(repo.getLocationName(location)) }
    }

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