package com.puntogris.whatdoiwear.data.data_source.local

import androidx.lifecycle.LiveData
import androidx.room.*
import com.puntogris.whatdoiwear.data.data_source.local.model.LocationEntity

@Dao
interface LocationDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(locationEntity: LocationEntity)

    @Query("SELECT * FROM location WHERE id = 1")
    fun getLastLocationLiveData(): LiveData<LocationEntity?>

    @Query("SELECT * FROM location WHERE id = 1")
    fun getLastLocation(): LocationEntity?
}