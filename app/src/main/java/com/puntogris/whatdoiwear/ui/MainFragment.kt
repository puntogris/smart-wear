package com.puntogris.whatdoiwear.ui

import android.location.Geocoder
import android.os.Bundle
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import androidx.databinding.BindingAdapter
import androidx.databinding.DataBindingUtil
import androidx.databinding.adapters.SeekBarBindingAdapter.OnProgressChanged
import androidx.databinding.adapters.SeekBarBindingAdapter.setOnSeekBarChangeListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
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

        buildLocationRequest()
        getLocation()

        binding.seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                viewModel.updateSeekBarPosition(progress)
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
                //
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                //
            }
        })

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
                binding.location.text = geocoder[0].locality + ", "+ geocoder[0].adminArea


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
    }

    private fun stopLocationUpdates() {
        fusedLocationClient.removeLocationUpdates(locationCallback)
    }

    override fun onPause() {
        super.onPause()
        stopLocationUpdates()
    }


}
