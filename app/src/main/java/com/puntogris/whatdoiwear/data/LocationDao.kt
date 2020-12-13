package com.puntogris.whatdoiwear.data

import androidx.room.*
import com.puntogris.whatdoiwear.model.LastLocation
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow

@Dao
interface LocationDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(lastLocation: LastLocation)

    @Update
    suspend fun update(lastLocation: LastLocation)

    @Query("SELECT * FROM lastlocation WHERE id = 1")
    suspend fun getLocation(): List<LastLocation>

}