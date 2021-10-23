package com.puntogris.whatdoiwear.ui.weather

import androidx.lifecycle.*
import com.puntogris.whatdoiwear.data.repo.Repository
import com.puntogris.whatdoiwear.utils.SharedPref
import com.puntogris.whatdoiwear.utils.WeatherResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Inject

@DelicateCoroutinesApi
@HiltViewModel
class WeatherViewModel @Inject constructor(
    private val repository: Repository,
    private val sharedPref: SharedPref
) : ViewModel(){

    private val _isAnimationEnabled = MutableLiveData(sharedPref.getShowAnimationPref())
    val isAnimationEnabled: LiveData<Boolean> = _isAnimationEnabled

    val lastLocation = repository.getLocalLastLocation()

    suspend fun getCurretLocation(){
        repository.updateLastLocation()
    }

    val weatherResult = lastLocation.switchMap {
        if (it == null) MutableLiveData(WeatherResult.Error)
        else repository.getWeatherApi(it).asLiveData()
    }


    fun enableAnimationPref(){
        sharedPref.enableShowAnimationPref()
        _isAnimationEnabled.value = !_isAnimationEnabled.value!!
    }
}