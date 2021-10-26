package com.puntogris.whatdoiwear.presentation.weather

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import com.puntogris.whatdoiwear.R
import com.puntogris.whatdoiwear.common.*
import com.puntogris.whatdoiwear.data.data_source.remote.dto.WeatherDto
import com.puntogris.whatdoiwear.databinding.FragmentWeatherBinding
import com.puntogris.whatdoiwear.domain.model.Location
import com.puntogris.whatdoiwear.presentation.base.BaseBindingFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class WeatherFragment : BaseBindingFragment<FragmentWeatherBinding>(R.layout.fragment_weather) {

    private val viewModel: WeatherViewModel by viewModels()

    override fun initializeViews() {
        binding.fragment = this
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel

        subscribeWeatherUi()
        subscribeRefreshUi()
        setupSearchLocationsUi()

        viewModel.currentLocation.observe(viewLifecycleOwner){
            if (it != null) binding.searchInput.setText(it.name)
        }
    }

    private fun setupSearchLocationsUi(){
        with(binding){
            searchInput.addTextChangedListener { input ->
                if (input.toString().isBlank()) searchSuggestions.gone()
            }
            searchInput.onSearch {
                hideKeyboard()
                onSearchLocationClicked()
            }
            SuggestionsAdapter(::onSuggestionClicked).let {
                searchSuggestions.adapter = it
                subscribeSearchSuggestions(it)
            }
        }
    }

    private fun subscribeSearchSuggestions(adapter: SuggestionsAdapter){
        launchAndRepeatWithViewLifecycle(Lifecycle.State.CREATED){
            viewModel.locationResult.collect { result ->
                when(result){
                    is LocationResult.Error -> {
                        createSnackBar(getString(result.error))
                    }
                    is LocationResult.Success.GetLocations -> {
                        adapter.updateSuggestions(result.data)
                    }
                    LocationResult.Success.UpdateLocation -> {
                        createSnackBar(getString(R.string.snack_location_updated_success))
                    }
                }
                hideKeyboard()
            }
        }
    }

    private fun onSuggestionClicked(location: Location){
        viewModel.insert(location)
        binding.searchSuggestions.gone()
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

    fun onSearchLocationClicked(){
        viewModel.getLocationSuggestions(binding.searchInput.getString())
    }

    fun useCurrentLocation(){
        viewModel.getCurrentLocation()
    }

    private fun inProgress(){

    }

    private fun onError(){
        createSnackBar(getString(R.string.snack_connection_error))
    }

    private fun onSuccess(data: WeatherDto){

    }

    private fun subscribeRefreshUi(){
        binding.swipeRefreshLayout.apply {
            setOnRefreshListener {

                isRefreshing = false
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