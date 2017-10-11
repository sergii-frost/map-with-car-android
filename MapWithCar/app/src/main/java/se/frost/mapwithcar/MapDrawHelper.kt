package se.frost.mapwithcar

import android.content.Context
import android.support.v4.content.res.ResourcesCompat
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.*

/**
 * MapWithCar
 * Created by Sergii Nezdolii on 2017-10-11.
 *
 * Copyright (c) 2017 Frost°. All rights reserved.
 */

const val DEGREES_FULL_CIRCLE = 360.0f
const val BOUNDS_PADDING = 100

public class MapDrawHelper {

    companion object {
        fun drawCar(map: GoogleMap, carLocation: LatLng, title: String?, bearing: Float = 0.0f) {
            map.addMarker(
                    MarkerOptions()
                            .position(carLocation)
                            .title(title)
                            .anchor(0.5f, 0.5f)
                            .icon(BitmapDescriptorFactory.fromResource(R.drawable.car_icon))
                            .rotation(DEGREES_FULL_CIRCLE - bearing) //bearing and rotation have different directions
            )
        }

        fun drawCarWithPath(context: Context, map: GoogleMap, locations: List<LatLng>) {
            if (locations.isEmpty()) return
            map.clear()
            map.addPolyline(
                    PolylineOptions()
                            .addAll(locations)
                            .color(ResourcesCompat.getColor(context.resources, R.color.orange, null))
                            .width(5.0f)
                            .geodesic(true)
            )

            var bearing = 0.0
            if (locations.size >= 2) {
                bearing = locations[locations.size - 2].bearing(locations.last())
            }

            drawCar(map, locations.last(), "Position #${locations.size}", bearing.toFloat())
        }

        fun zoomToFitAllLocations(map: GoogleMap, locations: List<LatLng>) {
            val latLngBoundsBuilder = LatLngBounds.builder()
            locations.forEach { latLngBoundsBuilder.include(it) }

            map.animateCamera(CameraUpdateFactory.newLatLngBounds(latLngBoundsBuilder.build(), BOUNDS_PADDING))
        }
    }

}