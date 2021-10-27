package com.puntogris.smartwear.presentation.location

import android.Manifest
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.navigation.fragment.findNavController
import com.puntogris.smartwear.R
import com.puntogris.smartwear.common.createSnackBar
import com.puntogris.smartwear.databinding.FragmentLocationBinding
import com.puntogris.smartwear.presentation.base.BaseBindingFragment
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
                else createSnackBar(getString(R.string.snack_location_required))
            }
    }

    private fun onPermissionGranted(){
        findNavController().navigate(R.id.weatherFragment)
    }

    fun requestLocationPermission(){
        permissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
    }
}