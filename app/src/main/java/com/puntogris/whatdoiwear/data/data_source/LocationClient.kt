package com.puntogris.whatdoiwear.data.data_source

import android.annotation.SuppressLint
import android.content.Context
import android.location.Geocoder
import android.location.Location
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import com.google.android.gms.tasks.CancellationToken
import com.google.android.gms.tasks.OnTokenCanceledListener
import com.puntogris.whatdoiwear.domain.model.LastLocation
import com.puntogris.whatdoiwear.utils.getLocationName
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.*
import kotlinx.coroutines.tasks.await
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
@DelicateCoroutinesApi
class LocationClient @Inject constructor(
    @ApplicationContext private val context: Context
    ){

    private val fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)

    @SuppressLint("MissingPermission")
    suspend fun requestLocation(): LastLocation {
        val location = fusedLocationClient.getCurrentLocation(LocationRequest.PRIORITY_HIGH_ACCURACY, object : CancellationToken(){
            override fun isCancellationRequested(): Boolean {
                return false
            }

            override fun onCanceledRequested(p0: OnTokenCanceledListener): CancellationToken {
                return this
            }
        }).await()

        val lastLocation = LastLocation.from(location)
        lastLocation.name = getLocationName(location)

        return lastLocation
    }

    private suspend fun getLocationName(location: Location): String {
        val gcd = Geocoder(context, Locale.getDefault())

        val addresses = withContext(Dispatchers.IO){
            gcd.getFromLocation(location.latitude, location.longitude, 1)
        }

        return  if(!addresses.isNullOrEmpty()) addresses[0].getLocationName() else "Error"
    }
}