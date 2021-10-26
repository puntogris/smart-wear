package com.puntogris.whatdoiwear.common

import com.puntogris.whatdoiwear.domain.model.Location

sealed class LocationResult{
    object Empty: LocationResult()
    object Loading: LocationResult()
    sealed class Success(): LocationResult(){
        object UpdateLocation: Success()
        class GetLocations(val data: List<Location>): Success()
    }
    class Error(val error: Int): LocationResult()
}