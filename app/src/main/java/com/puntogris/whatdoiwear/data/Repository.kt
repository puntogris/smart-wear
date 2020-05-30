package com.puntogris.whatdoiwear.data

import android.content.Context
import android.location.Geocoder
import android.location.Location
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.puntogris.whatdoiwear.model.WeatherBodyApi
import okhttp3.*
import java.io.IOException
import java.util.*
import javax.inject.Inject

class Repository @Inject constructor(
    private val customLiveData: LocationLiveData,
    private val context: Context) : IRepository{

    override fun getWeatherApi(location: Location) :LiveData<WeatherBodyApi>{
        val url =
            "https://api.darksky.net/forecast/e3c77f1ab2f31f0a772b2485589cd914/${location.latitude},${location.longitude}?units=si&lang=es"

        val client = OkHttpClient()
        val request = Request.Builder().url(url).build()

        val liveData = MutableLiveData<WeatherBodyApi>()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                //handle errors
            }
            override fun onResponse(call: Call, response: Response) {
                val body = response.body()?.string()
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