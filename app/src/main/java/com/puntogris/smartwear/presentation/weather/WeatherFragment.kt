package com.puntogris.smartwear.presentation.weather

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResultListener
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.puntogris.smartwear.R
import com.puntogris.smartwear.utils.Result
import com.puntogris.smartwear.utils.constants.HttpRoutes
import com.puntogris.smartwear.utils.constants.Keys
import com.puntogris.smartwear.utils.createSnackBar
import com.puntogris.smartwear.utils.getString
import com.puntogris.smartwear.utils.gone
import com.puntogris.smartwear.utils.hasLocationPermission
import com.puntogris.smartwear.utils.hideKeyboard
import com.puntogris.smartwear.utils.launchAndRepeatWithViewLifecycle
import com.puntogris.smartwear.utils.onSearch
import com.puntogris.smartwear.utils.viewBinding
import com.puntogris.smartwear.databinding.FragmentWeatherBinding
import com.puntogris.smartwear.feature_weather.domain.model.Location
import com.puntogris.smartwear.feature_weather.domain.model.Weather
import com.puntogris.smartwear.feature_weather.domain.model.events.RecommendationEvent
import com.puntogris.smartwear.feature_weather.presentation.util.EmptyLocationException
import com.puntogris.smartwear.feature_weather.presentation.util.LocationResult
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WeatherFragment : Fragment(R.layout.fragment_weather) {

    private val binding by viewBinding(FragmentWeatherBinding::bind)


    private val viewModel: WeatherViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.animationView.isVisible = viewModel.isAnimationEnabled
        setupClickListeners()
        subscribeWeatherUi()
        subscribeLocationUi()
        subscribeRefreshUi()
        subscribeFragmentResults()
        setupSearchLocationsUi()
    }

    private fun setupClickListeners() = with(binding) {
        searchButton.setOnClickListener { onSearchLocationClicked() }
        currentLocationButton.setOnClickListener { useCurrentLocation() }
        closeSuggestionsButton.setOnClickListener { closeSuggestions() }
    }

    private fun subscribeLocationUi() {
        launchAndRepeatWithViewLifecycle {
            viewModel.currentLocation.collect(::renderLocation)
        }
    }

    private fun renderLocation(currentLocation: Location?) = with(binding) {
        val hasLocation = currentLocation != null
        forecastTitle.isVisible = hasLocation
        recommendationTitle.isVisible = hasLocation
        emptyHolder.isVisible = !hasLocation
        searchInput.setText(currentLocation?.name.orEmpty())
        location.text = currentLocation?.let {
            getString(R.string.forecast_location_name_title, it.name)
        }.orEmpty()
    }

    private fun renderWeather(weather: Weather) = with(binding) {
        current.text = getString(
            R.string.current_weather_temp_description,
            weather.current.temperature.asString(),
            weather.current.description
        )
        forecast.text = weather.forecast.events
            .filter { it.isValid() }
            .joinToString(" ") { it.buildSummary(requireContext()) }
        recommendation.text = weather.forecast.events
            .filterIsInstance<RecommendationEvent>()
            .filter { it.isValid() }
            .joinToString(" ") { it.buildRecommendation(requireContext()) }
        Glide.with(imageView)
            .load(HttpRoutes.WEATHER_ICON + "/${weather.current.icon}.png")
            .into(imageView)
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
                    LocationResult.Loading -> Unit
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
                    is Result.Success -> {
                        renderWeather(it.data)
                        binding.swipeRefreshLayout.isRefreshing = false
                    }
                    is Result.Failure -> {
                        if (it.exception !is EmptyLocationException) {
                            createSnackBar(R.string.snack_connection_error)
                        }
                        binding.swipeRefreshLayout.isRefreshing = false
                    }
                    is Result.Loading -> {
                        binding.swipeRefreshLayout.isRefreshing = true
                    }
                    else -> Unit
                }
            }
        }
    }

    private fun onSearchLocationClicked() {
        viewModel.getLocationSuggestions(binding.searchInput.getString())
    }

    private fun useCurrentLocation() {
        if (hasLocationPermission()) {
            viewModel.updateCurrentLocation()
        } else {
            findNavController().navigate(R.id.locationFragment)
        }
    }

    private fun subscribeRefreshUi() {
        binding.swipeRefreshLayout.setOnRefreshListener {
            viewModel.requestWeather()
        }
    }

    private fun closeSuggestions() {
        binding.suggestionsLayout.gone()
        hideKeyboard()
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
