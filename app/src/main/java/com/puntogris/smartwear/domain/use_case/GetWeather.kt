package com.puntogris.smartwear.domain.use_case

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.puntogris.smartwear.common.Result
import com.puntogris.smartwear.domain.model.Forecast
import com.puntogris.smartwear.domain.model.Location
import com.puntogris.smartwear.domain.model.Weather
import com.puntogris.smartwear.domain.model.events.*
import com.puntogris.smartwear.domain.repository.WeatherRepository
import com.puntogris.smartwear.utils.TimeOfDay
import javax.inject.Inject

class GetWeather @Inject constructor(private val repository: WeatherRepository) {

    operator fun invoke(location: Location?): LiveData<Result<Weather>> = liveData {
        try {
            emit(Result.Loading)
            val weatherResult = repository.getWeather(location!!)

            val hoursAnalyzed = 8
            val hourlyWeather = weatherResult.hourly.subList(0, hoursAnalyzed)
            val timeOfDay = TimeOfDay.withOffset(hoursAnalyzed)

            val events = mutableListOf<ForecastEvent>(
                TemperatureEvent(weatherResult, timeOfDay),
                RainEvent(hourlyWeather),
                WindEvent(hourlyWeather),
                HumidityEvent(hourlyWeather)
            )

            if (eventAreEmptyOrNotValid(events)) events.add(StableEvent())

            val weather = Weather(
                current = weatherResult.current,
                daily = weatherResult.daily.first(),
                forecast = Forecast(
                    events = events,
                    time = timeOfDay
                )
            )

            emit(Result.Success(weather))
        } catch (e: Exception) {
            emit(Result.Failure(e))
        }
    }

    private fun eventAreEmptyOrNotValid(events: List<ForecastEvent>) =
        events.filter { it !is TemperatureEvent }.all { !it.isValid() }
}

