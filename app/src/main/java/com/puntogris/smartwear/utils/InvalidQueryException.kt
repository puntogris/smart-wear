package com.puntogris.smartwear.utils

import com.puntogris.smartwear.R

class InvalidQueryException(val error: Int = R.string.snack_query_required): Exception()