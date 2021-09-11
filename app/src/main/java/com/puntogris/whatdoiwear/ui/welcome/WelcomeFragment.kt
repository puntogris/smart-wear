package com.puntogris.whatdoiwear.ui.welcome

import android.Manifest
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.navigation.fragment.findNavController
import com.puntogris.whatdoiwear.R
import com.puntogris.whatdoiwear.databinding.FragmentWelcomeBinding
import com.puntogris.whatdoiwear.ui.base.BaseFragment
import com.puntogris.whatdoiwear.utils.SharedPref
import com.puntogris.whatdoiwear.utils.createSnackBar
import com.puntogris.whatdoiwear.utils.getString
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class WelcomeFragment : BaseFragment<FragmentWelcomeBinding>(R.layout.fragment_welcome) {

    @Inject lateinit var sharedPref: SharedPref
    lateinit var permissionLauncher: ActivityResultLauncher<String>

    override fun initializeViews() {
        binding.apply {
            fragment = this@WelcomeFragment
            lifecycleOwner = viewLifecycleOwner
        }

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
        sharedPref.setUsernamePref(binding.usernameField.getString())
        findNavController().navigate(R.id.mainFragment)
    }

    fun requestLocationPermission(){
        permissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
    }
}
