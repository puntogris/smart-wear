package com.puntogris.smartwear.presentation.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.puntogris.smartwear.BuildConfig
import com.puntogris.smartwear.utils.SharedPref
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    sharedPref: SharedPref
): ViewModel() {

    private val _appVersionStatus = MutableLiveData<Boolean>()
    val appVersionStatus: LiveData<Boolean> = _appVersionStatus
    
    init {
        if (sharedPref.lastVersionCode() < BuildConfig.VERSION_CODE) {
            sharedPref.updateLastVersionCode()
            _appVersionStatus.value = true
        }
    }
}