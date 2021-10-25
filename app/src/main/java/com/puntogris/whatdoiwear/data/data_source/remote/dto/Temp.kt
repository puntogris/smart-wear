package com.puntogris.whatdoiwear.data.data_source.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Temp(

    @SerialName("min")
    val min: Float,

    @SerialName("max")
    val max: Float
)