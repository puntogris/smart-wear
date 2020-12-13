package com.puntogris.whatdoiwear.data

import android.content.Context
import android.location.Address
import android.location.Geocoder
import android.location.Location
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.gson.GsonBuilder
import com.puntogris.whatdoiwear.model.LastLocation
import com.puntogris.whatdoiwear.model.Result
import com.puntogris.whatdoiwear.model.WeatherBodyApi
import com.puntogris.whatdoiwear.utils.Constants.FIRST_PATH_API
import com.puntogris.whatdoiwear.utils.Constants.SECOND_PATH_API
import com.puntogris.whatdoiwear.utils.MySharedPreferences
import com.puntogris.whatdoiwear.utils.getLocationName
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow
import okhttp3.*
import okhttp3.internal.ignoreIoExceptions
import okhttp3.internal.notify
import okhttp3.internal.wait
import java.io.IOException
import java.util.*
import javax.inject.Inject

class Repository @Inject constructor(
    private val customLiveData: LocationLiveData,
    @ApplicationContext private val context: Context,
) : IRepository{

    override fun getLocation(): LocationLiveData{
        return customLiveData
    }

    override fun getLocationNameAsync(location: LastLocation) : Deferred<String> {
        return GlobalScope.async(Dispatchers.IO) {
            val gcd = Geocoder(context, Locale.getDefault())
            val addresses = gcd.getFromLocation(location.latitude,
                location.longitude, 1)

            return@async if(!addresses.isNullOrEmpty()) addresses[0].getLocationName() else "Error"
        }
    }

    override fun getWeatherApi(location: LastLocation): MutableStateFlow<Result> {
        val result = MutableStateFlow<Result>(Result.InProgress)
        val url = "$FIRST_PATH_API${location.latitude},${location.longitude}$SECOND_PATH_API"

        val request = Request.Builder().url(url).build()
        OkHttpClient().newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                result.value = Result.Error(e)
            }
            override fun onResponse(call: Call, response: Response) {
                val body = response.body?.string()
                val gson = GsonBuilder().create()
                val cities = gson.fromJson(body, WeatherBodyApi::class.java)
                result.value = Result.Success(cities)
            }
        })

        return result
    }

}