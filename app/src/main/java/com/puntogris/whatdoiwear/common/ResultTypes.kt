package com.puntogris.whatdoiwear.utils

import com.puntogris.whatdoiwear.data.data_source.remote.dto.WeatherDto

sealed class WeatherResult{
    class Success(val data: WeatherDto): WeatherResult()
    object Error : WeatherResult()
    object InProgress: WeatherResult()
}


sealed class SimpleResult{
    object Success: SimpleResult()
    object Failure: SimpleResult()
}