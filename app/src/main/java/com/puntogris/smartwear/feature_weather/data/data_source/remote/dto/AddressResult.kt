package com.puntogris.smartwear.feature_weather.data.data_source.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AddressResult(

    @SerialName("country")
    val country: String = "",

    @SerialName("state")
    val state: String = "",

    @SerialName("city")
    val city: String = "",

    @SerialName("region")
    val region: String = "",

    @SerialName("municipality")
    val municipality: String = "",

    @SerialName("state_district")
    val stateDistrict: String = "",

    @SerialName("county")
    val county: String = "",

    @SerialName("town")
    val town: String = ""
) {
    val name = getAddressName()

    private fun getAddressName(): String {
        val separator = ", "
        var name = ""

        if (city.isNotBlank()) name += city + separator
        if (state.isNotBlank()) name += state + separator
        if (country.isNotBlank()) name += country

        return name
    }
}