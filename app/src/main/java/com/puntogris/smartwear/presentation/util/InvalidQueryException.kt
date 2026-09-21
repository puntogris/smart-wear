package com.puntogris.smartwear.presentation.util

import com.puntogris.smartwear.R

class InvalidQueryException(val error: Int = R.string.snack_query_required) : Exception()