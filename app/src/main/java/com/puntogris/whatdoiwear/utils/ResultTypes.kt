package com.puntogris.whatdoiwear.utils

import com.puntogris.whatdoiwear.model.LastLocation
import com.puntogris.whatdoiwear.model.WeatherBodyApi
import java.lang.Exception

sealed class WeatherResult{
    class Success(val data:WeatherBodyApi): WeatherResult()
    class Error(val exception: Exception): WeatherResult()
    object InProgress: WeatherResult()
}
sealed class LocationResult{
    class Success(val data: LastLocation): LocationResult()
    class Error(val exception: Exception): LocationResult()
}