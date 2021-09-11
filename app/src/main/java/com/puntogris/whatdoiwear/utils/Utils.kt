package com.puntogris.whatdoiwear.utils

import com.puntogris.whatdoiwear.model.LastLocation

object Utils {

    fun createApiPathWithLatLong(location: LastLocation):String =
        "${Constants.FIRST_PATH_API}${location.latitude},${location.longitude}${Constants.SECOND_PATH_API}"

}