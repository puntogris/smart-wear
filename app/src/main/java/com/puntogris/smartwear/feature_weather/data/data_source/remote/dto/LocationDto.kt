package com.puntogris.smartwear.feature_weather.data.data_source.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class LocationDto(

    @SerialName("display_name")
    val displayName: String,

    @SerialName("lat")
    val latitude: Float,

    @SerialName("lon")
    val longitude: Float,

    @SerialName("address")
    val address: AddressResult = AddressResult()
)