package com.puntogris.smartwear.feature_weather.data.data_source.remote.dto

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class WeatherDto(

    @SerializedName("current")
    val current: CurrentResult,

    @SerializedName("daily")
    val daily: List<DailyResult>,

    @SerializedName("hourly")
    val hourly: List<HourlyResult>
)
