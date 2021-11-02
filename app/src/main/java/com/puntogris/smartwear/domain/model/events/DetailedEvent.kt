package com.puntogris.smartwear.domain.model.events

import android.content.Context
import com.puntogris.smartwear.data.data_source.Condition

abstract class DetailedEvent: ForecastEvent {

    abstract val eventValues: List<Condition>
    abstract val metricReferenceValue: Int

    abstract val getMaxCondition: Condition?

    override fun buildSummary(context: Context): String {
        return context.getString(
            summaryRes,
            getMaxCondition?.asString()
        )
    }

    override fun isValid(): Boolean = getMaxCondition?.metricValue() ?: 0 > metricReferenceValue
}