package com.puntogris.whatdoiwear.domain.use_case

import com.puntogris.whatdoiwear.data.repository.LocationRepositoryImpl
import com.puntogris.whatdoiwear.domain.model.Location
import javax.inject.Inject

class InsertLocation @Inject constructor(
    private val locationRepository: LocationRepositoryImpl
){

    suspend operator fun invoke(location: Location){
        locationRepository.insertLastLocation(location)
    }
}