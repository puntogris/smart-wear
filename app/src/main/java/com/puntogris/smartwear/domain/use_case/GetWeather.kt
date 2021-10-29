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
            val hourly = weatherResult.hourly.subList(0, hoursAnalyzed)

            val events = hashMapOf<String, ForecastEvent>()

            //todo think of a better idea to do this, as a last resource i could use maxby for each event

            hourly.forEach {
                if (it.precipitation > 0) {
                    events["rain"] = RainEvent(it.precipitation)
                }
                if (it.windSpeed > 0) {
                    events["wind"] = WindEvent(it.windSpeed)
                }
                if (it.humidity > 0) {
                    events["humidity"] = HumidityEvent(it.humidity)
                }
            }

            events["temperature"] = TemperatureRange(weatherResult.daily.first())

            events.values.removeIf {
                it.isNotValid()
            }

            if (events.values.isEmpty() ||
                events.values.size == 1 && events.values.first() is TemperatureRange
            ) {
                events["stable"] = StableEvent()
            }

            val weather = Weather(
                current = weatherResult.current,
                daily = weatherResult.daily.first(),
                forecast = Forecast(
                    events = events.values.toList(),
                    time = TimeOfDay.withOffset(hoursAnalyzed)
                )
            )

            emit(Result.Success(weather))
        } catch (e: Exception) {
            emit(Result.Failure(e))
        }
    }
}

