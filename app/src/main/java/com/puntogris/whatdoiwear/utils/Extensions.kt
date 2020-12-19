package com.puntogris.whatdoiwear.utils

import android.location.Address
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.snackbar.Snackbar
import com.puntogris.whatdoiwear.model.LastLocation
import java.util.*

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

fun MutableLiveData<Date>.update(){
    value = Date()
}

fun ConstraintLayout.setupWith(parentLayout: ConstraintLayout){
    BottomSheetBehavior.from(this).apply {
        state = BottomSheetBehavior.STATE_COLLAPSED
        setOnClickListener { state = BottomSheetBehavior.STATE_EXPANDED }
        parentLayout.setOnClickListener { state = BottomSheetBehavior.STATE_COLLAPSED }
    }
}