package com.puntogris.smartwear.core.presentation.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.puntogris.smartwear.BuildConfig
import com.puntogris.smartwear.feature_weather.data.data_source.local.SharedPreferences
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val sharedPreferences: SharedPreferences
) : ViewModel() {

    private val _appVersionStatus = MutableLiveData<Boolean>()
    val appVersionStatus: LiveData<Boolean> = _appVersionStatus

    init {
        if (sharedPreferences.lastVersionCode() < BuildConfig.VERSION_CODE) {
            sharedPreferences.updateLastVersionCode()
            _appVersionStatus.value = true
        }
    }

    fun showWelcomeScreen() = sharedPreferences.showWelcome()
}