package com.puntogris.whatdoiwear.ui

import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.google.android.material.snackbar.Snackbar
import com.puntogris.whatdoiwear.R
import com.puntogris.whatdoiwear.databinding.FragmentMainBinding
import com.puntogris.whatdoiwear.model.WeatherBodyApi
import com.puntogris.whatdoiwear.utils.*
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import javax.inject.Inject

@AndroidEntryPoint
@ExperimentalCoroutinesApi
class MainFragment : BaseFragment<FragmentMainBinding>(R.layout.fragment_main) {

    private val viewModel:MainFragmentViewModel by viewModels()
    @Inject lateinit var sharedPref: SharedPref

    override fun initializeViews() {
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel
        checkAnimationPref()
        setBottomSheetBehavior()
        initSeekBar()

        lifecycleScope.launchWhenStarted {
            viewModel.weatherResult.collect { result ->
                when (result) {
                    is WeatherResult.Success -> onSuccess(result.data)
                    is WeatherResult.Error -> onError()
                    WeatherResult.InProgress -> onInProgress()
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
        createSnackBar(getString(R.string.error_weather_api), Snackbar.LENGTH_SHORT)
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
            addOnChangeListener { _, value, _ ->
                if(viewModel.isOnEndSeekBar(value) && !sharedPref.getShowAnimationPref()) {
                    enableAnimation()
                }
                viewModel.updateSeekBarPosition((value - valueFrom).toInt()) }
            setLabelFormatter { viewModel.getSeekBarLabel(it) }
        }
    }

    private fun enableAnimation(){
        binding.bottomSheetLayout.animationView.visible()
        sharedPref.setShowAnimationPref()
    }

    private fun setBottomSheetBehavior(){
        binding.bottomSheetLayout.bottomSheet.setupWith(binding.activityBackground)
    }

    private fun checkAnimationPref(){
        if (sharedPref.getShowAnimationPref()) binding.bottomSheetLayout.animationView.visible()
    }

    override fun onResume() {
        super.onResume()
        viewModel.updateDate()
    }
}