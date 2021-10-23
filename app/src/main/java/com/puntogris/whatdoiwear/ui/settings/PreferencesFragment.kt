package com.puntogris.whatdoiwear.ui.settings

import android.os.Bundle
import androidx.preference.PreferenceFragmentCompat
import com.puntogris.whatdoiwear.R

class PreferencesFragment : PreferenceFragmentCompat(){

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.preferences, rootKey)
    }
}