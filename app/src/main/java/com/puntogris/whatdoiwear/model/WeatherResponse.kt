package com.puntogris.whatdoiwear.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class WeatherResponse(

    @SerialName("current")
    val current: Current,

    @SerialName("daily")
    val daily: List<Daily>,

    @SerialName("hourly")
    val hourly: List<Hourly>

)