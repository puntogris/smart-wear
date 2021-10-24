package com.puntogris.whatdoiwear.utils

import android.Manifest
import android.app.Activity
import android.content.pm.PackageManager
import android.location.Address
import android.view.View
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.NavHostFragment
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
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

fun EditText.getString() = text.toString()

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

fun Activity.hasLocationPermission(): Boolean{
    if (Manifest.permission.ACCESS_FINE_LOCATION == Manifest.permission.ACCESS_BACKGROUND_LOCATION &&
        android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.Q) {
        return true
    }
    return ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) ==
            PackageManager.PERMISSION_GRANTED
}

inline fun PreferenceFragmentCompat.preference(key: String, block: Preference.() -> Unit){
    findPreference<Preference>(key)?.apply {
        block(this)
    }
}


inline fun Preference.onClick(crossinline block: () -> Unit){
    setOnPreferenceClickListener {
        block()
        true
    }
}

inline fun PreferenceFragmentCompat.preferenceOnClick(key: String, crossinline block: () -> Unit){
    findPreference<Preference>(key)?.setOnPreferenceClickListener {
        block()
        true
    }
}
