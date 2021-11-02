package com.puntogris.smartwear.domain.model

import com.puntogris.smartwear.data.data_source.Temperature

class Current(
    var temperature: Temperature,
    val description: String,
    val icon: String
)