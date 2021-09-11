package com.puntogris.whatdoiwear.utils

import android.location.Address
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.NavHostFragment
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.snackbar.Snackbar
import com.puntogris.whatdoiwear.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import java.util.*

fun View.visible(){
    visibility = View.VISIBLE
}

fun View.gone(){
    visibility = View.GONE
}

fun Fragment.createSnackBar(text: String, duration: Int = Snackbar.LENGTH_LONG){
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

fun AppCompatActivity.getNavController() = getNavHostFragment().navController

fun AppCompatActivity.getNavHostFragment() =
    (supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment)

inline fun Fragment.launchAndRepeatWithViewLifecycle(
    minActiveState: Lifecycle.State = Lifecycle.State.STARTED,
    crossinline block: suspend CoroutineScope.() -> Unit
) {
    viewLifecycleOwner.lifecycleScope.launch {
        viewLifecycleOwner.lifecycle.repeatOnLifecycle(minActiveState) {
            block()
        }
    }
}
