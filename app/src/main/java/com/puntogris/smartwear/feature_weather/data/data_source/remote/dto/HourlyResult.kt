package com.puntogris.smartwear.feature_weather.data.data_source.remote.dto

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class HourlyResult(

    @SerializedName("temp")
    val temp: Float,

    @SerializedName("humidity")
    val humidity: Int,

    @SerializedName("wind_speed")
    val windSpeed: Float,

    @SerializedName("pop")
    val precipitation: Float
)