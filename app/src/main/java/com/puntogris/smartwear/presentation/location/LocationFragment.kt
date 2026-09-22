package com.puntogris.smartwear.presentation.location

import android.Manifest
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import androidx.navigation.fragment.findNavController
import com.puntogris.smartwear.R
import com.puntogris.smartwear.presentation.weather.SmartWearTheme
import com.puntogris.smartwear.utils.constants.Keys
import com.puntogris.smartwear.utils.createSnackBar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LocationFragment : Fragment() {
    private val permissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) onPermissionGranted()
        else createSnackBar(R.string.snack_location_required)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, state: Bundle?): View =
        ComposeView(requireContext()).apply {
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {
                SmartWearTheme {
                    LocationPermissionScreen(onEnableLocation = ::requestLocationPermission)
                }
            }
        }

    private fun onPermissionGranted() {
        setFragmentResult(
            Keys.DATA,
            Bundle().apply { putBoolean(Keys.LOCATION_RESULT, true) }
        )
        findNavController().navigateUp()
    }

    private fun requestLocationPermission() {
        permissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
    }
}
