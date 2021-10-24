package com.puntogris.whatdoiwear.domain.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Weather(

    @SerialName("description")
    val description: String,

    @SerialName("icon")
    val icon: String
)