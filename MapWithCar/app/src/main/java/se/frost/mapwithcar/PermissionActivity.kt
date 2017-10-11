package se.frost.mapwithcar

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_permission.*


/**
 * MapWithCar
 * Created by Sergii Nezdolii on 2017-10-11.
 *
 * Copyright (c) 2017 Frost°. All rights reserved.
 */

class PermissionActivity: AppCompatActivity() {

    val MY_PERMISSIONS_REQUEST_FINE_LOCATION = 111

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_permission)
        location_permission_button.setOnClickListener({ handleLocationPermission() })
        app_settings_button.setOnClickListener({ launchAppSettings() })

        if (isLocationPermissionGranted()) {
            launchMapsActivity()
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        if (requestCode == MY_PERMISSIONS_REQUEST_FINE_LOCATION) {
            // If request is cancelled, the result arrays are empty.
            if (grantResults.isEmpty() || grantResults.first() != PackageManager.PERMISSION_GRANTED) {
                // permission denied, boo! Disable the
                // functionality that depends on this permission.
                return
            }

            // permission was granted, yay! Do the
            // contacts-related task you need to do.
            launchMapsActivity()
        }
    }

    private fun handleLocationPermission() {
        checkForLocationPermission()
    }

    private fun checkForLocationPermission() {
        // Should we show an explanation?
        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION)) {
            // Show an explanation to the user *asynchronously* -- don't block
            // this thread waiting for the user's response! After the user
            // sees the explanation, try again to request the permission.
            AlertDialog.Builder(this)
                    .setTitle(R.string.title_location_permission)
                    .setMessage(R.string.text_location_permission)
                    .setPositiveButton(R.string.ok, { dialogInterface, i ->
                        //Prompt the user once explanation has been shown
                        requestLocationPermission()
                    })
                    .create()
                    .show()
        } else {
            // No explanation needed, we can request the permission.
            requestLocationPermission()
        }
    }

    private fun requestLocationPermission() {
        ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), MY_PERMISSIONS_REQUEST_FINE_LOCATION)
    }

    private fun isLocationPermissionGranted() : Boolean {
        return ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
    }

    private fun launchMapsActivity() {
        startActivity(Intent(this, MapsActivity::class.java))
        finish()
    }

    private fun launchAppSettings() {
        startActivity(
                Intent(android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                    Uri.fromParts("package", packageName, null))
        )
    }
}