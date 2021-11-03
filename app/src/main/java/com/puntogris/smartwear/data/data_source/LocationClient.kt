package com.puntogris.smartwear.data.data_source

import android.annotation.SuppressLint
import android.content.Context
import android.location.Location
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import com.google.android.gms.tasks.CancellationTokenSource
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

@SuppressLint("MissingPermission")
class LocationClient @Inject constructor(@ApplicationContext context: Context) {

    private val fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)
    private val cancellationToken = CancellationTokenSource().token

    suspend fun requestLocation(): Location = getLastLocation() ?: getCurrentLocation()

    private suspend fun getLastLocation() = fusedLocationClient.lastLocation.await()

    private suspend fun getCurrentLocation(): Location {
        return fusedLocationClient.getCurrentLocation(
            LocationRequest.PRIORITY_HIGH_ACCURACY,
            cancellationToken
        ).await()
    }
}