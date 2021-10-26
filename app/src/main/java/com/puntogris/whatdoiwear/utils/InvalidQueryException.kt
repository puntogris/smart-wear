package com.puntogris.whatdoiwear.utils

import com.puntogris.whatdoiwear.R

class InvalidQueryException(val error: Int = R.string.snack_query_required): Exception()