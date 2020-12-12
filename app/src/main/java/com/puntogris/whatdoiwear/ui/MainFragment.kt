package com.puntogris.whatdoiwear.ui

import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.puntogris.whatdoiwear.R
import com.puntogris.whatdoiwear.databinding.FragmentMainBinding
import com.puntogris.whatdoiwear.di.injector
import com.puntogris.whatdoiwear.utils.gone
import com.puntogris.whatdoiwear.utils.visible

class MainFragment : BaseFragment<FragmentMainBinding>(R.layout.fragment_main) {

    private val viewModel by viewModel {  injector.mainViewModel}
    private val sharedPref by lazy { injector.sharedPreferences }

    override fun initializeViews() {
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        checkAnimationPref()

        binding.seekBar.apply {
            addOnChangeListener { slider, value, _ ->
                if(slider.valueTo == value)  if (!sharedPref.getShowAnimationPref()) {
                    binding.bottomSheetLayout.animationView.visible()
                    sharedPref.setShowAnimationPref()
                }
                viewModel.updateSeekBarPosition(value.toInt()) }
            setLabelFormatter { viewModel.getSeekBarLabel(it) }
        }

        val bottomSheet = binding.bottomSheetLayout.bottomSheet

        BottomSheetBehavior.from(bottomSheet).apply {
            state = BottomSheetBehavior.STATE_COLLAPSED
            bottomSheet.setOnClickListener { state = BottomSheetBehavior.STATE_EXPANDED }
            binding.activityBackground.setOnClickListener { state = BottomSheetBehavior.STATE_COLLAPSED }
        }

        viewModel.weatherBody.observe(viewLifecycleOwner, {
            binding.bottomSheetLayout.apply {
                clothingRecommendation.visible()
                userName.visible()
                progressBar.gone()
            }
        })
    }

    private fun checkAnimationPref(){
        if (sharedPref.getShowAnimationPref()) binding.bottomSheetLayout.animationView.visible()
    }

    override fun onResume() {
        super.onResume()
        viewModel.updateDate()
    }

}



