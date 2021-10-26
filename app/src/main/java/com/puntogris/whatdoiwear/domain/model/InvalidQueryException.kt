package com.puntogris.whatdoiwear.domain.model

import com.puntogris.whatdoiwear.R

class InvalidQueryException(val error: Int = R.string.snack_query_required): Exception()