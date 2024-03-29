package com.puntogris.smartwear.feature_weather.presentation.settings

import android.content.Intent
import android.os.Bundle
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import androidx.preference.ListPreference
import androidx.preference.PreferenceFragmentCompat
import com.google.android.gms.oss.licenses.OssLicensesMenuActivity
import com.puntogris.smartwear.BuildConfig
import com.puntogris.smartwear.R
import com.puntogris.smartwear.core.utils.ThemeManager
import com.puntogris.smartwear.core.utils.constants.Keys
import com.puntogris.smartwear.core.utils.onClick
import com.puntogris.smartwear.core.utils.preference
import com.puntogris.smartwear.core.utils.preferenceChange
import com.puntogris.smartwear.core.utils.preferenceOnClick
import com.puntogris.smartwear.feature_weather.data.data_source.local.enableShowAnimationPref
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class PreferencesFragment : PreferenceFragmentCompat() {

    @Inject
    lateinit var themeManager: ThemeManager

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.preferences, rootKey)

        preference(Keys.VERSION) {
            summary = "${BuildConfig.VERSION_NAME} (${BuildConfig.VERSION_CODE})"
            onClick {
                sharedPreferences?.enableShowAnimationPref()
            }
        }

        preferenceOnClick(Keys.LICENSES) {
            Intent(requireContext(), OssLicensesMenuActivity::class.java).apply {
                OssLicensesMenuActivity.setActivityTitle(getString(R.string.open_source_licenses))
                startActivity(this)
            }
        }

        preferenceChange<ListPreference>(Keys.WEATHER_UNITS) {
            setFragmentResult(Keys.DATA, bundleOf(Keys.UNITS_RESULT to true))
        }

        preferenceChange<ListPreference>(Keys.THEME) {
            themeManager.applyTheme(it as? String)
        }
    }
}