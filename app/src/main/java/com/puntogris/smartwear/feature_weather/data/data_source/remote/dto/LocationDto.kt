package com.puntogris.smartwear.feature_weather.data.data_source.remote.dto

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class LocationDto(

    @SerializedName("display_name")
    val displayName: String = "",

    @SerializedName("lat")
    val latitude: Float = 0F,

    @SerializedName("lon")
    val longitude: Float = 0F,

    @SerializedName("address")
    val address: AddressResult = AddressResult()
)