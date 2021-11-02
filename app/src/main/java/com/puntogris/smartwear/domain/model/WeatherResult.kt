package com.puntogris.smartwear.domain.model

class WeatherResult(
    val current: Current,
    val daily: List<Daily>,
    val hourly: List<Hourly>,
    val units :String
)