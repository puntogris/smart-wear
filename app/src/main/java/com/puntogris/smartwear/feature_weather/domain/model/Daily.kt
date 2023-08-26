package com.puntogris.smartwear.feature_weather.domain.model

import com.puntogris.smartwear.feature_weather.domain.model.conditions.Temperature

class Daily(
    val min: Temperature,
    val max: Temperature
)