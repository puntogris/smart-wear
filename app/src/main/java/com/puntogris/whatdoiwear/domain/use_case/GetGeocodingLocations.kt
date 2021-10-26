package com.puntogris.whatdoiwear.domain.use_case

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.puntogris.whatdoiwear.common.SuggestionsResult
import com.puntogris.whatdoiwear.data.data_source.toDomain
import com.puntogris.whatdoiwear.data.repository.LocationRepositoryImpl
import com.puntogris.whatdoiwear.domain.model.Location
import com.puntogris.whatdoiwear.domain.repository.LocationRepository
import javax.inject.Inject

class GetGeocodingLocations @Inject constructor(
    private val repository : LocationRepository
) {

    operator fun invoke(query: String): LiveData<SuggestionsResult> = liveData {
        try {
            emit(SuggestionsResult.InProgress)
            val suggestions = repository.getLocationCoordinates(query).map { it.toDomain() }
            emit(SuggestionsResult.Success(suggestions))
        }catch (e: Exception){
            emit(SuggestionsResult.Failure)
        }
    }
}
