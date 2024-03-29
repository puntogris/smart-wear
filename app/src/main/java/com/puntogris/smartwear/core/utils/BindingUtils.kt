package com.puntogris.smartwear.core.utils

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.puntogris.smartwear.R
import com.puntogris.smartwear.core.utils.constants.HttpRoutes
import com.puntogris.smartwear.feature_weather.domain.model.Location
import com.puntogris.smartwear.feature_weather.domain.model.Weather
import com.puntogris.smartwear.feature_weather.domain.model.events.RecommendationEvent

@BindingAdapter("locationVisibility")
fun View.setLocationVisibility(currentLocation: Location?) {
    isVisible = currentLocation != null
}

@BindingAdapter("emptyHolderVisibility")
fun View.setEmptyHolderVisibility(currentLocation: Location?) {
    isVisible = currentLocation == null
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


