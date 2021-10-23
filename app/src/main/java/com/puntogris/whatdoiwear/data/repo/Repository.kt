package com.puntogris.whatdoiwear.data.repo

import com.puntogris.whatdoiwear.data.LocationClient
import com.puntogris.whatdoiwear.data.local.LocationDao
import com.puntogris.whatdoiwear.model.LastLocation
import com.puntogris.whatdoiwear.model.WeatherBodyApi
import com.puntogris.whatdoiwear.utils.SimpleResult
import com.puntogris.whatdoiwear.utils.Utils.createApiPathWithLatLong
import com.puntogris.whatdoiwear.utils.WeatherResult
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow
import okhttp3.*
import java.io.IOException
import javax.inject.Inject

@DelicateCoroutinesApi
class Repository @Inject constructor(
    private val locationClient: LocationClient,
    private val locationDao: LocationDao
) : IRepository {

    override suspend fun updateLastLocation(): SimpleResult = withContext(Dispatchers.IO){
        try{
            val lastLocalLocation = locationDao.getLastLocation()
            val currentLocation = locationClient.requestLocation()

            if (lastLocalLocation == null || lastLocalLocation.name != currentLocation.name){
                locationDao.insert(currentLocation)
            }

            SimpleResult.Success
        }catch (e:Exception){
            SimpleResult.Failure
        }
    }

    override fun getLocalLastLocation() = locationDao.getLastLocationLiveData()


    override suspend fun insertLastLocation(lastLocation: LastLocation) {
        locationDao.insert(lastLocation)
    }

    override fun getWeatherApi(location: LastLocation): MutableStateFlow<WeatherResult> {
        val result = MutableStateFlow<WeatherResult>(WeatherResult.InProgress)
        val url = createApiPathWithLatLong(location)
        val request = Request.Builder().url(url).build()

        OkHttpClient().newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                result.value = WeatherResult.Error
            }
            override fun onResponse(call: Call, response: Response) {
                result.value = WeatherResult.Success(WeatherBodyApi.fromResponse(response))
            }
        })
        return result
    }

}