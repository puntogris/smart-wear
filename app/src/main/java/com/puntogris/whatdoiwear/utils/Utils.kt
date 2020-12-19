package com.puntogris.whatdoiwear.utils

import com.google.gson.GsonBuilder
import com.puntogris.whatdoiwear.model.LastLocation
import com.puntogris.whatdoiwear.model.WeatherBodyApi
import okhttp3.Response

object Utils {

    fun createApiPathWithLatLong(location: LastLocation):String =
        "${Constants.FIRST_PATH_API}${location.latitude},${location.longitude}${Constants.SECOND_PATH_API}"

    fun convertJsonToWeatherBodyApi(response: Response): WeatherBodyApi{
        val body = response.body?.string()
        val gson = GsonBuilder().create()
        return gson.fromJson(body, WeatherBodyApi::class.java)
    }
}