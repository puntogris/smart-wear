package com.puntogris.whatdoiwear.domain.repository

import androidx.lifecycle.LiveData
import com.puntogris.whatdoiwear.data.data_source.local.model.LocationEntity
import com.puntogris.whatdoiwear.data.data_source.remote.dto.LocationDto
import com.puntogris.whatdoiwear.domain.model.Location
import com.puntogris.whatdoiwear.utils.SimpleResult

interface LocationRepository {
    fun getLocalLastLocation(): LiveData<LocationEntity?>
    suspend fun updateLastLocation(): SimpleResult
    suspend fun insertLastLocation(location: Location)
    suspend fun getGpsLocation(): Location
    suspend fun getLocationCoordinates(query: String): List<LocationDto>
}