package com.puntogris.whatdoiwear.data.data_source.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class WeatherDto(

    @SerialName("current")
    val current: Current,

    @SerialName("daily")
    val daily: List<Daily>,

    @SerialName("hourly")
    val hourly: List<Hourly>

)
