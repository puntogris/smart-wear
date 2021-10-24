package com.puntogris.whatdoiwear.ui.main

import android.view.Menu
import android.view.MenuItem
import androidx.navigation.NavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import com.puntogris.whatdoiwear.R
import com.puntogris.whatdoiwear.databinding.ActivityMainBinding
import com.puntogris.whatdoiwear.ui.base.BaseActivity
import com.puntogris.whatdoiwear.utils.getNavController
import com.puntogris.whatdoiwear.utils.hasLocationPermission
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>(R.layout.activity_main) {

    private lateinit var navController: NavController
    private lateinit var appBarConfiguration: AppBarConfiguration

    override fun preInitializeViews() {
        setTheme(R.style.AppTheme)
    }

    override fun initializeViews() {
        setupNavigation()
        setupTopToolbar()
    }

    private fun setupNavigation() {
        navController = getNavController()
        appBarConfiguration = AppBarConfiguration(setOf(R.id.weatherFragment, R.id.locationFragment))
        setupInitialDestination()
    }

    private fun setupInitialDestination() {
        navController.graph = navController.navInflater.inflate(R.navigation.navigation)
            .apply {
                startDestination =
                    if (hasLocationPermission()) R.id.weatherFragment
                    else R.id.locationFragment
            }
    }

    private fun setupTopToolbar() {
        setSupportActionBar(binding.toolbar)
        setupActionBarWithNavController(navController, appBarConfiguration)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.toolbar, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onSupportNavigateUp() =
        navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.settings -> {
                navController.navigate(R.id.preferencesFragment)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}
