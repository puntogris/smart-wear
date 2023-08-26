package com.puntogris.smartwear.feature_weather.domain.model

import com.puntogris.smartwear.feature_weather.domain.model.conditions.Temperature

class Current(
    var temperature: Temperature,
    val description: String,
    val icon: String
)