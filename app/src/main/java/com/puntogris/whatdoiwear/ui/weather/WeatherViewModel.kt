package com.puntogris.whatdoiwear.ui.weather

import androidx.lifecycle.*
import com.puntogris.whatdoiwear.data.repo.Repository
import com.puntogris.whatdoiwear.utils.SharedPref
import com.puntogris.whatdoiwear.utils.WeatherResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import javax.inject.Inject

@DelicateCoroutinesApi
@HiltViewModel
class WeatherViewModel @Inject constructor(
    private val repository: Repository,
    sharedPref: SharedPref
) : ViewModel(){

    val isAnimationEnabled = sharedPref.isAnimationEnabledLiveData()

    val lastLocation = repository.getLocalLastLocation()

    fun refreshLocation(){
        viewModelScope.launch {
            repository.updateLastLocation()
        }
    }

    val weatherResult = lastLocation.switchMap {
        if (it == null) MutableLiveData(WeatherResult.Error)
        else repository.getWeatherApi(it).asLiveData()
    }

    init {
        viewModelScope.launch {
            repository.getGeo()
        }
    }

}