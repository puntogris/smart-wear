package com.puntogris.whatdoiwear.ui

import android.app.Application
import android.location.Location
import android.os.FileUtils
import android.view.View
import android.widget.SeekBar
import androidx.databinding.ObservableField
import androidx.lifecycle.*
import androidx.preference.PreferenceManager
import com.puntogris.whatdoiwear.data.Repository
import java.text.SimpleDateFormat
import java.util.*
import kotlin.time.ExperimentalTime
import kotlin.time.hours

class MainFragmentViewModel(application: Application):AndroidViewModel(application){

    private val repo = Repository()
    private val locale = Locale.getDefault()
    private val sharedPref = PreferenceManager.getDefaultSharedPreferences(application.applicationContext)

    private val dateNow = MutableLiveData(Date())
    private var _seekBarPosition = MutableLiveData<Int>()
    val seekBarPosition = _seekBarPosition

    private var location = MutableLiveData<Location>()
    private var _locationName = MutableLiveData<String>()
    val locationName:LiveData<String> = _locationName

    val time:LiveData<String> = dateNow.switchMap{
        MutableLiveData(SimpleDateFormat("h:mm a",locale).format(it))
    }
    val date:LiveData<String> = dateNow.switchMap{
        MutableLiveData(SimpleDateFormat("EEE, MMM d",locale).format(it))
    }
    val name = sharedPref!!.getString("player_name","Hey you")

    val weatherBody = location.switchMap {
        liveData { emitSource(repo.getWeatherApi(it)) }
    }

    fun setLocation(location: Location){
        this.location.value = location
    }
    fun setLocationName(locationName:String){
        this._locationName.value = locationName
    }

    fun updateSeekBarPosition(seekBar: SeekBar,progresValue: Int, fromUser: Boolean) {
        _seekBarPosition.value = progresValue
    }

    fun updateDate(){
        dateNow.value = Date()
    }

}