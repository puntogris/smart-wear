package com.puntogris.whatdoiwear.ui.welcome

import androidx.navigation.fragment.findNavController
import com.puntogris.whatdoiwear.R
import com.puntogris.whatdoiwear.databinding.FragmentWelcomeBinding
import com.puntogris.whatdoiwear.ui.base.BaseFragment
import com.puntogris.whatdoiwear.utils.SharedPref
import com.puntogris.whatdoiwear.utils.PermissionsManager
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class WelcomeFragment : BaseFragment<FragmentWelcomeBinding>(R.layout.fragment_welcome) {

    @Inject lateinit var sharedPref: SharedPref
    @Inject lateinit var permissionsManager: PermissionsManager

    override fun initializeViews() {
        binding.apply {
            welcomeFragment = this@WelcomeFragment
            lifecycleOwner = viewLifecycleOwner
        }
        if (permissionsManager.hasPermission()) findNavController().navigate(R.id.mainFragment)
    }

    fun saveUserNameAndNavigateToMainFragment(){
        val input = binding.userNameEditText.text.toString()
        sharedPref.setUsernamePref(input)
        permissionsManager.requestPermission(this)
    }
}
