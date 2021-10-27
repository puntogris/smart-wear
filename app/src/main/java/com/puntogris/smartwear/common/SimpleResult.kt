package com.puntogris.smartwear.common

sealed class SimpleResult{
    object Success: SimpleResult()
    object Failure: SimpleResult()
}