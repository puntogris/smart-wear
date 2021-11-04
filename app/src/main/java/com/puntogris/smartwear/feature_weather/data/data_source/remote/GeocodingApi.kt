package com.puntogris.smartwear.feature_weather.data.data_source.remote

import android.location.Location
import com.puntogris.smartwear.BuildConfig
import com.puntogris.smartwear.core.utils.constants.HttpRoutes
import com.puntogris.smartwear.feature_weather.data.data_source.remote.dto.LocationDto
import io.ktor.client.*
import io.ktor.client.request.*
import javax.inject.Inject

class GeocodingApi @Inject constructor(
    private val client: HttpClient
) {
    suspend fun getLocations(query: String): List<LocationDto> {
        return client.get {
            url(HttpRoutes.FORWARD_GEOCODING)
            parameter("key", BuildConfig.LOCATIONIQ_API_KEY)
            parameter("q", query)
            parameter("format", "json")
            parameter("limit", 5)
            parameter("addressdetails", 1)
            parameter("normalizecity", 1)
        }
    }

    suspend fun getLocationCoordinates(location: Location): LocationDto {
        return client.get {
            url(HttpRoutes.REVERSE_GEOCODING)
            parameter("key", BuildConfig.LOCATIONIQ_API_KEY)
            parameter("lat", location.latitude)
            parameter("lon", location.longitude)
            parameter("format", "json")
            parameter("addressdetails", 1)
        }
    }
}