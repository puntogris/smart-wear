package com.puntogris.whatdoiwear.model

class CurrentWeather(
    val time: Int,
    val summary: String,
    val icon: String,
    val precipProbability: Double,
    val temperature: Double,
    val humidity: Double,
    val windSpeed: Double
)