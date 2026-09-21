package com.puntogris.smartwear.data.data_source.remote.dto

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class CurrentResult(

    @SerializedName("temperature_2m")
    val temperature: Float,

    @SerializedName("weather_code")
    val weatherCode: Int,

    @SerializedName("is_day")
    val isDay: Int
)
