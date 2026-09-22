package com.puntogris.smartwear.presentation.welcome

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.puntogris.smartwear.R
import com.puntogris.smartwear.data.data_source.local.SharedPreferences
import com.puntogris.smartwear.presentation.weather.SmartWearTheme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class WelcomeFragment : Fragment() {
    @Inject lateinit var sharedPreferences: SharedPreferences

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, state: Bundle?): View =
        ComposeView(requireContext()).apply {
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {
                SmartWearTheme {
                    WelcomeScreen(onContinue = ::continueToWeather)
                }
            }
        }

    private fun continueToWeather() {
        sharedPreferences.disableWelcomeScreenPref()
        findNavController().navigate(R.id.weatherFragment)
    }
}
