package com.puntogris.whatdoiwear.presentation.location

import android.Manifest
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.navigation.fragment.findNavController
import com.puntogris.whatdoiwear.R
import com.puntogris.whatdoiwear.databinding.FragmentLocationBinding
import com.puntogris.whatdoiwear.presentation.base.BaseBindingFragment
import com.puntogris.whatdoiwear.common.createSnackBar
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
                else createSnackBar(getString(R.string.location_rationale_message))
            }
    }

    private fun onPermissionGranted(){
        findNavController().navigate(R.id.weatherFragment)
    }

    fun requestLocationPermission(){
        permissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
    }
}
