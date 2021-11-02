package com.puntogris.smartwear.domain.model

import com.puntogris.smartwear.data.data_source.Humidity
import com.puntogris.smartwear.data.data_source.Rain
import com.puntogris.smartwear.data.data_source.Temperature
import com.puntogris.smartwear.data.data_source.Wind

class Hourly(
    val temperature: Temperature,
    val humidity: Humidity,
    val windSpeed: Wind,
    val precipitation: Rain
)