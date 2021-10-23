package com.puntogris.whatdoiwear.ui.weather

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import androidx.fragment.app.viewModels
import com.puntogris.whatdoiwear.R
import com.puntogris.whatdoiwear.databinding.FragmentWeatherBinding
import com.puntogris.whatdoiwear.model.WeatherBodyApi
import com.puntogris.whatdoiwear.ui.base.BaseFragment
import com.puntogris.whatdoiwear.utils.*
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.DelicateCoroutinesApi

@AndroidEntryPoint
@DelicateCoroutinesApi
class WeatherFragment : BaseFragment<FragmentWeatherBinding>(R.layout.fragment_weather) {

    private val viewModel: WeatherViewModel by viewModels()

    override fun initializeViews() {
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel

        subscribeWeatherUi()
    }

    private fun subscribeWeatherUi(){
        launchAndRepeatWithViewLifecycle {
//            viewModel.weatherResult.collect { result ->
//                when (result) {
//                    is WeatherResult.Success -> onSuccess(result.data)
//                    WeatherResult.Error -> onError()
//                    WeatherResult.InProgress -> inProgress()
//                }
//            }
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
     //   viewModel.updateWeather(data)
//        with(binding){
//            weatherProgressBar.gone()
//            bottomSheetLayout.apply {
//                clothingRecommendation.visible()
//                userName.visible()
//                bottomSheetProgressBar.gone()
//            }
//        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        setHasOptionsMenu(true)
        super.onCreate(savedInstanceState)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        menu.findItem(R.id.settings).isVisible = true
        super.onCreateOptionsMenu(menu, inflater)
    }
}