package com.puntogris.smartwear.data.data_source.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TemperatureResult(

    @SerialName("min")
    val min: Float,

    @SerialName("max")
    val max: Float
)