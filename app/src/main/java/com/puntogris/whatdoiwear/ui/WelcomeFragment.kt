package com.puntogris.whatdoiwear.ui

import android.content.pm.PackageManager
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.puntogris.whatdoiwear.R
import com.puntogris.whatdoiwear.data.LocationDao
import com.puntogris.whatdoiwear.databinding.FragmentWelcomeBinding
import com.puntogris.whatdoiwear.model.LastLocation
import com.puntogris.whatdoiwear.utils.MySharedPreferences
import com.puntogris.whatdoiwear.utils.PermissionsManager
import com.puntogris.whatdoiwear.utils.createSnackBar
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@Suppress("DEPRECATION")
@AndroidEntryPoint
class WelcomeFragment : BaseFragment<FragmentWelcomeBinding>(R.layout.fragment_welcome) {

    @Inject
    lateinit var sharedPref: MySharedPreferences

    @Inject lateinit var permissionsManager: PermissionsManager

    override fun initializeViews() {
        binding.apply {
            welcomeFragment = this@WelcomeFragment
            lifecycleOwner = viewLifecycleOwner
        }

        if(sharedPref.checkIfUsernamePrefExists()) requestLocationPermission()
    }

    fun saveUserNameAndNavigateToMainFragment(){
        val input = binding.userNameEditText.text.toString()
        sharedPref.setUsernamePref(input)
        requestLocationPermission()
    }

    private fun onLocationPermissionGranted(){
        findNavController().navigate(R.id.mainFragment)
    }

    private fun requestLocationPermission() {
        if (permissionsManager.isLocationPermissionGranted(requireContext())) onLocationPermissionGranted()
        else permissionsManager.requestLocationPermission(this)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == PermissionsManager.LOCATION_PERMISSION_REQUEST_CODE && grantResults.isNotEmpty()) {
            when (grantResults.first()) {
                PackageManager.PERMISSION_GRANTED -> onLocationPermissionGranted()
                PackageManager.PERMISSION_DENIED -> onLocationPermissionDenied()
            }
        }
    }
    private fun onLocationPermissionDenied(){
        sharedPref.deleteUsernamePref()
        createSnackBar(getString(R.string.weather_location_message))
    }

}
