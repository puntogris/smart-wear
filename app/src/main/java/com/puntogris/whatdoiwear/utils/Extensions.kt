package com.puntogris.whatdoiwear.utils

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