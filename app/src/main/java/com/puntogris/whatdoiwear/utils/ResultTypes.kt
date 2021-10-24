package com.puntogris.whatdoiwear.utils

import com.puntogris.whatdoiwear.model.WeatherResponse

sealed class WeatherResult{
    class Success(val data:WeatherResponse): WeatherResult()
    object Error : WeatherResult()
    object InProgress: WeatherResult()
}


sealed class SimpleResult{
    object Success: SimpleResult()
    object Failure: SimpleResult()
}