package com.puntogris.smartwear.data.data_source.remote.dto

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class HourlyResult(

    @SerializedName("temperature_2m")
    val temperatures: List<Float>,

    @SerializedName("relative_humidity_2m")
    val humidity: List<Int>,

    @SerializedName("wind_speed_10m")
    val windSpeeds: List<Float>,

    @SerializedName("precipitation_probability")
    val precipitationProbabilities: List<Int>
)
