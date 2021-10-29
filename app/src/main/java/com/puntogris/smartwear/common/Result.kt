package com.puntogris.smartwear.common

sealed class Result<out T : Any> {
    object Loading: Result<Nothing>()
    data class Success<out T : Any>(val data: T) : Result<T>()
    data class Failure(val exception: Exception) : Result<Nothing>()
}

