package com.puntogris.whatdoiwear.ui.main

import androidx.fragment.app.viewModels
import com.puntogris.whatdoiwear.R
import com.puntogris.whatdoiwear.databinding.FragmentMainBinding
import com.puntogris.whatdoiwear.model.WeatherBodyApi
import com.puntogris.whatdoiwear.ui.base.BaseFragment
import com.puntogris.whatdoiwear.utils.*
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
@DelicateCoroutinesApi
@ExperimentalCoroutinesApi
class MainFragment : BaseFragment<FragmentMainBinding>(R.layout.fragment_main) {

    private val viewModel: MainViewModel by viewModels()

    override fun initializeViews() {
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel

        subscribeWeatherUi()
        setBottomSheetBehavior()
        initSeekBar()
    }

    private fun subscribeWeatherUi(){
        launchAndRepeatWithViewLifecycle {
            viewModel.weatherResult.collect { result ->
                when (result) {
                    is WeatherResult.Success -> onSuccess(result.data)
                    WeatherResult.Error -> onError()
                    WeatherResult.InProgress -> inProgress()
                }
            }
        }
    }

    private fun inProgress(){
//        binding.apply {
//            bottomSheetLayout.userName.gone()
//            bottomSheetLayout.bottomSheetProgressBar.visible()
//            weatherProgressBar.visible()
//        }
    }

    private fun onError(){
        createSnackBar(getString(R.string.error_weather_api))
   //     binding.weatherProgressBar.gone()
    }

    private fun onSuccess(data: WeatherBodyApi){
        viewModel.updateWeather(data)
//        with(binding){
//            weatherProgressBar.gone()
//            bottomSheetLayout.apply {
//                clothingRecommendation.visible()
//                userName.visible()
//                bottomSheetProgressBar.gone()
//            }
//        }
    }

    private fun initSeekBar(){
//        binding.seekBar.apply {
//            addOnChangeListener { _, value, _ ->
//                if (viewModel.isOnEndSeekBar(value)) viewModel.enableAnimationPref()
//                viewModel.updateSeekBarPosition((value - valueFrom).toInt()) }
//            setLabelFormatter { viewModel.getSeekBarLabel(it) }
//        }
    }

    private fun setBottomSheetBehavior(){
       // binding.bottomSheetLayout.bottomSheet.setupWith(binding.activityBackground)
    }

    override fun onResume() {
        super.onResume()
        viewModel.updateDate()
    }
}