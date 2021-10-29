package com.puntogris.smartwear.domain.use_case

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.puntogris.smartwear.common.Result
import com.puntogris.smartwear.domain.model.*
import com.puntogris.smartwear.domain.repository.WeatherRepository
import com.puntogris.smartwear.utils.TimeOfDay
import javax.inject.Inject

class GetWeather @Inject constructor(
    private val repository: WeatherRepository
) {

    operator fun invoke(location: Location?): LiveData<Result<Weather>> = liveData {
        try {
            emit(Result.Loading)
            val weatherResult = repository.getWeather(location!!)

            val hoursAnalyzed = 8
            val hourlyWeather = weatherResult.hourly.subList(0, hoursAnalyzed)

            val events = mutableListOf(
                TemperatureRange(weatherResult.daily.first()),
                RainEvent(),
                WindEvent(),
                HumidityEvent()
            )

            with(events) {
                forEach {
                    if (it is EventfulEvent) it.analyze(hourlyWeather)
                }

                removeIf { it.isNotValid() }
                if (isEmpty()) add(StableEvent())
            }

            val weather = Weather(
                current = weatherResult.current,
                daily = weatherResult.daily.first(),
                forecast = Forecast(
                    events = events,
                    time = TimeOfDay.withOffset(hoursAnalyzed)
                )
            )

            emit(Result.Success(weather))
        } catch (e: Exception) {
            emit(Result.Failure(e))
        }
    }
}

