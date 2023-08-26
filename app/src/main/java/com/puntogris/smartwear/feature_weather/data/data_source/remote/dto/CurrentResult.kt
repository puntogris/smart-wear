package com.puntogris.smartwear.feature_weather.data.data_source.remote.dto

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class CurrentResult(

    @SerializedName("temp")
    val temperature: Float,

    @SerializedName("weather")
    val weather: List<WeatherDetailsResult>
)