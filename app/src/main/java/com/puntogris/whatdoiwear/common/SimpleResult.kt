package com.puntogris.whatdoiwear.common

sealed class SimpleResult{
    object Success: SimpleResult()
    object Failure: SimpleResult()
}