package com.puntogris.whatdoiwear.ui

import android.app.Application
import android.location.Location
import android.widget.SeekBar
import androidx.databinding.ObservableField
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.switchMap
import androidx.preference.PreferenceManager
import com.puntogris.whatdoiwear.data.Repository
import java.text.SimpleDateFormat
import java.util.*


class MainFragmentViewModel(application: Application):AndroidViewModel(application){
    private val repo = Repository()
    private val locale = Locale.getDefault()
    private val dateNow = Date()
    private val sharedPref = PreferenceManager.getDefaultSharedPreferences(application.applicationContext)
    val app = application

    private var _seekBarPosition = MutableLiveData<Int>()
    val seekBarPosition = _seekBarPosition


//    var seekBarValue = ObservableField(0)
//
//
//    fun onValueChanged(
//        seekBar: SeekBar?,
//        progresValue: Int,
//        fromUser: Boolean
//    ) {
//        seekBarValue.set(progresValue)
//
//    }



    private var _location = MutableLiveData<Location>()

    val time: String = SimpleDateFormat("h:mm a",locale).format(dateNow)
    val date: String = SimpleDateFormat("EEE, MMM d",locale).format(dateNow)
    val name = sharedPref!!.getString("player_name","Hey you")

    val weatherBody = _location.switchMap {
        liveData { emitSource(repo.getWeatherApi(it)) }
    }

    fun updateSeekBarPosition(position:Int){
        _seekBarPosition.value = position
    }

    fun setLocation(location: Location){
        _location.value = location
    }

}