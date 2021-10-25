package com.puntogris.whatdoiwear.domain.use_case

import com.puntogris.whatdoiwear.data.data_source.toDomain
import com.puntogris.whatdoiwear.data.repository.LocationRepositoryImpl
import com.puntogris.whatdoiwear.domain.model.Location
import javax.inject.Inject

class GetGeocodingLocations @Inject constructor(
    private val repository : LocationRepositoryImpl
) {

    suspend operator fun invoke(query: String): List<Location>{
        return repository.getLocationCoordinates(query).map { it.toDomain() }
    }
}