package com.puntogris.whatdoiwear.domain.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Current(
    
    @SerialName("temp")
    val temperature: Float,

    @SerialName("weather")
    val weather: List<Weather>

)