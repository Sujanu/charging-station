package com.example.chargingstation.activites


import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.chargingstation.ChargingStation
import com.example.chargingstation.ui.theme.ChargingStationTheme

class Charging : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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

    var stationName by remember { mutableStateOf("") }
    var owner by remember { mutableStateOf("") }
    var contact by remember { mutableStateOf("") }
    var location by remember { mutableStateOf("") }

    var latitude by remember { mutableStateOf("") }
    var longitude by remember { mutableStateOf("") }
    var elevation by remember { mutableStateOf("") }

    val context = LocalContext.current

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Charging Station") },
             actions = {
                 val context = LocalContext.current
                 Button(onClick = {
                     context.startActivity(Intent(context, MainActivity::class.java))
                 }) {
                     Text("Home")
                 }

                 Button(onClick = {
                     context.startActivity(Intent(context, Station1::class.java))
                 }) {
                     Text(text = "Station 1")
                 }

                 Button(onClick = {
                     context.startActivity(Intent(context, Station2::class.java))
                 }) {
                     Text(text = "Station 2")
                 }

                 Button(onClick = {
                     context.startActivity(Intent(context, Station3::class.java))
                 }) {
                     Text(text = "Station 3")
                 }

                 Button(onClick = {
                     context.startActivity(Intent(context, StationDesc::class.java))
                 }) {
                     Text(text = "Station info")
                 }

             }
            )
        }

    )
    { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(innerPadding)
                .padding(16.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start
        ) {
            /// Column ///
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

            OutlinedTextField(
                value = longitude,
                onValueChange = { longitude = it },
                label = { Text("Longitude") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = latitude,
                onValueChange = { latitude = it },
                label = { Text("Latitude") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = elevation,
                onValueChange = { elevation = it },
                label = { Text("Elevation") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(8.dp))


            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            )
            {
                Button(onClick = {
                    if (
                        owner.isNotEmpty() && contact.isNotEmpty() && stationName.isNotEmpty() && location.isNotEmpty()
//                        &&  longitude.isNotEmpty() && latitude.isNotEmpty() && elevation.isNotEmpty()
                    ) {

                        val contactInt = contact.toLong()
//                        val longitudeDouble = longitude.toDouble()
//                        val latitudeDouble = latitude.toDouble()
//                        val elevationDouble = elevation.toDouble()

                        db?.insertChargingStation(
                            owner = owner,
                            contact = contactInt,
                            stationName = stationName,
                            location = location
//                            longitude = longitudeDouble,
//                            latitude = latitudeDouble,
//                            elevation = elevationDouble
                        )
                        Toast.makeText(context, "SAVED", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(context, "All field are not filled", Toast.LENGTH_SHORT)
                            .show()
                    }
                })
                {
                    Text(text = "SAVE")
                }

                Button(onClick = {

                    owner = ""
                    contact = ""
                    location = ""
                    longitude = ""
                    elevation = ""
                    stationName = ""

                    Toast.makeText(context, "All field cleared", Toast.LENGTH_SHORT).show()

                }) {
                    Text(text = "Clear")
                }
            }
        }

    }
}
