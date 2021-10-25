package com.puntogris.whatdoiwear.data.data_source.remote

import com.puntogris.whatdoiwear.BuildConfig
import com.puntogris.whatdoiwear.common.constants.HttpRoutes
import com.puntogris.whatdoiwear.data.data_source.remote.dto.LocationDto
import io.ktor.client.*
import io.ktor.client.request.*
import javax.inject.Inject

class GeocodingApi @Inject constructor(
    private val client: HttpClient
) {

    suspend fun getLocations(query: String): List<LocationDto> {
        return client.get {
            url(HttpRoutes.GEOCODING)
            parameter("key", BuildConfig.LOCATIONIQ_API_KEY)
            parameter("q", query)
            parameter("format", "json")
            parameter("limit", 5)
            parameter("addressdetails", 1)
        }
    }
}