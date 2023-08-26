package com.puntogris.smartwear.feature_weather.data.data_source.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class WeatherDetailsResult(

    @SerialName("description")
    val description: String,

    @SerialName("icon")
    val icon: String
)