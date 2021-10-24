package com.puntogris.whatdoiwear.data.repo

import com.puntogris.whatdoiwear.data.LocationClient
import com.puntogris.whatdoiwear.data.local.LocationDao
import com.puntogris.whatdoiwear.model.LastLocation
import com.puntogris.whatdoiwear.model.WeatherResponse
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
    private val locationDao: LocationDao,
    private val weatherService: WeatherService
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



        return result
    }

}