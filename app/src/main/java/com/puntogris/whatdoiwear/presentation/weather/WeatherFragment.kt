package com.puntogris.whatdoiwear.presentation.weather

import android.annotation.SuppressLint
import android.app.SearchManager
import android.database.MatrixCursor
import android.os.Bundle
import android.provider.BaseColumns
import android.view.Menu
import android.view.MenuInflater
import android.widget.ArrayAdapter
import android.widget.CursorAdapter
import androidx.cursoradapter.widget.SimpleCursorAdapter
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.puntogris.whatdoiwear.R
import com.puntogris.whatdoiwear.common.*
import com.puntogris.whatdoiwear.databinding.FragmentWeatherBinding
import com.puntogris.whatdoiwear.data.data_source.remote.dto.WeatherDto
import com.puntogris.whatdoiwear.domain.model.Location
import com.puntogris.whatdoiwear.presentation.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.launch

@AndroidEntryPoint
@DelicateCoroutinesApi
class WeatherFragment : BaseFragment<FragmentWeatherBinding>(R.layout.fragment_weather) {

    private val viewModel: WeatherViewModel by viewModels()
    private lateinit var suggestionsAdapter: SuggestionsAdapter

    override fun initializeViews() {
        binding.fragment = this
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel

        subscribeWeatherUi()
        subscribeRefreshUi()
        subscribeSearchView()
        subscribeGeocodingLocations()
    }

    private fun subscribeSearchView(){
        suggestionsAdapter = SuggestionsAdapter(::onSuggestionClicked)
        binding.suggestionsList.adapter = suggestionsAdapter

    }

    private fun onSuggestionClicked(location: Location){
        viewModel.insert(location)
        binding.suggestionsList.gone()
    }

    fun onSearchLocationClicked(){
        binding.searchButton.gone()
        binding.progressBar.visible()
        binding.searchInput.getString().let {
            if (it.isNotBlank()) viewModel.setQuery(it)
            else createSnackBar("Can't be empty.")
        }
    }


    private fun subscribeWeatherUi(){
        viewModel.weather.observe(viewLifecycleOwner){
            when(it){
                is WeatherResult.Success -> onSuccess(it.data)
                WeatherResult.Error -> onError()
                WeatherResult.InProgress -> inProgress()
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

    private fun subscribeGeocodingLocations(){
        viewModel.geocodingLocations.observe(viewLifecycleOwner){
            suggestionsAdapter.updateSuggestions(it)
        }
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