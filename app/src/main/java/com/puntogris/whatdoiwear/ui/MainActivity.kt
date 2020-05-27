package com.puntogris.whatdoiwear.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.preference.PreferenceManager
import com.puntogris.whatdoiwear.R

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val sharedPref = PreferenceManager.getDefaultSharedPreferences(application.applicationContext)
        val name = sharedPref!!.getString("player_name","Hey you")


    }

}
