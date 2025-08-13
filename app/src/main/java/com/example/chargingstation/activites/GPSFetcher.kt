package com.example.chargingstation.utils

import android.annotation.SuppressLint
import android.content.Context
import android.location.Location
import android.os.Looper
import com.google.android.gms.location.*

class GPSFetcher(private val context: Context) {
    private val fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)

    @SuppressLint("MissingPermission")
    fun fetchLocation(onLocationReceived: (latitude: Double, longitude: Double, elevation: Double) -> Unit) {
        val locationRequest = LocationRequest.Builder(Priority.PRIORITY_HIGH_ACCURACY, 3000)
            .setWaitForAccurateLocation(true)
            .build()

        fusedLocationClient.requestLocationUpdates(locationRequest, object : LocationCallback() {
            override fun onLocationResult(result: LocationResult) {
                val location: Location = result.lastLocation ?: return
                val latitude = location.latitude
                val longitude = location.longitude
                val elevation = location.altitude // in meters

                onLocationReceived(latitude, longitude, elevation)
                fusedLocationClient.removeLocationUpdates(this) // stop after one reading
            }
        }, Looper.getMainLooper())
    }
}


///////////

//
//api								params				type
//https://nwash.gov.np/projectsync/get_projects			mun_code			Get
//
//https://nwash.gov.np/applogin					{"Email": "dev@mail.com",	POST
//"Password": "nwashadmin"}
//
//
//
//
//
//
//
//
//
//

////////////