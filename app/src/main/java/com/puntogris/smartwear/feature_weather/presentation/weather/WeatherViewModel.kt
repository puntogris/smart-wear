package com.puntogris.smartwear.feature_weather.presentation.weather

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.puntogris.smartwear.core.utils.Result
import com.puntogris.smartwear.feature_weather.data.data_source.local.SharedPreferences
import com.puntogris.smartwear.feature_weather.domain.model.Location
import com.puntogris.smartwear.feature_weather.domain.model.Weather
import com.puntogris.smartwear.feature_weather.domain.use_case.GetWeather
import com.puntogris.smartwear.feature_weather.domain.use_case.LocationUseCases
import com.puntogris.smartwear.feature_weather.presentation.util.LocationResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel @Inject constructor(
    private val sharedPreferences: SharedPreferences,
    private val locationUseCases: LocationUseCases,
    private val getWeather: GetWeather
) : ViewModel() {

    val isAnimationEnabled: Boolean
        get() = sharedPreferences.isAnimationEnabled()

    val currentLocation = locationUseCases.getLocation().stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(),
        null
    )

    private val _locationResult = MutableSharedFlow<LocationResult>()
    val locationResult = _locationResult.asSharedFlow()

    private val _weatherResult = MutableStateFlow<Result<Weather>?>(null)
    val weatherResult = _weatherResult.asStateFlow()

    init {
        viewModelScope.launch {
            currentLocation.collect {
                _weatherResult.emitAll(getWeather(it))
            }
        }
    }

    fun requestWeather() {
        viewModelScope.launch {
            _weatherResult.emitAll(getWeather(currentLocation.value))
        }
    }

    fun getLocationSuggestions(query: String) {
        viewModelScope.launch {
            _locationResult.emitAll(locationUseCases.getGeocodingLocations(query))
        }
    }

    fun updateCurrentLocation() {
        viewModelScope.launch {
            _locationResult.emitAll(locationUseCases.updateLocation())
        }
    }

    fun insert(location: Location) {
        viewModelScope.launch {
            locationUseCases.insertLocation(location)
        }
    }
}