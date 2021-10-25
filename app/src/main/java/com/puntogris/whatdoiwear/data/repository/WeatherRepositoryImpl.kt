package com.puntogris.whatdoiwear.data.repository

import com.puntogris.whatdoiwear.data.data_source.remote.WeatherApi
import com.puntogris.whatdoiwear.domain.model.Location
import com.puntogris.whatdoiwear.domain.repository.WeatherRepository
import com.puntogris.whatdoiwear.utils.WeatherResult
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

class WeatherRepositoryImpl @Inject constructor(
    private val weatherApi: WeatherApi
): WeatherRepository{

    override fun getWeatherApi(location: Location): MutableStateFlow<WeatherResult> {
        val result = MutableStateFlow<WeatherResult>(WeatherResult.InProgress)

        // weatherService.getWeather()

        return result
    }
}