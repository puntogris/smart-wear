package com.puntogris.whatdoiwear.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.puntogris.whatdoiwear.data.local.LocationDao
import com.puntogris.whatdoiwear.model.LastLocation

@Database(entities = [LastLocation::class], version = 1, exportSchema = false)
abstract class LocationDatabase : RoomDatabase() {

    abstract fun locationDao(): LocationDao

}