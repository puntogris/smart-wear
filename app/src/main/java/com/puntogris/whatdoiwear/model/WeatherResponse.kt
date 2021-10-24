package com.puntogris.whatdoiwear.model

import com.google.gson.GsonBuilder
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import okhttp3.Response

@Serializable
data class WeatherResponse(

    @SerialName("b")
    val currently: CurrentWeather,

    @SerialName("a")
    val hourly: HourlyWeather
    ){

}
