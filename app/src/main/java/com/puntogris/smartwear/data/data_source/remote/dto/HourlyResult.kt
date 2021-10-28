package com.puntogris.smartwear.data.data_source.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class HourlyResult(

    @SerialName("temp")
    val temp: Float,

    @SerialName("humidity")
    val humidity: Int,

    @SerialName("wind_speed")
    val windSpeed: Float,

    @SerialName("pop")
    val precipitation: Float
)