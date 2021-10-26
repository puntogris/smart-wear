package com.puntogris.whatdoiwear.domain.use_case

import com.puntogris.whatdoiwear.domain.model.Location
import com.puntogris.whatdoiwear.domain.repository.LocationRepository
import javax.inject.Inject

class InsertLocation @Inject constructor(
    private val repository : LocationRepository
){

    suspend operator fun invoke(location: Location){
        repository.insertLastLocation(location)
    }
}