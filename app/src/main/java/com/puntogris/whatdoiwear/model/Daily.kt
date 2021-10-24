package com.puntogris.whatdoiwear.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Daily(

    @SerialName("temp")
    val temp: Temp
)
