package com.puntogris.whatdoiwear.data.repository

import com.puntogris.whatdoiwear.common.SimpleResult
import com.puntogris.whatdoiwear.data.data_source.LocationClient
import com.puntogris.whatdoiwear.data.data_source.local.LocationDao
import com.puntogris.whatdoiwear.data.data_source.remote.GeocodingApi
import com.puntogris.whatdoiwear.data.data_source.remote.dto.LocationDto
import com.puntogris.whatdoiwear.data.data_source.toEntity
import com.puntogris.whatdoiwear.domain.model.Location
import com.puntogris.whatdoiwear.domain.repository.DispatcherProvider
import com.puntogris.whatdoiwear.domain.repository.LocationRepository
import kotlinx.coroutines.withContext

class LocationRepositoryImpl(
    private val locationClient: LocationClient,
    private val locationDao: LocationDao,
    private val geocodingApi: GeocodingApi,
    private val dispatchers: DispatcherProvider
) : LocationRepository {

    override suspend fun updateLastLocation(): SimpleResult = withContext(dispatchers.io){
        try{
            val lastLocalLocation = locationDao.getLastLocation()
            val currentLocation = locationClient.requestLocation()

            val finalLocation = geocodingApi.getLocationCoordinates(currentLocation).toEntity()

            if (lastLocalLocation == null || lastLocalLocation.name != finalLocation.name){
                locationDao.insert(finalLocation)
            }

            SimpleResult.Success
        }catch (e:Exception){
            SimpleResult.Failure
        }
    }

    override fun getLocalLastLocation() = locationDao.getLastLocationLiveData()

    override suspend fun insertLastLocation(location: Location) = withContext(dispatchers.io){
        locationDao.insert(location.toEntity())
    }

    override suspend fun getLocationCoordinates(query: String): List<LocationDto> {
        return geocodingApi.getLocations(query)
    }

}