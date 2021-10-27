package com.puntogris.smartwear.common

import com.puntogris.smartwear.domain.model.Weather

sealed class WeatherResult{
    class Success(val data: Weather): WeatherResult()
    object Error : WeatherResult()
    object Loading: WeatherResult()
}