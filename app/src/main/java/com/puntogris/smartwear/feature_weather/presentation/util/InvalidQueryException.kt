package com.puntogris.smartwear.feature_weather.presentation.util

import com.puntogris.smartwear.R

class InvalidQueryException(val error: Int = R.string.snack_query_required): Exception()