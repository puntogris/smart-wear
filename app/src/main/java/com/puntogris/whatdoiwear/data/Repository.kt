package com.puntogris.whatdoiwear.data

import android.content.Context
import android.location.Geocoder
import androidx.lifecycle.LiveData
import com.google.gson.GsonBuilder
import com.puntogris.whatdoiwear.model.LastLocation
import com.puntogris.whatdoiwear.model.WeatherBodyApi
import com.puntogris.whatdoiwear.utils.Constants.FIRST_PATH_API
import com.puntogris.whatdoiwear.utils.Constants.SECOND_PATH_API
import com.puntogris.whatdoiwear.utils.WeatherResult
import com.puntogris.whatdoiwear.utils.getLocationName
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow
import okhttp3.*
import java.io.IOException
import java.util.*
import javax.inject.Inject

class Repository @Inject constructor(
    @ApplicationContext private val context: Context,
    private val locationClient: LocationClient,
) : IRepository{

    override fun getLocation(): LiveData<LastLocation> = locationClient

    override fun getLocationNameAsync(location: LastLocation) : Deferred<String> {
        return GlobalScope.async(Dispatchers.IO) {
            val gcd = Geocoder(context, Locale.getDefault())
            val addresses = gcd.getFromLocation(location.latitude,
                location.longitude, 1)

            return@async if(!addresses.isNullOrEmpty()) addresses[0].getLocationName() else "Error"
        }
    }

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