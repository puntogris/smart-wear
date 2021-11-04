package com.puntogris.smartwear.feature_weather.presentation.util

import com.puntogris.smartwear.feature_weather.domain.model.Location

sealed class LocationResult{
    object Loading: LocationResult()
    sealed class Success: LocationResult(){
        object UpdateLocation: Success()
        class GetLocations(val data: List<Location>): Success()
    }
    class Error(val error: Int): LocationResult()
}