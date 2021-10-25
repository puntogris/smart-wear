package com.puntogris.whatdoiwear.data.repository

import com.puntogris.whatdoiwear.data.data_source.LocationClient
import com.puntogris.whatdoiwear.data.data_source.local.LocationDao
import com.puntogris.whatdoiwear.data.data_source.remote.GeocodingApi
import com.puntogris.whatdoiwear.data.data_source.remote.dto.LocationDto
import com.puntogris.whatdoiwear.data.data_source.toEntity
import com.puntogris.whatdoiwear.domain.model.Location
import com.puntogris.whatdoiwear.domain.repository.LocationRepository
import com.puntogris.whatdoiwear.common.SimpleResult
import kotlinx.coroutines.*
import javax.inject.Inject

class LocationRepositoryImpl @Inject constructor(
    private val locationClient: LocationClient,
    private val locationDao: LocationDao,
    private val geocodingApi: GeocodingApi
) : LocationRepository {

    override suspend fun updateLastLocation(): SimpleResult = withContext(Dispatchers.IO){
        try{
            val lastLocalLocation = locationDao.getLastLocation()
            val currentLocation = locationClient.requestLocation()

            if (lastLocalLocation == null || lastLocalLocation.name != currentLocation.name){
                locationDao.insert(currentLocation.toEntity())
            }

            SimpleResult.Success
        }catch (e:Exception){
            SimpleResult.Failure
        }
    }

    override fun getLocalLastLocation() = locationDao.getLastLocationLiveData()

    override suspend fun insertLastLocation(location: Location) {
        locationDao.insert(location.toEntity())
    }

    override suspend fun getGpsLocation(): Location {
        return locationClient.requestLocation()
    }

    override suspend fun getLocationCoordinates(query: String): List<LocationDto> {
        return geocodingApi.getLocations(query)
    }

}