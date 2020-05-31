package com.puntogris.whatdoiwear.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.puntogris.whatdoiwear.R
import com.puntogris.whatdoiwear.databinding.FragmentWelcomeBinding
import com.puntogris.whatdoiwear.di.injector

class WelcomeFragment : Fragment() {

    private val sharedPref by lazy { injector.sharedPreferences }
    private val navController by lazy { findNavController() }

    private lateinit var binding:FragmentWelcomeBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_welcome, container, false)

        binding.apply {
            welcomeFragment = this@WelcomeFragment
            lifecycleOwner = viewLifecycleOwner
        }

        if(sharedPref.exists()) navController.navigate(R.id.mainFragment)

        return binding.root
    }

    fun saveUserNameAndNavigateToMainFragment(){
        val input = binding.userNameEditText.text.toString()
        sharedPref.putData(input)
        navController.navigate(R.id.mainFragment)
    }

}
