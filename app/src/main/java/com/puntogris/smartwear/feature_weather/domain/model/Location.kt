package com.puntogris.smartwear.feature_weather.domain.model

data class Location(
    val displayName: String = "",
    var name: String = "",
    val latitude: Double = 0.0,
    val longitude: Double = 0.0
)