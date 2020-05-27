package com.puntogris.whatdoiwear.ui

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import androidx.preference.PreferenceManager
import com.puntogris.whatdoiwear.R
import com.puntogris.whatdoiwear.databinding.FragmentWelcomeBinding

class WelcomeFragment : Fragment() {
    private lateinit var binding:FragmentWelcomeBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_welcome, container, false)
        binding.welcomeFragment = this


        // Inflate the layout for this fragment
        return binding.root
    }

    fun saveUserNameAndNavigateToMainFragment(){
        val sharedPref = PreferenceManager.getDefaultSharedPreferences(context)
        var input = binding.userNameEditText.text.toString()
        if (input.isEmpty()) input = "you"
        with (sharedPref.edit()) {
            putString("player_name", "Hey $input")
            apply()}
        findNavController().navigate(R.id.mainFragment)
    }

}
