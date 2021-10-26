package com.puntogris.whatdoiwear.common

import com.puntogris.whatdoiwear.domain.model.Location

sealed class Result<out T : Any> {
    object Loading: Result<Nothing>()
    data class Success<out T : Any>(val data: T) : Result<T>()
    data class Error(val exception: Exception) : Result<Nothing>()
}

sealed class LocationResult{
    object Empty: LocationResult()
    object Loading: LocationResult()
    sealed class Success(): LocationResult(){
        object UpdateLocation: Success()
        class GetLocations(val data: List<Location>): Success()
    }
    class Error(val error: Int): LocationResult()
}