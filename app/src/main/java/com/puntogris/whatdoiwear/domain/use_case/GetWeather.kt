package com.puntogris.whatdoiwear.domain.use_case

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asLiveData
import com.puntogris.whatdoiwear.data.repository.WeatherRepositoryImpl
import com.puntogris.whatdoiwear.domain.model.Location
import com.puntogris.whatdoiwear.utils.WeatherResult
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

class GetWeather @Inject constructor(
    private val repository: WeatherRepositoryImpl
){

    operator fun invoke(location: Location?): LiveData<WeatherResult> {
        return if (location == null) {
            MutableLiveData(WeatherResult.Error)
        }
        else {
            repository.getWeatherApi(location).asLiveData()
        }
    }
}