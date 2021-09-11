package com.puntogris.whatdoiwear.data.local

import androidx.room.*
import com.puntogris.whatdoiwear.model.LastLocation

@Dao
interface LocationDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(lastLocation: LastLocation)

    @Query("SELECT * FROM lastlocation WHERE id = 1")
    suspend fun getLastLocation(): LastLocation?

}