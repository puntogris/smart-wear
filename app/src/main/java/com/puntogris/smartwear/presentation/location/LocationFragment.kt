package com.puntogris.smartwear.presentation.location

import android.Manifest
import android.os.Bundle
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import androidx.navigation.fragment.findNavController
import com.puntogris.smartwear.R
import com.puntogris.smartwear.utils.createSnackBar
import com.puntogris.smartwear.utils.constants.Keys
import com.puntogris.smartwear.utils.viewBinding
import com.puntogris.smartwear.databinding.FragmentLocationBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LocationFragment : Fragment(R.layout.fragment_location) {

    private val binding by viewBinding(FragmentLocationBinding::bind)

    private val permissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) onPermissionGranted()
        else createSnackBar(R.string.snack_location_required)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.nextButton.setOnClickListener { requestLocationPermission() }
    }

    private fun onPermissionGranted() {
        setFragmentResult(Keys.DATA, bundleOf(Keys.LOCATION_RESULT to true))
        findNavController().navigateUp()
    }

    private fun requestLocationPermission() {
        permissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
    }
}
