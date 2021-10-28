package com.puntogris.smartwear.domain.repository

import androidx.lifecycle.LiveData
import com.puntogris.smartwear.common.SimpleResult
import com.puntogris.smartwear.domain.model.Location

interface LocationRepository {
    fun getLocalLastLocation(): LiveData<Location?>
    suspend fun updateLastLocation(): SimpleResult
    suspend fun insertLastLocation(location: Location)
    suspend fun getLocationCoordinates(query: String): List<Location>
}