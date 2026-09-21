package com.puntogris.smartwear.data.data_source.remote.dto

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class DailyResult(

    @SerializedName("temperature_2m_min")
    val minimumTemperatures: List<Float>,

    @SerializedName("temperature_2m_max")
    val maximumTemperatures: List<Float>
)
