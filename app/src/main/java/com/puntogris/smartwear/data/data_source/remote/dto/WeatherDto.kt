package com.puntogris.smartwear.data.data_source.remote.dto

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class WeatherDto(
    @SerializedName("current")
    val current: CurrentResult,

    @SerializedName("daily")
    val daily: DailyResult,

    @SerializedName("hourly")
    val hourly: HourlyResult
)
