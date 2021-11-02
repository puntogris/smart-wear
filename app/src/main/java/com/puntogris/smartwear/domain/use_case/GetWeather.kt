package com.puntogris.smartwear.domain.use_case

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.puntogris.smartwear.common.Result
import com.puntogris.smartwear.domain.model.Forecast
import com.puntogris.smartwear.domain.model.Location
import com.puntogris.smartwear.domain.model.Weather
import com.puntogris.smartwear.domain.model.WeatherResult
import com.puntogris.smartwear.domain.model.events.*
import com.puntogris.smartwear.domain.repository.WeatherRepository
import com.puntogris.smartwear.utils.TimeOfDay
import javax.inject.Inject

class GetWeather @Inject constructor(private val repository: WeatherRepository) {

    operator fun invoke(location: Location?): LiveData<Result<Weather>> = liveData {
        try {
            emit(Result.Loading)
            val weatherResult = repository.getWeather(location!!)
            val weather = analyzeAndBuildWeather(weatherResult)
            emit(Result.Success(weather))
        } catch (e: Exception) {
            emit(Result.Failure(e))
        }
    }

    private fun analyzeAndBuildWeather(weatherResult: WeatherResult): Weather {
        val hoursAnalyzed = 8
        val timeOfDay = TimeOfDay.withOffset(hoursAnalyzed)

        val events = mutableListOf<ForecastEvent>(
            TemperatureEvent(weatherResult, hoursAnalyzed, timeOfDay),
            RainEvent(weatherResult, hoursAnalyzed),
            WindEvent(weatherResult, hoursAnalyzed),
            HumidityEvent(weatherResult, hoursAnalyzed)
        )

        if (eventAreEmptyOrNotValid(events)) events.add(StableEvent())

        return Weather(
            current = weatherResult.current,
            daily = weatherResult.daily.first(),
            forecast = Forecast(
                events = events,
                time = timeOfDay
            )
        )
    }

    private fun eventAreEmptyOrNotValid(events: List<ForecastEvent>): Boolean {
        return events.filter { it !is TemperatureEvent }.all { !it.isValid() }
    }
}

