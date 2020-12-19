package com.puntogris.whatdoiwear.utils

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.app.ActivityCompat.requestPermissions
import androidx.core.app.ActivityCompat.shouldShowRequestPermissionRationale
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.puntogris.whatdoiwear.R
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class PermissionsManager @Inject constructor( @ApplicationContext private val context: Context,
) {

    fun hasPermission(): Boolean{
        if (Manifest.permission.ACCESS_FINE_LOCATION == Manifest.permission.ACCESS_BACKGROUND_LOCATION &&
            android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.Q) {
            return true
        }
        return ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) ==
                PackageManager.PERMISSION_GRANTED
    }

    fun requestPermission(fragment: Fragment){
        val requestPermissionLauncher =
            fragment.registerForActivityResult(
                ActivityResultContracts.RequestPermission()
            ) { isGranted: Boolean ->
                if (isGranted) fragment.findNavController().navigate(R.id.mainFragment)
                else fragment.createSnackBar(context.resources.getString(R.string.location_rationale_message), Snackbar.LENGTH_SHORT)
            }

        if (hasPermission()) fragment.findNavController().navigate(R.id.mainFragment)
        else requestPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)

    }

}