package com.puntogris.whatdoiwear.data

import android.annotation.SuppressLint
import android.content.Context
import android.location.Geocoder
import android.location.Location
import android.os.Looper
import androidx.lifecycle.LiveData
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.puntogris.whatdoiwear.model.LastLocation
import dagger.hilt.android.qualifiers.ApplicationContext
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.coroutines.suspendCoroutine

@Singleton
class LocationLiveData @Inject constructor(@ApplicationContext context: Context):LiveData<LastLocation>(){

    private val fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)
    private val locationRequest = LocationRequest.create().apply {
        priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        interval = 3000
        fastestInterval = 3000
        smallestDisplacement = 10f
    }

    private val locationCallback: LocationCallback = object : LocationCallback() {
        override fun onLocationResult(locationResult: LocationResult?) {
            locationResult?.let {
                val location = LastLocation(latitude = it.lastLocation.latitude, longitude = it.lastLocation.longitude)
                postValue(location)
            }
        }
    }

    override fun onActive() {
        startLocationUpdates()
        super.onActive()
    }

    override fun onInactive() {
        stopLocationUpdates()
        super.onInactive()
    }

    @SuppressLint("MissingPermission")
    private fun startLocationUpdates() {
        fusedLocationClient.requestLocationUpdates(locationRequest,
            locationCallback,
            Looper.getMainLooper())
    }

    private fun stopLocationUpdates() {
        fusedLocationClient.removeLocationUpdates(locationCallback)
    }

}