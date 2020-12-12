package com.puntogris.whatdoiwear.data

import android.content.Context
import android.location.Geocoder
import android.location.Location
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.liveData
import com.google.gson.GsonBuilder
import com.puntogris.whatdoiwear.model.WeatherBodyApi
import com.puntogris.whatdoiwear.utils.Constants.FIRST_PATH_API
import com.puntogris.whatdoiwear.utils.Constants.SECOND_PATH_API
import okhttp3.*
import java.io.IOException
import java.lang.Exception
import java.util.*
import javax.inject.Inject

class Repository @Inject constructor(
    private val customLiveData: LocationLiveData,
    private val context: Context) : IRepository{

    override fun getWeatherApi(location: Location) :LiveData<WeatherBodyApi>{
        val url = "$FIRST_PATH_API${location.latitude},${location.longitude}$SECOND_PATH_API"

        val client = OkHttpClient()
        val request = Request.Builder().url(url).build()

        val liveData = MutableLiveData<WeatherBodyApi>()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                //handle errors
            }
            override fun onResponse(call: Call, response: Response) {
                val body = response.body?.string()
                val gson = GsonBuilder().create()
                val cities = gson.fromJson(body, WeatherBodyApi::class.java)

                liveData.postValue(cities)
            }
        })

        return liveData
    }

    override fun getLocation(): LocationLiveData{
        return customLiveData
    }

    override fun getLocationName(location: Location): LiveData<String> {
        val livedata = MutableLiveData<String>()
        Geocoder(context, Locale.getDefault()).getFromLocation(
            location.latitude,
            location.longitude,
            1
        ).also {
            livedata.postValue(it[0].locality + ", "+ it[0].adminArea)
        }
        return livedata
    }

}