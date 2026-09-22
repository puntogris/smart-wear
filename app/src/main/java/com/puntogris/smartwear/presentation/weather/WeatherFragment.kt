package com.puntogris.smartwear.presentation.weather

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.setFragmentResultListener
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.fragment.findNavController
import com.puntogris.smartwear.R
import com.puntogris.smartwear.domain.model.Location
import com.puntogris.smartwear.presentation.util.LocationResult
import com.puntogris.smartwear.utils.constants.Keys
import com.puntogris.smartwear.utils.createSnackBar
import com.puntogris.smartwear.utils.hasLocationPermission
import com.puntogris.smartwear.utils.launchAndRepeatWithViewLifecycle
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WeatherFragment : Fragment() {
    private val viewModel: WeatherViewModel by activityViewModels()
    private var suggestions by mutableStateOf<List<Location>>(emptyList())
    private var isSearching by mutableStateOf(false)
    private var showSuggestions by mutableStateOf(false)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, state: Bundle?): View =
        ComposeView(requireContext()).apply {
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {
                val location by viewModel.currentLocation.collectAsStateWithLifecycle()
                val weatherResult by viewModel.weatherResult.collectAsStateWithLifecycle()
                SmartWearTheme {
                    WeatherScreen(
                        location = location,
                        weatherResult = weatherResult,
                        suggestions = suggestions,
                        isSearching = isSearching,
                        showSuggestions = showSuggestions,
                        onSearch = viewModel::getLocationSuggestions,
                        onUseCurrentLocation = ::useCurrentLocation,
                        onSuggestionSelected = ::selectLocation,
                        onDismissSuggestions = { showSuggestions = false },
                        onRefresh = viewModel::requestWeather
                    )
                }
            }
        }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        launchAndRepeatWithViewLifecycle {
            viewModel.locationResult.collect { result ->
                isSearching = result is LocationResult.Loading
                when (result) {
                    is LocationResult.Error -> createSnackBar(result.error)
                    is LocationResult.Success.GetLocations -> {
                        suggestions = result.data
                        showSuggestions = true
                    }
                    is LocationResult.Success.UpdateLocation -> {
                        showSuggestions = false
                        createSnackBar(R.string.snack_location_updated_success)
                    }
                    LocationResult.Loading -> Unit
                }
            }
        }
        setFragmentResultListener(Keys.DATA) { _, bundle ->
            if (bundle.getBoolean(Keys.UNITS_RESULT)) viewModel.requestWeather()
            if (bundle.getBoolean(Keys.LOCATION_RESULT)) viewModel.updateCurrentLocation()
        }
    }

    private fun selectLocation(location: Location) {
        viewModel.insert(location)
        showSuggestions = false
    }

    private fun useCurrentLocation() {
        if (hasLocationPermission()) viewModel.updateCurrentLocation()
        else findNavController().navigate(R.id.locationFragment)
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
