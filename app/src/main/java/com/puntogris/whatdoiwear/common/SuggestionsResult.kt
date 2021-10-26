package com.puntogris.whatdoiwear.common

import com.puntogris.whatdoiwear.domain.model.Location

sealed class SuggestionsResult{
    object InProgress: SuggestionsResult()
    class Success(val data: List<Location>): SuggestionsResult()
    object Failure: SuggestionsResult()
}