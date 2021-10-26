package com.puntogris.whatdoiwear.common

import com.puntogris.whatdoiwear.domain.model.Weather

sealed class WeatherResult{
    class Success(val data: Weather): WeatherResult()
    object Error : WeatherResult()
    object Loading: WeatherResult()
}