package com.puntogris.whatdoiwear.utils

import com.puntogris.whatdoiwear.model.WeatherBodyApi

sealed class WeatherResult{
    class Success(val data:WeatherBodyApi): WeatherResult()
    object Error : WeatherResult()
    object InProgress: WeatherResult()
}
