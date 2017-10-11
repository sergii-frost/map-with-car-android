package se.frost.mapwithcar

import com.google.android.gms.maps.model.LatLng

/**
 * MapWithCar
 * Created by Sergii Nezdolii on 2017-10-11.
 *
 * Copyright (c) 2017 Frost°. All rights reserved.
 */
public class CarLocationService private constructor() {

    init {
        println("This ($this) is a singleton")
    }

    private object Holder { val INSTANCE = CarLocationService() }

    companion object {
        val instance: CarLocationService by lazy { Holder.INSTANCE }
    }

    private val locations = ArrayList<LatLng>()

    fun getCarLocation() : LatLng? {
        if (locations.isEmpty()) {
            return null
        }
        locations.add(locations.last().random(maxBearing = MAX_BEARING_DEGREES / (locations.size % 4 + 1)))

        return locations.last()
    }

    fun getCarLocations() : ArrayList<LatLng> {
        return locations
    }

    fun reset(initialLocation: LatLng) {
        locations.clear()
        locations.add(initialLocation)
    }

}