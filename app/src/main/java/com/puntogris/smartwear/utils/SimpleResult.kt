package com.puntogris.smartwear.utils

sealed class SimpleResult {
    object Success : SimpleResult()
    object Failure : SimpleResult()
}