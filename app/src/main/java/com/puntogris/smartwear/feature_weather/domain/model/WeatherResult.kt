package com.puntogris.smartwear.feature_weather.domain.model

class WeatherResult(
    val current: Current,
    val daily: List<Daily>,
    val hourly: List<Hourly>
)