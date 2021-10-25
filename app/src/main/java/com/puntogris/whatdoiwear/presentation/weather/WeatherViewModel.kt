package com.puntogris.whatdoiwear.presentation.weather

import androidx.lifecycle.*
import com.puntogris.whatdoiwear.domain.use_case.GetWeather
import com.puntogris.whatdoiwear.domain.use_case.LocationUseCases
import com.puntogris.whatdoiwear.utils.SharedPref
import com.puntogris.whatdoiwear.utils.WeatherResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.launch
import javax.inject.Inject

@DelicateCoroutinesApi
@HiltViewModel
class WeatherViewModel @Inject constructor(
    sharedPref: SharedPref,
    private val locationUseCases: LocationUseCases,
    private val getWeather: GetWeather
) : ViewModel()
{

    private val _query = MutableLiveData<String>()
    val query: LiveData<String> get() = _query

    val isAnimationEnabled = sharedPref.isAnimationEnabledLiveData()

    val location = locationUseCases.getLocation()

    fun onRefreshEvent(){
        viewModelScope.launch {
            locationUseCases.updateLastLocation()
        }
    }

    private val geocodingLocations = _query.switchMap {
        liveData {
            emit(locationUseCases.getGeocodingLocations(it))
        }
    }

    val weather = location.switchMap {
        getWeather(it)
    }

}