package com.puntogris.whatdoiwear.data.repository

import com.puntogris.whatdoiwear.data.data_source.remote.WeatherApi
import com.puntogris.whatdoiwear.data.data_source.remote.dto.WeatherDto
import com.puntogris.whatdoiwear.domain.model.Location
import com.puntogris.whatdoiwear.domain.repository.WeatherRepository

class WeatherRepositoryImpl(
    private val weatherApi: WeatherApi
): WeatherRepository{

    override suspend fun getWeather(location: Location): WeatherDto {
         return weatherApi.getWeather(location)
    }
}