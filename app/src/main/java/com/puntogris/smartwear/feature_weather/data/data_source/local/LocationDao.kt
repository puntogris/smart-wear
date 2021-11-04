package com.puntogris.smartwear.feature_weather.data.data_source.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.puntogris.smartwear.feature_weather.data.data_source.local.model.LocationEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface LocationDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(locationEntity: LocationEntity)

    @Query("SELECT * FROM location WHERE id = 1")
    fun getLocationLiveData(): Flow<LocationEntity?>

    @Query("SELECT * FROM location WHERE id = 1")
    fun getLastLocation(): LocationEntity?
}