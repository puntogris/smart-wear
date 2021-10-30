package com.puntogris.smartwear.domain.model.events

import android.content.Context

abstract class RecommendationEvent : DetailedEvent() {
    abstract fun buildRecommendation(context: Context): String
}