package com.puntogris.whatdoiwear.data

import com.google.gson.GsonBuilder
import com.puntogris.whatdoiwear.model.LastLocation
import com.puntogris.whatdoiwear.model.WeatherBodyApi
import com.puntogris.whatdoiwear.utils.Constants.FIRST_PATH_API
import com.puntogris.whatdoiwear.utils.Constants.SECOND_PATH_API
import com.puntogris.whatdoiwear.utils.WeatherResult
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import okhttp3.*
import java.io.IOException
import javax.inject.Inject

class Repository @Inject constructor(
    private val locationClient: LocationClient,
) : IRepository{

    @ExperimentalCoroutinesApi
    override fun getLocation(): Flow<LastLocation> = locationClient.requestLocation()

    override fun getWeatherApi(location: LastLocation): MutableStateFlow<WeatherResult> {
        val result = MutableStateFlow<WeatherResult>(WeatherResult.InProgress)
        val url = "$FIRST_PATH_API${location.latitude},${location.longitude}$SECOND_PATH_API"

        val request = Request.Builder().url(url).build()
        OkHttpClient().newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                result.value = WeatherResult.Error(e)
            }
            override fun onResponse(call: Call, response: Response) {
                val body = response.body?.string()
                val gson = GsonBuilder().create()
                val cities = gson.fromJson(body, WeatherBodyApi::class.java)
                result.value = WeatherResult.Success(cities)
            }
        })

        return result
    }

}