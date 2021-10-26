package com.puntogris.whatdoiwear.data.data_source

import android.annotation.SuppressLint
import android.content.Context
import android.location.Location
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import com.google.android.gms.tasks.CancellationToken
import com.google.android.gms.tasks.CancellationTokenSource
import com.google.android.gms.tasks.OnTokenCanceledListener
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import kotlin.coroutines.cancellation.CancellationException

class LocationClient @Inject constructor(@ApplicationContext context: Context) {

    private val fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)
    private val cancellationToken = CancellationTokenSource().token

    @SuppressLint("MissingPermission")
    suspend fun requestLocation(): Location {
        return fusedLocationClient.getCurrentLocation(
            LocationRequest.PRIORITY_HIGH_ACCURACY,
            cancellationToken
        ).await()
    }
}