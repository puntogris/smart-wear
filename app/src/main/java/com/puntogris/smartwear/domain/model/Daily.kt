package com.puntogris.smartwear.domain.model

import com.puntogris.smartwear.data.data_source.Temperature

class Daily(
    val min: Temperature,
    val max: Temperature
)