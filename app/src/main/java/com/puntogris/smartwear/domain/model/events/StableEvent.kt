package com.puntogris.smartwear.domain.model.events

import android.content.Context
import com.puntogris.smartwear.R

class StableEvent : ForecastEvent {
    override val summaryRes: Int = R.string.forecast_stable
    override fun buildSummary(context: Context): String = context.getString(summaryRes)
    override fun isValid(): Boolean = true
}
