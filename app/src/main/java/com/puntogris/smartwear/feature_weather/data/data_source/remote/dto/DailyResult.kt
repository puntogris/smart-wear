package com.puntogris.smartwear.feature_weather.data.data_source.remote.dto

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class DailyResult(

    @SerializedName("temp")
    val temperature: TemperatureResult
)
