package se.frost.mapwithcar

import android.support.v7.app.AppCompatActivity
import android.os.Bundle

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.android.synthetic.main.activity_maps.*

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap

    private val frost = LatLng(59.3374653, 18.0288792)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
                .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
        initButtons()
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        mMap.uiSettings.isRotateGesturesEnabled = false
        mMap.uiSettings.isMyLocationButtonEnabled = true

        // Add a marker in Frost and move the camera
        update_button.isEnabled = true
        reset_button.isEnabled = true
        resetCarLocation()
    }

    private fun initButtons() {
        update_button.setOnClickListener({ updateCarLocation() })
        reset_button.setOnClickListener({ resetCarLocation() })
    }

    private fun updateCarLocation() {
        val location : LatLng = CarLocationService.instance.getCarLocation() ?: return

        MapDrawHelper.drawCarWithPath(applicationContext, mMap, CarLocationService.instance.getCarLocations())
        MapDrawHelper.zoomToFitAllLocations(mMap, CarLocationService.instance.getCarLocations())
    }

    private fun resetCarLocation() {
        mMap.clear()
        CarLocationService.instance.reset(frost)
        mMap.addMarker(MarkerOptions().position(frost).title("Frost° in Stockholm"))
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(frost, 15.0f))

    }

}
