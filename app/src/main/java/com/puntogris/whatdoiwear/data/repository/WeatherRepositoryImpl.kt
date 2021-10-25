package com.puntogris.whatdoiwear.data.repository

import com.puntogris.whatdoiwear.data.data_source.remote.WeatherApi
import com.puntogris.whatdoiwear.domain.model.Location
import com.puntogris.whatdoiwear.domain.repository.WeatherRepository
import com.puntogris.whatdoiwear.common.WeatherResult
import com.puntogris.whatdoiwear.data.data_source.remote.dto.WeatherDto
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

class WeatherRepositoryImpl @Inject constructor(
    private val weatherApi: WeatherApi
): WeatherRepository{

    override suspend fun getWeather(location: Location): WeatherDto {
         return weatherApi.getWeather(location)
    }
}