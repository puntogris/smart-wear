package com.puntogris.whatdoiwear.data.repository

import com.puntogris.whatdoiwear.data.data_source.LocationClient
import com.puntogris.whatdoiwear.data.data_source.local.LocationDao
import com.puntogris.whatdoiwear.data.data_source.remote.GeocodingApi
import com.puntogris.whatdoiwear.data.data_source.remote.WeatherApi
import com.puntogris.whatdoiwear.domain.model.LastLocation
import com.puntogris.whatdoiwear.domain.repository.Repository
import com.puntogris.whatdoiwear.utils.SimpleResult
import com.puntogris.whatdoiwear.utils.WeatherResult
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

@DelicateCoroutinesApi
class RepositoryImpl @Inject constructor(
    private val locationClient: LocationClient,
    private val locationDao: LocationDao,
    private val weatherApi: WeatherApi,
    private val geocodingApi: GeocodingApi
) : Repository {

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

       // weatherService.getWeather()

        return result
    }

}