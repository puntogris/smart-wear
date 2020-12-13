package com.puntogris.whatdoiwear.data

import android.content.Context
import android.location.Geocoder
import android.location.Location
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.liveData
import com.google.gson.GsonBuilder
import com.puntogris.whatdoiwear.model.Result
import com.puntogris.whatdoiwear.model.WeatherBodyApi
import com.puntogris.whatdoiwear.utils.Constants.FIRST_PATH_API
import com.puntogris.whatdoiwear.utils.Constants.SECOND_PATH_API
import com.puntogris.whatdoiwear.utils.MySharedPreferences
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import okhttp3.*
import java.io.IOException
import java.lang.Exception
import java.util.*
import javax.inject.Inject

class Repository @Inject constructor(
    private val customLiveData: LocationLiveData,
    @ApplicationContext private val context: Context,
    private val sharedPref: MySharedPreferences
) : IRepository{

    override fun getLocation(): LocationLiveData{
        return customLiveData
    }

    override fun getLocationName(location: Location): LiveData<String> {
        val liveData = MutableLiveData<String>()
        Geocoder(context, Locale.getDefault()).getFromLocation(
            location.latitude,
            location.longitude,
            1
        ).also {
            if (!it.isNullOrEmpty()) {
                val locationName = it[0].locality + ", "+ it[0].adminArea
                if (sharedPref.getLastLocation() != locationName) sharedPref.setLastLocation(locationName)
                liveData.postValue(locationName)
            }
        }
        return liveData
    }

    override fun getWeatherApi(location: Location): MutableStateFlow<Result> {
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