package com.puntogris.whatdoiwear.data

import android.location.Location
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.gson.GsonBuilder
import com.puntogris.whatdoiwear.model.CurrentWeather
import com.puntogris.whatdoiwear.model.WeatherBodyApi
import okhttp3.*
import java.io.IOException

class Repository :IRepository{

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




}