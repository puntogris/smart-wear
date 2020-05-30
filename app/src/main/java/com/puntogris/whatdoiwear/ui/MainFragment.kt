package com.puntogris.whatdoiwear.ui

import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.puntogris.whatdoiwear.R
import com.puntogris.whatdoiwear.databinding.FragmentMainBinding
import com.puntogris.whatdoiwear.di.injector
import com.puntogris.whatdoiwear.utils.PermissionsManager

class MainFragment : Fragment() {
    private val viewModel by viewModel {  injector.mainViewModel}
    private val permissionsManager by lazy { injector.permissionManager}

    private lateinit var binding: FragmentMainBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_main,container,false)
        binding.viewModel = viewModel
        requestLocationPermission()

        return binding.root
    }

    private fun requestLocationPermission() {
        if (permissionsManager.isLocationPermissionGranted(requireContext())) {
            onLocationPermissionGranted()
        } else {
            permissionsManager.requestLocationPermission(this)
        }
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

    private fun onLocationPermissionGranted() {
        binding.lifecycleOwner = viewLifecycleOwner
    }

    private fun onLocationPermissionDenied(){
        findNavController().navigate(R.id.welcomeFragment)
    }

    override fun onResume() {
        super.onResume()
        viewModel.updateDate()
    }


}



