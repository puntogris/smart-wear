package com.puntogris.whatdoiwear.model

class HourlyData(
    val time: Int,
    val summary: String,
    val icon: String,
    val temperature: Double,
    val humidity: Double,
    val windSpeed: Double,
    val precipProbability: Double
)
