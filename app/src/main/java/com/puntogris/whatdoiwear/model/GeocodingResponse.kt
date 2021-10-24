package com.puntogris.whatdoiwear.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GeocodingResponse(

    @SerialName("lat")
    val latitude: Float,

    @SerialName("lon")
    val longitude: Float,

    @SerialName("address")
    val address: Address
)