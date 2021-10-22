package com.puntogris.whatdoiwear.ui.main

import android.Manifest
import android.content.pm.PackageManager
import android.graphics.Color
import android.view.View
import android.view.WindowManager
import androidx.core.app.ActivityCompat
import androidx.navigation.NavController
import com.puntogris.whatdoiwear.R
import com.puntogris.whatdoiwear.databinding.ActivityMainBinding
import com.puntogris.whatdoiwear.ui.base.BaseActivity
import com.puntogris.whatdoiwear.utils.getNavController
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>(R.layout.activity_main) {

    private lateinit var navController: NavController

    override fun preInitializeViews() {
        setTheme(R.style.AppTheme)
    }

    override fun initializeViews() {
        setupNavigation()
    }

    private fun setupNavigation(){
        navController = getNavController()
        setupInitialDestination()
    }

    private fun setupInitialDestination(){
        navController.graph = navController.navInflater.inflate(R.navigation.navigation)
            .apply {
                startDestination =
                    if (hasLocationPermission()) R.id.mainFragment
                    else R.id.welcomeFragment
            }
    }

    private fun hasLocationPermission(): Boolean{
        if (Manifest.permission.ACCESS_FINE_LOCATION == Manifest.permission.ACCESS_BACKGROUND_LOCATION &&
            android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.Q) {
            return true
        }
        return ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) ==
                PackageManager.PERMISSION_GRANTED
    }
}
