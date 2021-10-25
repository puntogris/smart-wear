package com.puntogris.whatdoiwear.presentation.weather

import androidx.lifecycle.*
import com.puntogris.whatdoiwear.domain.model.Location
import com.puntogris.whatdoiwear.domain.use_case.GetWeather
import com.puntogris.whatdoiwear.domain.use_case.LocationUseCases
import com.puntogris.whatdoiwear.utils.SharedPref
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import javax.inject.Inject

@ExperimentalCoroutinesApi
@DelicateCoroutinesApi
@HiltViewModel
class WeatherViewModel @Inject constructor(
    sharedPref: SharedPref,
    private val locationUseCases: LocationUseCases,
    private val getWeather: GetWeather
) : ViewModel()
{

    private val query = MutableLiveData<String>()

    val isAnimationEnabled = sharedPref.isAnimationEnabledLiveData()

    val location = locationUseCases.getLocation()

    suspend fun onRefreshLocation() = locationUseCases.updateLastLocation()

    val searchSuggestions = query.switchMap {
        liveData {
            emit(locationUseCases.getGeocodingLocations(it))
        }
    }

    val weather = location.switchMap {
        getWeather(it)
    }

    fun insert(location: Location){
        viewModelScope.launch {
            locationUseCases.insertLocation(location)
        }
    }

    fun setQuery(query: String){
        this.query.value = query
    }

}