package com.puntogris.smartwear.feature_weather.domain.use_case

import com.puntogris.smartwear.R
import com.puntogris.smartwear.feature_weather.presentation.util.LocationResult
import com.puntogris.smartwear.feature_weather.domain.repository.LocationRepository
import com.puntogris.smartwear.feature_weather.presentation.util.InvalidQueryException
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

            val suggestions = repository.getLocationCoordinates(query)
            emit(LocationResult.Success.GetLocations(suggestions))
        }catch (e: InvalidQueryException){
            emit(LocationResult.Error(e.error))
        }
        catch (e: Exception){
            emit(LocationResult.Error(R.string.snack_connection_error))
        }
    }

}

