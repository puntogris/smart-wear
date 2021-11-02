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
import com.puntogris.smartwear.domain.model.Location
import com.puntogris.smartwear.domain.model.Weather
import com.puntogris.smartwear.domain.model.events.RecommendationEvent

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
            weather.current.temperature.asString(),
            weather.current.description
        )
}

@BindingAdapter("weatherForecast")
fun TextView.setWeatherForecast(weather: Weather?) {
    if (weather != null)
        text = weather.forecast.events
            .filter { it.isValid() }
            .joinToString(" ")
            { it.buildSummary(context) }
}

@BindingAdapter("clothingRecommendation")
fun TextView.setClothingRecommendation(weather: Weather?) {
    if (weather == null) return
    text = weather.forecast.events
        .filterIsInstance<RecommendationEvent>()
        .filter { it.isValid() }
        .joinToString(" ")
        { it.buildRecommendation(context) }
}

@BindingAdapter("weatherIcon")
fun ImageView.setWeatherIcon(icon: String?) {
    if (icon != null) {
        Glide.with(context)
            .load(HttpRoutes.WEATHER_ICON + "/$icon.png")
            .into(this)
    }
}


