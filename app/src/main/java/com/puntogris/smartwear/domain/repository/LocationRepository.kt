package com.puntogris.smartwear.domain.repository

import com.puntogris.smartwear.common.SimpleResult
import com.puntogris.smartwear.domain.model.Location
import kotlinx.coroutines.flow.Flow

interface LocationRepository {
    fun getLocalLastLocation(): Flow<Location?>
    suspend fun updateLastLocation(): SimpleResult
    suspend fun insertLastLocation(location: Location)
    suspend fun getLocationCoordinates(query: String): List<Location>
}