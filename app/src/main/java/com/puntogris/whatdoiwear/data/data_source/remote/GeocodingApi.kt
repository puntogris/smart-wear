package com.puntogris.whatdoiwear.data.data_source.remote

import com.puntogris.whatdoiwear.BuildConfig
import com.puntogris.whatdoiwear.domain.model.GeocodingResponse
import com.puntogris.whatdoiwear.utils.constants.HttpRoutes
import io.ktor.client.*
import io.ktor.client.request.*
import javax.inject.Inject

class GeocodingApi @Inject constructor(
    private val client: HttpClient
) {

    suspend fun getLocationCoordinates(location: String): List<GeocodingResponse> {
        return client.get {
            url(HttpRoutes.GEOCODING)
            parameter("key", BuildConfig.LOCATIONIQ_API_KEY)
            parameter("q", location)
            parameter("format", "json")
            parameter("limit", 5)
            parameter("addressdetails", 1)
        }
    }

}