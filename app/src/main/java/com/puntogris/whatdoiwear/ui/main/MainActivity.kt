package com.puntogris.whatdoiwear.ui.main

import android.graphics.Color
import android.view.View
import android.view.WindowManager
import com.puntogris.whatdoiwear.R
import com.puntogris.whatdoiwear.databinding.ActivityMainBinding
import com.puntogris.whatdoiwear.ui.base.BaseActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>(R.layout.activity_main) {

    override fun preInitializeViews() {
        setTheme(R.style.AppTheme)
    }

    override fun initializeViews() {
        setupWindow()
    }

    private fun setupWindow(){
        window.apply {
            clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
            statusBarColor = Color.TRANSPARENT
        }
    }
}
