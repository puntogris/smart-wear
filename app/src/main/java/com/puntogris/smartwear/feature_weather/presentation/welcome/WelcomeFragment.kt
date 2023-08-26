package com.puntogris.smartwear.feature_weather.presentation.welcome

import androidx.navigation.fragment.findNavController
import com.puntogris.smartwear.R
import com.puntogris.smartwear.databinding.FragmentWelcomeBinding
import com.puntogris.smartwear.core.presentation.base.BaseBindingFragment
import com.puntogris.smartwear.feature_weather.data.data_source.local.SharedPreferences
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class WelcomeFragment : BaseBindingFragment<FragmentWelcomeBinding>(R.layout.fragment_welcome) {

    @Inject
    lateinit var sharedPreferences: SharedPreferences

    override fun initializeViews() {
        with(binding) {
            fragment = this@WelcomeFragment
            viewPager.adapter = IllustrationAdapter()
            dotsIndicator.setViewPager2(viewPager)
        }
    }

    fun onContinueClicked() {
        sharedPreferences.disableWelcomeScreenPref()
        findNavController().navigate(R.id.weatherFragment)
    }

    override fun onDestroyView() {
        binding.viewPager.adapter = null
        super.onDestroyView()
    }
}