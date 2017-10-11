package se.frost.mapwithcar

import com.google.android.gms.maps.model.LatLng

/**
 * MapWithCar
 * Created by Sergii Nezdolii on 2017-10-11.
 *
 * Copyright (c) 2017 Frost°. All rights reserved.
 */

val EARTH_RADIUS_METERS = 6372797.6
val MAX_DISTANCE_METERS = 100.0
val MAX_BEARING_DEGREES = 360.0

fun randomDouble(min: Double, max: Double) : Double {
    return Math.random() * (max - min)  + min
}

fun degreesToRadians(degrees: Double) : Double {
    return degrees * Math.PI / 180.0
}

fun radiansToDegrees(radians: Double) : Double {
    return radians * 180.0 / Math.PI
}

fun LatLng.random(maxDistance: Double = MAX_DISTANCE_METERS, maxBearing: Double = MAX_BEARING_DEGREES) : LatLng {
    return location(randomDouble(0.0, maxDistance), randomDouble(0.0, maxBearing))
}

fun LatLng.location(distanceMeters: Double, bearingDegrees: Double) : LatLng {
    val distanceRadians = distanceMeters / EARTH_RADIUS_METERS

    val bearingRadians = degreesToRadians(bearingDegrees)

    val lat1 = degreesToRadians(this.latitude)
    val lng1 = degreesToRadians(this.longitude)

    val lat2 = Math.asin(Math.sin(lat1) * Math.cos(distanceRadians) + Math.cos(lat1) * Math.sin(distanceRadians) * Math.cos(bearingRadians))
    val lng2 = lng1 + Math.atan2(Math.sin(bearingRadians) * Math.sin(distanceRadians) * Math.cos(lat1), Math.cos(distanceRadians) - Math.sin(lat1) * Math.sin(lat2))

    return LatLng(radiansToDegrees(lat2), radiansToDegrees(lng2))
}

fun LatLng.bearing(toLatLng: LatLng) : Double {
    val lat1 = degreesToRadians(this.latitude)
    val lng1 = degreesToRadians(this.longitude)

    val lat2 = degreesToRadians(toLatLng.latitude)
    val lng2 = degreesToRadians(toLatLng.longitude)

    val dLng = lng2 - lng1

    val x = Math.sin(dLng) * Math.cos(lat2)
    val y = Math.cos(lat1) * Math.sin(lat2) - Math.sin(lat1) * Math.cos(lat2) * Math.cos(dLng)

    val radiansBearing = Math.atan2(y, x)

    return radiansToDegrees(radiansBearing)
}

