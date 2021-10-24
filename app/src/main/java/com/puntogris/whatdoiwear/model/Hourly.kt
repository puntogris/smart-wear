package com.puntogris.whatdoiwear.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Hourly(

    @SerialName("temp")
    val temp: Float,

    @SerialName("humidity")
    val humidity: Int,

    @SerialName("wind_speed")
    val windSpeed: Float
)