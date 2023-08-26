package com.puntogris.smartwear.feature_weather.data.repository

import com.puntogris.smartwear.core.utils.SimpleResult
import com.puntogris.smartwear.feature_weather.data.data_source.FusedLocationClient
import com.puntogris.smartwear.feature_weather.data.data_source.local.LocationDao
import com.puntogris.smartwear.feature_weather.data.data_source.remote.GeocodingApi
import com.puntogris.smartwear.feature_weather.data.data_source.toDomain
import com.puntogris.smartwear.feature_weather.data.data_source.toEntity
import com.puntogris.smartwear.feature_weather.domain.model.Location
import com.puntogris.smartwear.feature_weather.domain.repository.DispatcherProvider
import com.puntogris.smartwear.feature_weather.domain.repository.LocationRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

class LocationRepositoryImpl(
    private val locationClient: FusedLocationClient,
    private val locationDao: LocationDao,
    private val geocodingApi: GeocodingApi,
    private val dispatchers: DispatcherProvider
) : LocationRepository {

    override fun getLocalLastLocation(): Flow<Location?> {
        return locationDao.getLocationLiveData().map { it?.toDomain() }
    }

    override suspend fun updateLastLocation(): SimpleResult = withContext(dispatchers.io) {
        try {
            val lastLocalLocation = locationDao.getLastLocation()
            val currentLocation = locationClient.requestLocation()

            val finalLocation = geocodingApi.getLocationCoordinates(currentLocation).toEntity()

            if (lastLocalLocation == null || lastLocalLocation.name != finalLocation.name) {
                locationDao.insert(finalLocation)
            }
            SimpleResult.Success
        } catch (e: Exception) {
            SimpleResult.Failure
        }
    }

    override suspend fun insertLastLocation(location: Location) = withContext(dispatchers.io) {
        locationDao.insert(location.toEntity())
    }

    override suspend fun getLocationCoordinates(query: String): List<Location> {
        return geocodingApi.getLocations(query).map { it.toDomain() }
    }
}