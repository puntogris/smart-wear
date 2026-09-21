package com.puntogris.smartwear.domain.model

import com.puntogris.smartwear.domain.model.conditions.Temperature

class Daily(
    val min: Temperature,
    val max: Temperature
)