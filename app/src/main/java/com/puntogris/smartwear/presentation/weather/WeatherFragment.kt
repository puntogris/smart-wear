package com.puntogris.smartwear.presentation.weather

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.findNavController
import com.puntogris.smartwear.R
import com.puntogris.smartwear.common.*
import com.puntogris.smartwear.common.constants.Keys
import com.puntogris.smartwear.databinding.FragmentWeatherBinding
import com.puntogris.smartwear.domain.model.Location
import com.puntogris.smartwear.domain.model.Weather
import com.puntogris.smartwear.presentation.base.BaseBindingFragment
import com.puntogris.smartwear.utils.EmptyLocationException
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
        subscribeFragmentResults()
        setupSearchLocationsUi()
    }

    private fun setupSearchLocationsUi() {
        with(binding) {
            searchInput.addTextChangedListener { input ->
                if (input.toString().isBlank()) suggestionsLayout.gone()
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

    private fun subscribeSearchSuggestions(adapter: SuggestionsAdapter) {
        launchAndRepeatWithViewLifecycle(Lifecycle.State.STARTED) {
            viewModel.locationResult.collect { result ->
                when (result) {
                    is LocationResult.Error -> {
                        createSnackBar(result.error)
                    }
                    is LocationResult.Success.GetLocations -> {
                        adapter.updateSuggestions(result.data)
                    }
                    is LocationResult.Success.UpdateLocation -> {
                        createSnackBar(R.string.snack_location_updated_success)
                    }
                }

                with(binding) {
                    suggestionsLayout.isVisible = result is LocationResult.Success.GetLocations
                    progressBar.isVisible = result is LocationResult.Loading
                    searchButton.isVisible = result !is LocationResult.Loading
                }

                hideKeyboard()
            }
        }
    }

    private fun onSuggestionClicked(location: Location) {
        viewModel.insert(location)
        binding.suggestionsLayout.gone()
        binding.searchInput.clearFocus()
    }

    private fun subscribeWeatherUi() {
        launchAndRepeatWithViewLifecycle {
            viewModel.weatherResult.collect {
                when (it) {
                    is Result.Success -> onSuccess(it.data)
                    is Result.Failure -> onError(it.exception)
                    is Result.Loading -> inProgress()
                }
            }
        }
    }

    fun onSearchLocationClicked() {
        viewModel.getLocationSuggestions(binding.searchInput.getString())
    }

    fun useCurrentLocation() {
        if (hasLocationPermission()) {
            viewModel.updateCurrentLocation()
        } else {
            findNavController().navigate(R.id.locationFragment)
        }
    }

    private fun inProgress() {
        binding.swipeRefreshLayout.isRefreshing = true
    }

    private fun onError(exception: Exception) {
        if (exception !is EmptyLocationException) {
            createSnackBar(R.string.snack_connection_error)
        }
        binding.swipeRefreshLayout.isRefreshing = false
    }

    private fun onSuccess(weather: Weather) {
        binding.weather = weather
        binding.swipeRefreshLayout.isRefreshing = false
    }

    private fun subscribeRefreshUi() {
        binding.swipeRefreshLayout.setOnRefreshListener {
            viewModel.requestWeather()
        }
    }

    fun closeSuggestions() {
        binding.suggestionsLayout.gone()
    }

    private fun subscribeFragmentResults() {
        setFragmentResultListener(Keys.DATA) { _, bundle ->
            val didUnitsChanged = bundle.getBoolean(Keys.UNITS_RESULT)
            if (didUnitsChanged) viewModel.requestWeather()

            val permissionGranted = bundle.getBoolean(Keys.LOCATION_RESULT)
            if (permissionGranted) viewModel.updateCurrentLocation()
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