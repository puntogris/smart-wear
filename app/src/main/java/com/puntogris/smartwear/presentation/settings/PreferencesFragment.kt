package com.puntogris.smartwear.presentation.settings

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import com.google.android.gms.oss.licenses.OssLicensesMenuActivity
import com.puntogris.smartwear.BuildConfig
import com.puntogris.smartwear.R
import com.puntogris.smartwear.data.data_source.local.SharedPreferences
import com.puntogris.smartwear.presentation.weather.SmartWearTheme
import com.puntogris.smartwear.utils.ThemeManager
import com.puntogris.smartwear.utils.constants.Keys
import com.puntogris.smartwear.utils.launchWebBrowserIntent
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class PreferencesFragment : Fragment() {
    @Inject lateinit var themeManager: ThemeManager
    @Inject lateinit var preferences: SharedPreferences

    private var selectedTheme by mutableStateOf(ThemeManager.LIGHT)
    private var selectedUnits by mutableStateOf("metric")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        selectedTheme = preferences.appTheme() ?: ThemeManager.LIGHT
        selectedUnits = preferences.weatherUnits() ?: "metric"
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, state: Bundle?): View =
        ComposeView(requireContext()).apply {
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {
                SmartWearTheme {
                    SettingsScreen(
                        selectedTheme = selectedTheme,
                        selectedUnits = selectedUnits,
                        version = "${BuildConfig.VERSION_NAME} (${BuildConfig.VERSION_CODE})",
                        onThemeSelected = ::selectTheme,
                        onUnitsSelected = ::selectUnits,
                        onRateApp = ::rateApp,
                        onVersionClick = preferences::enableShowAnimationPref,
                        onWeatherDataClick = { launchWebBrowserIntent(getString(R.string.open_meteo_url)) },
                        onLicensesClick = ::openLicenses,
                        onPrivacyClick = { launchWebBrowserIntent(getString(R.string.pref_privacy_policy_url)) },
                        onTermsClick = { launchWebBrowserIntent(getString(R.string.pref_terms_and_conditions_url)) }
                    )
                }
            }
        }

    private fun selectTheme(value: String) {
        selectedTheme = value
        preferences.updateTheme(value)
        themeManager.applyTheme(value)
    }

    private fun selectUnits(value: String) {
        selectedUnits = value
        preferences.updateWeatherUnits(value)
        setFragmentResult(Keys.DATA, bundleOf(Keys.UNITS_RESULT to true))
    }

    private fun rateApp() {
        val packageName = BuildConfig.APPLICATION_ID
        runCatching {
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=$packageName")))
        }.onFailure {
            launchWebBrowserIntent("https://play.google.com/store/apps/details?id=$packageName")
        }
    }

    private fun openLicenses() {
        OssLicensesMenuActivity.setActivityTitle(getString(R.string.open_source_licenses))
        startActivity(Intent(requireContext(), OssLicensesMenuActivity::class.java))
    }
}
