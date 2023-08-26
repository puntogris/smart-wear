package com.puntogris.smartwear.feature_weather.data.data_source.remote.dto

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class AddressResult(

    @SerializedName("country")
    val country: String = "",

    @SerializedName("state")
    val state: String = "",

    @SerializedName("city")
    val city: String = "",

    @SerializedName("region")
    val region: String = "",

    @SerializedName("municipality")
    val municipality: String = "",

    @SerializedName("state_district")
    val stateDistrict: String = "",

    @SerializedName("county")
    val county: String = "",

    @SerializedName("town")
    val town: String = ""
) {
    val name = getAddressName()

    private fun getAddressName(): String {
        val separator = ", "

        return buildString {
            if (city.isNotBlank()) append(city + separator)
            if (state.isNotBlank()) append(state + separator)
            if (country.isNotBlank()) append(country)
        }
    }
}