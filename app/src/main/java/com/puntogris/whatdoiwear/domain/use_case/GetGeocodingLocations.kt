package com.puntogris.whatdoiwear.domain.use_case

import com.puntogris.whatdoiwear.R
import com.puntogris.whatdoiwear.common.LocationResult
import com.puntogris.whatdoiwear.data.data_source.toDomain
import com.puntogris.whatdoiwear.utils.InvalidQueryException
import com.puntogris.whatdoiwear.domain.repository.LocationRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetGeocodingLocations @Inject constructor(
    private val repository : LocationRepository
) {

    @Throws(InvalidQueryException::class)
    operator fun invoke(query: String): Flow<LocationResult> = flow {
        try {
            emit(LocationResult.Loading)

            if (query.isBlank()){
                throw InvalidQueryException()
            }

            val suggestions = repository.getLocationCoordinates(query).map { it.toDomain() }
            emit(LocationResult.Success.GetLocations(suggestions))
        }catch (e: InvalidQueryException){
            emit(LocationResult.Error(e.error))
        }
        catch (e: Exception){
            emit(LocationResult.Error(R.string.snack_connection_error))
        }
    }

}

