package com.puntogris.smartwear.data.data_source.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class WeatherDto(

    @SerialName("current")
    val current: CurrentResult,

    @SerialName("daily")
    val daily: List<DailyResult>,

    @SerialName("hourly")
    val hourly: List<HourlyResult>

)
