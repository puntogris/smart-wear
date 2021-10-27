package com.puntogris.smartwear.domain.use_case

import com.puntogris.smartwear.domain.model.Location
import com.puntogris.smartwear.domain.repository.LocationRepository
import javax.inject.Inject

class InsertLocation @Inject constructor(
    private val repository : LocationRepository
){

    suspend operator fun invoke(location: Location){
        repository.insertLastLocation(location)
    }
}