package com.puntogris.smartwear.feature_weather.presentation.location

import android.Manifest
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import androidx.navigation.fragment.findNavController
import com.puntogris.smartwear.R
import com.puntogris.smartwear.core.utils.constants.Keys
import com.puntogris.smartwear.core.utils.createSnackBar
import com.puntogris.smartwear.databinding.FragmentLocationBinding
import com.puntogris.smartwear.core.presentation.base.BaseBindingFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LocationFragment : BaseBindingFragment<FragmentLocationBinding>(R.layout.fragment_location) {

    private lateinit var permissionLauncher: ActivityResultLauncher<String>

    override fun initializeViews() {
        binding.fragment = this
        setupLocationPermissionLauncher()
    }

    private fun setupLocationPermissionLauncher(){
        permissionLauncher = registerForActivityResult(ActivityResultContracts.RequestPermission())
            { isGranted: Boolean ->
                if (isGranted) onPermissionGranted()
                else createSnackBar(R.string.snack_location_required)
            }
    }

    private fun onPermissionGranted(){
        setFragmentResult(Keys.DATA, bundleOf(Keys.LOCATION_RESULT to true))
        findNavController().navigateUp()
    }

    fun requestLocationPermission(){
        permissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
    }
}
