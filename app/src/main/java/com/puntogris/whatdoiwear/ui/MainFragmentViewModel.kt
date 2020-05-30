package com.puntogris.whatdoiwear.ui

import android.widget.SeekBar
import androidx.lifecycle.*
import com.puntogris.whatdoiwear.data.Repository
import com.puntogris.whatdoiwear.utils.MySharedPreferences
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

class MainFragmentViewModel @Inject constructor(
    private val repo: Repository,
    sharedPref: MySharedPreferences
) :ViewModel(){

    private val locale = Locale.getDefault()
    private val dateNow = MutableLiveData<Date>()
    private val _seekBarPosition = MutableLiveData<Int>()
    private val location = repo.getLocation()
    val seekBarPosition = _seekBarPosition

    val time:LiveData<String> = dateNow.switchMap{
        MutableLiveData(SimpleDateFormat("h:mm a",locale).format(it))
    }
    val date:LiveData<String> = dateNow.switchMap{
        MutableLiveData(SimpleDateFormat("EEE, MMM d",locale).format(it))
    }
    val name = sharedPref.getData()

    val weatherBody = location.switchMap { location ->
        liveData { emitSource(repo.getWeatherApi(location)) }
    }

    val locationName = location.switchMap { location ->
        liveData { emitSource(repo.getLocationName(location)) }
    }

    fun updateSeekBarPosition(seekBar: SeekBar,progresValue: Int, fromUser: Boolean) {
        _seekBarPosition.value = progresValue
    }

    fun updateDate(){
        dateNow.value = Date()
    }

}