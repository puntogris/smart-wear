package com.puntogris.smartwear.presentation.welcome

import androidx.navigation.fragment.findNavController
import com.puntogris.smartwear.R
import com.puntogris.smartwear.databinding.FragmentWelcomeBinding
import com.puntogris.smartwear.presentation.base.BaseBindingFragment
import com.puntogris.smartwear.utils.SharedPref
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class WelcomeFragment : BaseBindingFragment<FragmentWelcomeBinding>(R.layout.fragment_welcome) {

    @Inject lateinit var sharedPref: SharedPref

    override fun initializeViews() {
        with(binding){
            fragment = this@WelcomeFragment
            viewPager.adapter = IllustrationAdapter()
            dotsIndicator.setViewPager2(viewPager)
        }
    }

    fun onContinueClicked(){
        sharedPref.disableWelcomeScreenPref()
        findNavController().navigate(R.id.weatherFragment)
    }

    override fun onDestroyView() {
        binding.viewPager.adapter = null
        super.onDestroyView()
    }
}