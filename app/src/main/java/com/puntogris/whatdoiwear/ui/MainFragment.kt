package com.puntogris.whatdoiwear.ui

import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.puntogris.whatdoiwear.R
import com.puntogris.whatdoiwear.databinding.FragmentMainBinding
import com.puntogris.whatdoiwear.model.Result
import com.puntogris.whatdoiwear.model.WeatherBodyApi
import com.puntogris.whatdoiwear.utils.MySharedPreferences
import com.puntogris.whatdoiwear.utils.createSnackBar
import com.puntogris.whatdoiwear.utils.gone
import com.puntogris.whatdoiwear.utils.visible
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MainFragment : BaseFragment<FragmentMainBinding>(R.layout.fragment_main) {

    private val viewModel:MainFragmentViewModel by viewModels()

    @Inject lateinit var sharedPref: MySharedPreferences

    override fun initializeViews() {
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        checkAnimationPref()
        setBottomSheetBehavior()
        initSeekBar()

        lifecycleScope.launch {
            viewModel.weatherResult.collect { result ->
                when (result) {
                    is Result.Success -> onSuccess(result.data)
                    is Result.Error -> onError()
                    Result.InProgress -> onInProgress()
                }
            }
        }
    }

    private fun onInProgress(){
        binding.apply {
            bottomSheetLayout.userName.gone()
            bottomSheetLayout.bottomSheetProgressBar.visible()
            weatherProgressBar.visible()
        }
    }

    private fun onError(){
        createSnackBar(getString(R.string.error_weather_api))
        binding.weatherProgressBar.gone()
    }

    private fun onSuccess(data: WeatherBodyApi){
        binding.bottomSheetLayout.userName.text = sharedPref.getUsernamePref()
        viewModel.updateWeather(data)
        binding.weatherProgressBar.gone()
        binding.bottomSheetLayout.apply {
            clothingRecommendation.visible()
            userName.visible()
            bottomSheetProgressBar.gone()
        }
    }

    private fun initSeekBar(){
        binding.seekBar.apply {
            addOnChangeListener { slider, value, _ ->
                if(slider.valueTo == value && !sharedPref.getShowAnimationPref()) {
                    binding.bottomSheetLayout.animationView.visible()
                    sharedPref.setShowAnimationPref()
                }
                viewModel.updateSeekBarPosition((value - valueFrom).toInt()) }
            setLabelFormatter { viewModel.getSeekBarLabel(it) }
        }
    }

    private fun setBottomSheetBehavior(){
        val bottomSheet = binding.bottomSheetLayout.bottomSheet
        BottomSheetBehavior.from(bottomSheet).apply {
            state = BottomSheetBehavior.STATE_COLLAPSED
            bottomSheet.setOnClickListener { state = BottomSheetBehavior.STATE_EXPANDED }
            binding.activityBackground.setOnClickListener { state = BottomSheetBehavior.STATE_COLLAPSED }
        }
    }

    private fun checkAnimationPref(){
        if (sharedPref.getShowAnimationPref()) binding.bottomSheetLayout.animationView.visible()
    }

    override fun onResume() {
        super.onResume()
        viewModel.updateDate()
    }
}