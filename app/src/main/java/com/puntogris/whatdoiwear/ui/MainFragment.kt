package com.puntogris.whatdoiwear.ui

import android.location.Geocoder
import android.os.Bundle
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.google.android.gms.location.*
import com.puntogris.whatdoiwear.R
import com.puntogris.whatdoiwear.databinding.FragmentMainBinding
import java.util.*

class MainFragment : Fragment() {
    val viewModel:MainFragmentViewModel by viewModels{ MainFragmentViewModelFactory(requireNotNull(this.activity).application)}
    private lateinit var binding: FragmentMainBinding
    private lateinit var locationCallback: LocationCallback
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var locationRequest: LocationRequest

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_main,container,false)

        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel

        viewModel.updateDate()
        buildLocationRequest()
        getLocation()

        // Inflate the layout for this fragment
        return binding.root
    }


    private fun startLocationUpdates() {
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity())
        fusedLocationClient.requestLocationUpdates(locationRequest,
            locationCallback,
            Looper.getMainLooper())
    }

    private fun getLocation(){
        locationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult?) {
                locationResult ?: return
                val location = locationResult.lastLocation
                viewModel.setLocation(location)
                val geocoder = Geocoder(context, Locale.getDefault()).getFromLocation(
                        location.latitude,
                        location.longitude,
                        1
                    )
                viewModel.setLocationName(geocoder[0].locality + ", "+ geocoder[0].adminArea)

            }
        }
    }

    private fun buildLocationRequest() {
        locationRequest = LocationRequest.create().apply {
            priority = LocationRequest.PRIORITY_HIGH_ACCURACY
            interval = 5000
            fastestInterval = 3000
            smallestDisplacement = 10f
        }
    }

    override fun onResume() {
        super.onResume()
        startLocationUpdates()
        viewModel.updateDate()
    }

    private fun stopLocationUpdates() {
        fusedLocationClient.removeLocationUpdates(locationCallback)
    }

    override fun onPause() {
        super.onPause()
        stopLocationUpdates()
    }


}
