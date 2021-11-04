package com.puntogris.smartwear.feature_weather.domain.repository

import com.puntogris.smartwear.core.utils.SimpleResult
import com.puntogris.smartwear.feature_weather.domain.model.Location
import kotlinx.coroutines.flow.Flow

interface LocationRepository {
    fun getLocalLastLocation(): Flow<Location?>
    suspend fun updateLastLocation(): SimpleResult
    suspend fun insertLastLocation(location: Location)
    suspend fun getLocationCoordinates(query: String): List<Location>
}