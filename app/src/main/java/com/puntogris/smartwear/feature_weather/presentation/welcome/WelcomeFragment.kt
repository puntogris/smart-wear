package com.puntogris.smartwear.feature_weather.presentation.welcome

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.puntogris.smartwear.R
import com.puntogris.smartwear.core.utils.viewBinding
import com.puntogris.smartwear.databinding.FragmentWelcomeBinding
import com.puntogris.smartwear.feature_weather.data.data_source.local.SharedPreferences
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class WelcomeFragment : Fragment(R.layout.fragment_welcome) {

    private val binding by viewBinding(FragmentWelcomeBinding::bind)


    @Inject
    lateinit var sharedPreferences: SharedPreferences

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {
            viewPager.adapter = IllustrationAdapter()
            dotsIndicator.setViewPager2(viewPager)
            startButton.setOnClickListener { onContinueClicked() }
        }
    }

    private fun onContinueClicked() {
        sharedPreferences.disableWelcomeScreenPref()
        findNavController().navigate(R.id.weatherFragment)
    }
}
