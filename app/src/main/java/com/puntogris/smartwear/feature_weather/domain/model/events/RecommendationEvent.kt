package com.puntogris.smartwear.feature_weather.domain.model.events

import android.content.Context

abstract class RecommendationEvent : DetailedEvent() {
    abstract fun buildRecommendation(context: Context): String
}