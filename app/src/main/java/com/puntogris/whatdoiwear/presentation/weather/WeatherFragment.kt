package com.puntogris.whatdoiwear.presentation.weather

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.puntogris.whatdoiwear.R
import com.puntogris.whatdoiwear.common.SimpleResult
import com.puntogris.whatdoiwear.common.WeatherResult
import com.puntogris.whatdoiwear.databinding.FragmentWeatherBinding
import com.puntogris.whatdoiwear.data.data_source.remote.dto.WeatherDto
import com.puntogris.whatdoiwear.presentation.base.BaseFragment
import com.puntogris.whatdoiwear.utils.*
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.launch

@AndroidEntryPoint
@DelicateCoroutinesApi
class WeatherFragment : BaseFragment<FragmentWeatherBinding>(R.layout.fragment_weather) {

    private val viewModel: WeatherViewModel by viewModels()

    override fun initializeViews() {
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel

        subscribeWeatherUi()
        subscribeRefreshUi()

    }

    private fun subscribeWeatherUi(){
        launchAndRepeatWithViewLifecycle {
            viewModel.weather.observe(viewLifecycleOwner){
                when(it){
                    is WeatherResult.Success -> onSuccess(it.data)
                    WeatherResult.Error -> onError()
                    WeatherResult.InProgress -> inProgress()
                }
            }
//            viewModel.weatherResult.collect { result ->
//                when (result) {
//                    is WeatherResult.kt.Success -> onSuccess(result.data)
//                    WeatherResult.kt.Error -> onError()
//                    WeatherResult.kt.InProgress -> inProgress()
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

    private fun onSuccess(data: WeatherDto){
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

    private fun subscribeRefreshUi(){
        binding.swipeRefreshLayout.apply {
            setOnRefreshListener {
                lifecycleScope.launch {
                    val message = when(viewModel.onRefreshLocation()){
                        SimpleResult.Failure -> "Error"
                        SimpleResult.Success -> "Success"
                    }
                    createSnackBar(message)
                    isRefreshing = false
                }
            }
        }
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