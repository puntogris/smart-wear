package com.puntogris.smartwear.feature_weather.data.data_source.local.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "Location")
data class LocationEntity(

    @PrimaryKey(autoGenerate = false)
    val id: Int = 1,

    @ColumnInfo
    var name: String = "",

    @ColumnInfo
    val latitude: Double = 0.0,

    @ColumnInfo
    val longitude: Double = 0.0

) : Parcelable

