package com.puntogris.whatdoiwear.common

import com.puntogris.whatdoiwear.data.data_source.remote.dto.WeatherDto

sealed class WeatherResult{
    class Success(val data: WeatherDto): WeatherResult()
    object Error : WeatherResult()
    object InProgress: WeatherResult()
}


