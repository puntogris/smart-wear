package com.puntogris.whatdoiwear.utils

import android.location.Address
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import com.google.android.material.snackbar.Snackbar
import com.puntogris.whatdoiwear.model.LastLocation

fun View.visible(){
    visibility = View.VISIBLE
}

fun View.gone(){
    visibility = View.GONE
}

fun Fragment.createSnackBar(text: String, duration: Int){
    val snackLayout = this.requireActivity().findViewById<View>(android.R.id.content)
    Snackbar.make(snackLayout, text,duration).show()
}

fun Address.getLocationName():String{
    var locationName = ""
    if (locality != null) {
        locationName += locality
        if (adminArea != null) locationName += ", $adminArea"
    }else if (adminArea != null) locationName += adminArea
    return locationName
}
