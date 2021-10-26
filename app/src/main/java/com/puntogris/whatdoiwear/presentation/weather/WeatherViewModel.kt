package com.puntogris.whatdoiwear.presentation.weather

import androidx.lifecycle.*
import com.puntogris.whatdoiwear.common.LocationResult
import com.puntogris.whatdoiwear.domain.model.Location
import com.puntogris.whatdoiwear.domain.use_case.GetWeather
import com.puntogris.whatdoiwear.domain.use_case.LocationUseCases
import com.puntogris.whatdoiwear.utils.SharedPref
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel @Inject constructor(
    private val sharedPref: SharedPref,
    private val locationUseCases: LocationUseCases,
    private val getWeather: GetWeather
) : ViewModel()
{
    val isAnimationEnabled: Boolean
        get () = sharedPref.isAnimationEnabled()

    val currentLocation = locationUseCases.getLocation()

    val weather = currentLocation.switchMap {
        getWeather(it)
    }

    private val _locationResult = MutableStateFlow<LocationResult>(LocationResult.Empty)
    val locationResult: StateFlow<LocationResult> = _locationResult

    fun getLocationSuggestions(query: String){
        viewModelScope.launch {
            _locationResult.emitAll(locationUseCases.getGeocodingLocations(query))
        }
    }

    fun getCurrentLocation(){
        viewModelScope.launch {
            _locationResult.emitAll(locationUseCases.updateLocation())
        }
    }

    fun insert(location: Location){
        viewModelScope.launch {
            locationUseCases.insertLocation(location)
        }
    }
}