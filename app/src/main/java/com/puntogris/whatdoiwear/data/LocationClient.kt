package com.puntogris.whatdoiwear.data

import android.annotation.SuppressLint
import android.content.Context
import android.location.Geocoder
import android.os.Looper
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.puntogris.whatdoiwear.model.LastLocation
import com.puntogris.whatdoiwear.utils.getLocationName
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.ProducerScope
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
@DelicateCoroutinesApi
@ExperimentalCoroutinesApi
class LocationClient @Inject constructor(@ApplicationContext private val context: Context){

    private val fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)
    private val locationRequest = LocationRequest.create().apply {
        priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        interval = 3000
        fastestInterval = 3000
        smallestDisplacement = 10f
    }

    @SuppressLint("MissingPermission")
    fun requestLocation(): Flow<LastLocation> = callbackFlow {

        val locationCallback = getLocationCallback(this)

        fusedLocationClient.requestLocationUpdates(locationRequest,
            locationCallback,
            Looper.getMainLooper()).addOnFailureListener { close(it) }

        awaitClose { fusedLocationClient.removeLocationUpdates(locationCallback) }
    }

    private fun getLocationCallback(producerScope: ProducerScope<LastLocation>): LocationCallback{
        return object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult?) {
                LastLocation.from(locationResult)?.let {
                    GlobalScope.launch(Dispatchers.IO){
                        it.name = getLocationNameAsync(it).await()
                        producerScope.trySend(it)
                    }
                }
            }
        }
    }


    fun getLocationNameAsync(location: LastLocation) : Deferred<String> {
        return GlobalScope.async(Dispatchers.IO) {
            val gcd = Geocoder(context, Locale.getDefault())
            val addresses = gcd.getFromLocation(location.latitude,
                location.longitude, 1)

            return@async if(!addresses.isNullOrEmpty()) addresses[0].getLocationName() else "Error"
        }
    }
}