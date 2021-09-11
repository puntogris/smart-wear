package com.puntogris.whatdoiwear.data.repo

import com.puntogris.whatdoiwear.data.LocationClient
import com.puntogris.whatdoiwear.model.LastLocation
import com.puntogris.whatdoiwear.model.WeatherBodyApi
import com.puntogris.whatdoiwear.utils.Utils.createApiPathWithLatLong
import com.puntogris.whatdoiwear.utils.WeatherResult
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import okhttp3.*
import java.io.IOException
import javax.inject.Inject

class Repository @Inject constructor(
    private val locationClient: LocationClient,
) : IRepository {

    @ExperimentalCoroutinesApi
    override fun getLocation(): Flow<LastLocation> = locationClient.requestLocation()

    override fun getWeatherApi(location: LastLocation): MutableStateFlow<WeatherResult> {
        val result = MutableStateFlow<WeatherResult>(WeatherResult.InProgress)
        val url = createApiPathWithLatLong(location)
        val request = Request.Builder().url(url).build()

        OkHttpClient().newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                result.value = WeatherResult.Error(e)
            }
            override fun onResponse(call: Call, response: Response) {
                result.value = WeatherResult.Success(WeatherBodyApi.fromResponse(response))
            }
        })
        return result
    }

}