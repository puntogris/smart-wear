package com.puntogris.whatdoiwear.utils

import android.location.Address
import android.view.View
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar

fun View.visible(){
    visibility = View.VISIBLE
}

fun View.gone(){
    visibility = View.GONE
}

fun Fragment.createSnackBar(text: String){
    val snackLayout = this.requireActivity().findViewById<View>(android.R.id.content)
    Snackbar.make(snackLayout, text, Snackbar.LENGTH_LONG).show()
}

fun Address.getLocationName():String{
    var locationName = ""
    if (locality != null) {
        locationName += locality
        if (adminArea != null) locationName += ", $adminArea"
    }else if (adminArea != null) locationName += adminArea
    return locationName
}