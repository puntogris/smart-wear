package com.puntogris.smartwear.core.utils

sealed class SimpleResult{
    object Success: SimpleResult()
    object Failure: SimpleResult()
}