package com.puntogris.whatdoiwear.data.data_source.local.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.puntogris.whatdoiwear.domain.model.Location
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity
data class LocationEntity(

    @PrimaryKey
    val id: Int = 1,

    @ColumnInfo
    var name: String = "",

    @ColumnInfo
    val latitude: Double = 0.0,

    @ColumnInfo
    val longitude: Double = 0.0

):Parcelable
