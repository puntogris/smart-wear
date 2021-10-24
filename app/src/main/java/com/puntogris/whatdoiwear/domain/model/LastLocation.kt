package com.puntogris.whatdoiwear.domain.model

import android.location.Location
import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity
data class LastLocation(

    @PrimaryKey
    val id: Int = 1,

    @ColumnInfo
    var name: String = "",

    @ColumnInfo
    val latitude: Double = 0.0,

    @ColumnInfo
    val longitude: Double = 0.0

):Parcelable{

    companion object{
        fun from(location: Location): LastLocation{
            return LastLocation(
                latitude = location.latitude,
                longitude = location.longitude
            )
        }
    }
}
