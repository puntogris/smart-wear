package com.puntogris.smartwear.feature_weather.data.data_source.remote.dto

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class WeatherDetailsResult(

    @SerializedName("description")
    val description: String,

    @SerializedName("icon")
    val icon: String
)