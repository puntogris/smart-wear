package com.puntogris.whatdoiwear.presentation.settings

import android.content.Intent
import android.os.Bundle
import androidx.preference.PreferenceFragmentCompat
import com.google.android.gms.oss.licenses.OssLicensesMenuActivity
import com.puntogris.whatdoiwear.BuildConfig
import com.puntogris.whatdoiwear.R
import com.puntogris.whatdoiwear.utils.*
import com.puntogris.whatdoiwear.common.constants.PreferencesKeys
import com.puntogris.whatdoiwear.common.onClick
import com.puntogris.whatdoiwear.common.preference
import com.puntogris.whatdoiwear.common.preferenceOnClick
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class PreferencesFragment : PreferenceFragmentCompat(){

    @Inject lateinit var sharedPref: SharedPref

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.preferences, rootKey)

        preference(PreferencesKeys.VERSION){
            summary = "${BuildConfig.VERSION_NAME} (${BuildConfig.VERSION_CODE})"
            onClick {
                sharedPref.enableShowAnimationPref()
            }
        }

        preferenceOnClick(PreferencesKeys.LICENSES){
            Intent(requireContext(), OssLicensesMenuActivity::class.java).apply {
                OssLicensesMenuActivity.setActivityTitle(getString(R.string.open_source_licenses))
                startActivity(this)
            }
        }
    }
}