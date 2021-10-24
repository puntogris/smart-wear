package com.puntogris.whatdoiwear.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Weather(

    @SerialName("description")
    val description: String,

    @SerialName("icon")
    val icon: String
)