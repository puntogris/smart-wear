package com.puntogris.whatdoiwear.data.data_source.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class LocationDto(

    @SerialName("lat")
    val latitude: Float,

    @SerialName("lon")
    val longitude: Float,

    @SerialName("address")
    val address: Address
)
