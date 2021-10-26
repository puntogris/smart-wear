package com.puntogris.whatdoiwear.domain.model

class Weather(
    val current: Current,
    val daily: List<Daily>,
    val hourly: List<Hourly>
)