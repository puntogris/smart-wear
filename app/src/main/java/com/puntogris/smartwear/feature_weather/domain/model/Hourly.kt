package com.puntogris.smartwear.feature_weather.domain.model

import com.puntogris.smartwear.feature_weather.domain.model.conditions.Humidity
import com.puntogris.smartwear.feature_weather.domain.model.conditions.Precipitation
import com.puntogris.smartwear.feature_weather.domain.model.conditions.Temperature
import com.puntogris.smartwear.feature_weather.domain.model.conditions.Wind

class Hourly(
    val temperature: Temperature,
    val humidity: Humidity,
    val windSpeed: Wind,
    val precipitation: Precipitation
)