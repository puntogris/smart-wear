package com.puntogris.smartwear.common

import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.view.isVisible
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.puntogris.smartwear.R
import com.puntogris.smartwear.common.constants.HttpRoutes
import com.puntogris.smartwear.data.data_source.remote.dto.WeatherDto
import com.puntogris.smartwear.domain.model.Location
import com.puntogris.smartwear.domain.model.Weather
import kotlin.math.roundToInt

@BindingAdapter("suggestionVisibility")
fun ProgressBar.setSuggestionVisibility(locationResult: LocationResult?) {
    if (locationResult != null)
        isVisible = locationResult is LocationResult.Loading
}

@BindingAdapter("suggestionVisibility")
fun ImageView.setSuggestionVisibility(locationResult: LocationResult?) {
    if (locationResult != null)
        isVisible = locationResult !is LocationResult.Loading
}

@BindingAdapter("suggestionVisibility")
fun CardView.setSuggestionVisibility(locationResult: LocationResult?) {
    if (locationResult != null)
        isVisible = locationResult is LocationResult.Success.GetLocations
}

@BindingAdapter("locationName")
fun TextView.setLocationName(location: Location?) {
    if (location != null)
        text = resources.getString(R.string.forecast_location_name_title, location.name)
}

@BindingAdapter("currentWeather")
fun TextView.setCurrentWeather(weather: Weather?) {
    if (weather != null)
        text = resources.getString(
            R.string.current_weather_temp_description,
            weather.current.temperature.toString(),
            weather.current.description
        )
}

@BindingAdapter("weatherForecast")
fun TextView.setWeatherForecast(weather: Weather?) {
    if (weather != null)
        text = weather.forecast.events.map { it.buildSummary(context) }.joinToString(" ")
}

@BindingAdapter("doubleToStringPercentage")
fun TextView.setDoubleToStringPercentage(double: Double) {
    val doubleText = (double * 100).toInt()
    text = context.getString(R.string.percentage_symbol, doubleText)
}

@BindingAdapter("windSpeedToKmH")
fun TextView.setWindSpeedToKmH(windSpeed: Double) {
    val windSpeedText = (windSpeed / 0.277778).roundToInt()
    text = context.getString(R.string.kmh_symbol, windSpeedText)
}

@BindingAdapter("weatherTemperature")
fun TextView.setWeatherTemperature(temperature: Double) {
    text = context.getString(R.string.temperature_celsius, temperature.roundToInt())
}

@BindingAdapter("weatherIcon")
fun ImageView.setWeatherIcon(icon: String?) {
    if (icon != null) {
        Glide.with(context)
            .load(HttpRoutes.WEATHER_ICON + "/$icon.png")
            .into(this)
    }
}

@BindingAdapter("weatherRecommendation")
fun TextView.buildClothingRecommendation(body: WeatherDto?) {
    if (body == null) return

//    val currentTemp = body.currently.temperature.toInt()
//    val humidity = (body.currently.humidity * 100).roundToInt()
//    val currentWind = (body.currently.windSpeed.toInt() / 0.277778)
//    val currentPrecip = (body.currently.precipProbability *100).roundToInt()
//    val tempIn3Hours = body.hourly.data[3].temperature.toInt()
//    val tempIn6Hours = body.hourly.data[6].temperature.toInt()
//    val precipIn3Hours = (body.hourly.data[3].precipProbability * 100).roundToInt()
//    val precipIn6Hours = (body.hourly.data[6].precipProbability * 100).roundToInt()
//
//    var recommendation = when (currentTemp) {
//        in Int.MIN_VALUE..0 -> resources.getString(R.string.temp_minus_0)
//        in 1..9 -> resources.getString(R.string.temp_1_9)
//        in 10..14 -> resources.getString(R.string.temp_10_14)
//        in 15..19 -> resources.getString(R.string.temp_15_19)
//        in 20..29 -> resources.getString(R.string.temp_20_29)
//        in 30..34 -> resources.getString(R.string.temp_30_34)
//        in 35..39 -> resources.getString(R.string.temp_35_39)
//        !in 0..40 -> resources.getString(R.string.temp_max_40)
//        else -> resources.getString(R.string.temp_error)
//    }
//
//    recommendation += if (tempIn3Hours in (currentTemp + 1) until tempIn6Hours && (tempIn6Hours - currentTemp) >= 5) {
//        resources.getString(R.string.temp_raise_a_lot)
//    } else if (tempIn3Hours in (tempIn6Hours + 1) until currentTemp && (currentTemp - tempIn6Hours) >= 5) {
//        resources.getString(R.string.temp_decrese_a_lot)
//    } else if (currentTemp < tempIn3Hours && (tempIn3Hours - currentTemp) >= 3) {
//        resources.getString(R.string.temp_raise_a_little)
//    } else if (currentTemp > tempIn3Hours && (currentTemp - tempIn3Hours) >= 3) {
//        resources.getString(R.string.temp_decrese_a_little)
//    } else {
//        resources.getString(R.string.temp_not_changing)
//    }
//
//    recommendation += when {
//        currentPrecip in 14..59 -> resources.getString(R.string.precip_15_60)
//        currentPrecip > 60 -> resources.getString(R.string.precip_60)
//        else -> ""
//    }
//
//    recommendation += if (precipIn3Hours in (currentPrecip + 1) until precipIn6Hours && (precipIn6Hours - currentPrecip) >= 50) {
//        resources.getString(R.string.precip_start_rain_50)
//    } else if (precipIn3Hours in (precipIn6Hours + 1) until currentPrecip && (currentPrecip - precipIn6Hours) >= 50) {
//        resources.getString(R.string.precip_stop_rain_50)
//    } else if (currentPrecip > precipIn3Hours && (currentPrecip - precipIn3Hours) >= 25) {
//        resources.getString(R.string.precip_stop_rain_25)
//    } else if (currentPrecip < precipIn3Hours && precipIn3Hours - currentPrecip >= 25) {
//        resources.getString(R.string.precip_start_rain_25)
//    } else ""
//
//
//    recommendation +=
//        if (currentWind > 17) {
//            if ((0..1).random() == 1) resources.getString(R.string.wind_1)
//            else resources.getString(R.string.wind_2)
//        } else resources.getString(R.string.no_wind)
//
//    if (humidity > 89) recommendation += resources.getString(R.string.humidity_89)

    text = "recommendation"

}

