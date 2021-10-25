package com.puntogris.whatdoiwear.data.data_source.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Address(

    @SerialName("country")
    val country: String,

    @SerialName("state")
    val state: String,

    @SerialName("city")
    val city: String
)