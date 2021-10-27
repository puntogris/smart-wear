package com.puntogris.smartwear.domain.repository

import androidx.lifecycle.LiveData
import com.puntogris.smartwear.data.data_source.local.model.LocationEntity
import com.puntogris.smartwear.data.data_source.remote.dto.LocationDto
import com.puntogris.smartwear.domain.model.Location
import com.puntogris.smartwear.common.SimpleResult

interface LocationRepository {
    fun getLocalLastLocation(): LiveData<LocationEntity?>
    suspend fun updateLastLocation(): SimpleResult
    suspend fun insertLastLocation(location: Location)
    suspend fun getLocationCoordinates(query: String): List<LocationDto>
}