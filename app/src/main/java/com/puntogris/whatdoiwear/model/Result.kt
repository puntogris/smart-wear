package com.puntogris.whatdoiwear.model

import java.lang.Exception

sealed class Result{
    class Success(val data:WeatherBodyApi): Result()
    class Error(val exception: Exception): Result()
    object InProgress: Result()
}