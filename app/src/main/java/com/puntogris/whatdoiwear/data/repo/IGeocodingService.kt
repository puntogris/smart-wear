package com.puntogris.whatdoiwear.data.repo

import com.puntogris.whatdoiwear.model.GeocodingResponse

interface IGeocodingService {
    suspend fun getLocationCoordinates(location: String): List<GeocodingResponse>
}