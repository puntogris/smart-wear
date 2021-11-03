package com.puntogris.smartwear.presentation.main

import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.navigation.NavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import com.puntogris.smartwear.R
import com.puntogris.smartwear.common.getNavController
import com.puntogris.smartwear.databinding.ActivityMainBinding
import com.puntogris.smartwear.presentation.base.BaseBindingActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseBindingActivity<ActivityMainBinding>(R.layout.activity_main) {

    private lateinit var navController: NavController
    private lateinit var appBarConfiguration: AppBarConfiguration
    private val viewModel: MainViewModel by viewModels()

    override fun preInitializeViews() {
        setTheme(R.style.AppTheme)
    }

    override fun initializeViews() {
        setupNavigation()
        setupTopToolbar()
        checkAppCurrentVersion()
    }

    private fun setupNavigation() {
        navController = getNavController()
        appBarConfiguration =
            AppBarConfiguration(
                setOf(
                    R.id.weatherFragment,
                    R.id.welcomeFragment
                )
            )
        setupInitialDestination()
    }

    private fun setupInitialDestination() {
        navController.graph = navController.navInflater.inflate(R.navigation.navigation)
            .apply {
                startDestination =
                    if (viewModel.showWelcomeScreen()) R.id.welcomeFragment
                    else R.id.weatherFragment
            }
    }

    private fun setupTopToolbar() {
        setSupportActionBar(binding.toolbar)
        setupActionBarWithNavController(navController, appBarConfiguration)
    }

    private fun checkAppCurrentVersion() {
        viewModel.appVersionStatus.observe(this) { isNewVersion ->
            if (isNewVersion) navController.navigate(R.id.whatsNewDialog)
        }
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
