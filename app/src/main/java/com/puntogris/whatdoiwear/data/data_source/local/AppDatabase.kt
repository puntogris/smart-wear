package com.puntogris.whatdoiwear.data.data_source.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.puntogris.whatdoiwear.data.data_source.local.model.LocationEntity

@Database(entities = [LocationEntity::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun locationDao(): LocationDao

    companion object {
        const val DATABASE_NAME = "what_do_i_wear_database"
    }
}