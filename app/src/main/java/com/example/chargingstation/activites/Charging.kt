package com.example.chargingstation.activites

import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.unit.dp
import java.text.SimpleDateFormat
import java.util.*

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat
import com.example.chargingstation.*
import com.example.chargingstation.ui.theme.ChargingStationTheme
import com.example.chargingstation.utils.GPSFetcher
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices

class Charging : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // ✅ Request location permission
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                1001
            )
        }

        val dbHelper = ChargingStation(this)
        enableEdgeToEdge()
        setContent {
            ChargingStationTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    Stationscreen(db = dbHelper)
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true)
@Composable
fun Stationscreen(db: ChargingStation? = null) {
    val context = LocalContext.current

    var stationName by remember { mutableStateOf("") }
    var owner by remember { mutableStateOf("") }
    var contact by remember { mutableStateOf("") }
    var location by remember { mutableStateOf("") }

    var latitude by remember { mutableStateOf("") }
    var longitude by remember { mutableStateOf("") }
    var elevation by remember { mutableStateOf("") }

    var dateTime by remember { mutableStateOf("") }
    var locationText by remember { mutableStateOf("Fetching location...") }

    val fusedLocationClient = remember {
        LocationServices.getFusedLocationProviderClient(context)
    }

    //  Function to get GPS location
    @SuppressLint("MissingPermission")
    fun getCurrentLocation() {
        if (ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            Toast.makeText(context, "Location permission not granted", Toast.LENGTH_SHORT).show()
            return
        }

        fusedLocationClient.lastLocation
            .addOnSuccessListener { locationResult: Location? ->
                if (locationResult != null) {
                    latitude = locationResult.latitude.toString()
                    longitude = locationResult.longitude.toString()
                    elevation = locationResult.altitude.toString()
                    Toast.makeText(context, "Location Acquired", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(context, "Location unavailable", Toast.LENGTH_SHORT).show()
                }
            }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Charging Station") },
                actions = {
                    IconButton(onClick = {
                        context.startActivity(Intent(context, MainActivity::class.java))
                    }) {
                        Icon(
                            imageVector = Icons.Filled.Home,
                            contentDescription = "Home"
                        )
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(innerPadding)
                .padding(16.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start
        ) {
////////////////////////////////////////////////////////////////////////////////////////////
            Text("Station Information", modifier = Modifier.padding(bottom = 8.dp))

            OutlinedTextField(
                value = stationName,
                onValueChange = { stationName = it },
                label = { Text("Charging Station Name") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = owner,
                onValueChange = { owner = it },
                label = { Text("Owner Name") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = contact,
                onValueChange = { contact = it },
                label = { Text("Contact No.") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = location,
                onValueChange = { location = it },
                label = { Text("Location") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(8.dp))

            Box(
                modifier = Modifier,
            ) {

                Column {


                    Button(onClick = {
                        val currentDateTime = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(Date())
                        dateTime = currentDateTime

                            val gps = GPSFetcher(context)
                            gps.fetchLocation { lat, lon, elev ->
                                latitude = lat.toString()
                                longitude = lon.toString()
                                elevation = String.format("%.2f", elev)
                                locationText =
                                    "Lat: $lat\nLon: $lon\nElevation: ${"%.2f".format(elev)} m"
                            }

                    })
                    {
                        Text("Get GPS Location")
                    }

                    OutlinedTextField(
                        value = latitude,
                        onValueChange = {},
                        label = { Text("Latitude $latitude") },
                        modifier = Modifier.fillMaxWidth(),
                        enabled = false
                    )

                    OutlinedTextField(
                        value = longitude,
                        onValueChange = {},
                        label = { Text("Longitude $longitude") },
                        modifier = Modifier.fillMaxWidth(),
                        enabled = false
                    )

                    OutlinedTextField(
                        value = elevation,
                        onValueChange = {},
                        label = { Text("Elevation $elevation") },
                        modifier = Modifier.fillMaxWidth(),
                        enabled = false
                    )

                    OutlinedTextField(
                        value = dateTime,
                        onValueChange = {},
                        label = { Text("Date & time  $dateTime") },
                        modifier = Modifier.fillMaxWidth(),
                        enabled = false
                    )


                }


                Spacer(modifier = Modifier.height(8.dp))
            }



                Spacer(modifier = Modifier.height(16.dp))

                Button(onClick = {
                    if (
                        owner.isNotEmpty() && contact.isNotEmpty() && stationName.isNotEmpty() && location.isNotEmpty()
                        && longitude.isNotEmpty()
                    ) {
                        val contactInt = contact.toLong()
                        val longitudeDouble = longitude.toDouble()
                        val latitudeDouble = latitude.toDouble()
                        val elevationDouble = elevation.toDouble()
                        val chargerno1INt = charger2.toLong()
                        val chargercostI1nt = chargerCost2.toLong()
                        val chargerno1INt = chargerCost3.toLong()
                        val chargercostI1nt = chargerCost3.toLong()

                        db?.insertChargingStation(
                            owner = owner,
                            contact = contactInt,
                            stationName = stationName,
                            location = location,
                            longitude = longitudeDouble,
                            latitude = latitudeDouble,
                            elevation = elevationDouble,
                            dateTime = dateTime
                        )
                        owner = ""
                        contact = ""
                        location = ""
                        longitude = ""
                        elevation = ""
                        stationName = ""
                        latitude = ""
                        Toast.makeText(context, "SAVED", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(context, "All fields are not filled", Toast.LENGTH_SHORT)
                            .show()
                    }
                }) {
                    Text("SAVE")
                }
            }
        }
    }

