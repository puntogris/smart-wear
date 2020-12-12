package com.puntogris.whatdoiwear.data

import android.annotation.SuppressLint
import android.content.Context
import android.location.Location
import android.os.Looper
import androidx.lifecycle.LiveData
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LocationLiveData @Inject constructor(context: Context):LiveData<Location>(){

    private val fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)
    private val locationRequest = LocationRequest.create().apply {
        priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        interval = 5000
        fastestInterval = 3000
        smallestDisplacement = 10f
    }

    private val locationCallback: LocationCallback = object : LocationCallback() {
        override fun onLocationResult(locationResult: LocationResult?) {
            locationResult ?: return
            postValue(locationResult.lastLocation)
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