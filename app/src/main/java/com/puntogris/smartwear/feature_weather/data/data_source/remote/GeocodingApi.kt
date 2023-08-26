package com.puntogris.smartwear.feature_weather.data.data_source.remote

import com.puntogris.smartwear.feature_weather.data.data_source.remote.dto.LocationDto
import retrofit2.http.GET
import retrofit2.http.Query

interface GeocodingApi {

    @GET("v1/search")
    suspend fun getLocations(
        @Query("q") query: String,
        @Query("limit") limit: Int = 5,
        @Query("normalizecity") normalizecity: Int = 1,
        @Query("addressdetails") addressdetails: Int = 1
    ): List<LocationDto>

    @GET("v1/reverse")
    suspend fun getLocationCoordinates(
        @Query("lat") lat: Double,
        @Query("lon") lon: Double,
        @Query("normalizecity") addressdetails: Int = 1
    ): LocationDto
}