package com.puntogris.smartwear.domain.model

import com.puntogris.smartwear.domain.model.conditions.Temperature

class Current(
    var temperature: Temperature,
    val description: String,
    val icon: String
)