package com.example.chargingstation.activites

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.telecom.Call.Details
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.utils.widget.MotionLabel
import com.example.chargingstation.ChargingStation
import com.example.chargingstation.activites.ui.theme.ChargingStationTheme
import com.example.chargingstation.model.ChargerData
import com.example.chargingstation.model.ChargingStationData
import java.io.File

class DetailPage : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val dbHelper = ChargingStation(this)
        val stationId = intent.getIntExtra("station_id", -1)


        val selectedStation = if (stationId != -1) {
            dbHelper.getStationById(stationId)
        } else {
            null
        }

        enableEdgeToEdge()
        setContent {
            ChargingStationTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    DetailView(station = selectedStation)
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailView(station: ChargingStationData?) {
    val context = LocalContext.current
    var stationToDelete by remember { mutableStateOf<Int?>(null) }


    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Station Details") },
                actions = {
                    IconButton(onClick = {
                        context.startActivity(Intent(context, MainActivity::class.java))
                    }) {
                        Icon(imageVector = Icons.Filled.Home, contentDescription = "Home")
                    }
                }
            )
        }
    ) { innerPadding ->
        if (station == null) {
            // Display a message if the station data is not available
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
                contentAlignment = Alignment.Center
            ) {
                Text("Station not found.", style = MaterialTheme.typography.headlineSmall)
            }
        } else {
            // Main scrollable column for all the content
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(innerPadding)
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(24.dp) // Adds space between each section
            ) {
                // Main station name as a prominent header
                Text(
                    text = station.stationName,
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )

                // Station Information Section
                Section(title = "Station Information") {
                    StationDetailText("Owner", station.owner)
                    StationDetailText("Contact", station.contact.toString())
                    StationDetailText("Location", station.location)
                    StationDetailText("GPS Coordinates", "${station.latitude}, ${station.longitude}")
                    StationDetailText("Recorded on", station.dateTime)
                }

//                 Charger Details Section

//                Section(title = "Charger Details") {
//                    ChargerDetailCard("Charger 1", station)
//                    Spacer(modifier = Modifier.height(16.dp))
//                    ChargerDetailCard("Charger 2", station)
//                    Spacer(modifier = Modifier.height(16.dp))
//                    ChargerDetailCard("Charger 3", station)
//                }

                // Operational Data Section

                Section(title = "Operational Data") {
                    StationDetailText("Avg. Electricity Cost/Month", "${station.electricityCostPerMonth}")
                    StationDetailText("Avg. Cars/Buses per Day", station.carBusPerDay.toString())
                    StationDetailText("Avg. Microbuses per Day", station.microBusPerDay.toString())
                    StationDetailText("Implementation Challenges", station.challenges, singleLine = false)
                }

                // Photos Section
                Section(title = "Photos") {
                    DisplayPhotos(photo1 = station.photo1, photo2 = station.photo2)
                }
                Row (
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ){
                    Button(onClick = {
                        val intent = Intent(context, Station1::class.java)
                        val tex = station.id
                        intent.putExtra("station_id", tex)
                        context.startActivity(intent)
                    },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.Transparent, // Matches background
                            contentColor = Color.Black// Text color
                        )
                    )
                    {
                        Text(text = "Edit    ")
                        Icon(
                            modifier = Modifier
                                .size(20.dp),
                            imageVector = Icons.Filled.Edit,
                            contentDescription = "Edit"
                        )
                    }

                    Button( onClick = {
                        context.startActivity(Intent(context, Charger::class.java))
                    },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.Transparent, // Matches background
                            contentColor = Color.Black// Text color
                        ) ){
                        Text("Add Charger  ")
                        Icon(
                            modifier = Modifier.size(20.dp),
                            imageVector = Icons.Filled.Add,
                            contentDescription = "Add"
                        )
                    }

                    Button(onClick = {
                        stationToDelete = station.id
                    },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.Transparent, // Matches background
                            contentColor = Color.Black// Text color
                    )
                    )
                    {
                        Text(text = "Delete    ")
                        Icon(
                            modifier = Modifier.size(20.dp),
                            imageVector = Icons.Filled.Delete,
                            contentDescription = "Delete"
                        )
                    }
                }
            }
            // outside Column
            stationToDelete?.let { id ->
                AlertDialog(
                    onDismissRequest = { stationToDelete = null },
                    title = { Text("Confirm Deletion") },
                    text = { Text("Are you sure you want to delete this station?") },
                    confirmButton = {
                        TextButton(onClick = {
                            val deleted = ChargingStation(context).deleteTaskById(id)
                            Toast.makeText(context, "Deleted $deleted record(s)", Toast.LENGTH_SHORT).show()
                            stationToDelete = null
                            context.startActivity(Intent(context, MainActivity::class.java))
                            // optionally finish() the DetailPage activity
                        }) { Text("Yes") }
                    },
                    dismissButton = {
                        TextButton(onClick = { stationToDelete = null }) { Text("Cancel")
                        }
                    }
                )
            }
        }
    }
}

@Composable
fun Section(title: String, content: @Composable ColumnScope.() -> Unit) {
    Column {
        Text(
            text = title,
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        Divider(thickness = 1.dp, color = MaterialTheme.colorScheme.primary.copy(alpha = 0.5f))
        Spacer(modifier = Modifier.height(12.dp))
        content()
    }
}

@Composable
fun StationDetailText(label: String, value: String, singleLine: Boolean = true) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = if (singleLine) Alignment.CenterVertically else Alignment.Top
    ) {
        Text(
            text = "$label:",
            fontWeight = FontWeight.Bold,
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.weight(0.4f) // Label takes 40% of the width
        )
        Text(
            text = value,
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.weight(0.6f) // Value takes 60% of the width
        )
    }
    Spacer(modifier = Modifier.height(8.dp))
}


//@Composable
//fun ChargerDetailCard(chargerId: String, station: ChargingStationData , station1: ChargerData) {
//
//
//    val (chargerNo, make, type, capacity, cost) = when (chargerId) {
//        "Charger 1" -> listOf(station.charger1, station.chargerMake1, station.chargerType1, station.chargerCapacity1, station.chargerCost1)
//        "Charger 2" -> listOf(station.charger2, station.chargerMake2, station.chargerType2, station.chargerCapacity2, station.chargerCost2)
//        else -> listOf(station.charger3, station.chargerMake3, station.chargerType3, station.chargerCapacity3, station.chargerCost3)
//    }
//
//    Card(
//        modifier = Modifier.fillMaxWidth(),
//        elevation = CardDefaults.cardElevation(4.dp),
//        shape = RoundedCornerShape(12.dp),
//        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
//    ) {
//        Column(modifier = Modifier.padding(16.dp)) {
//            Text(
//                text = "$chargerId - #$chargerNo",
//                style = MaterialTheme.typography.titleMedium,
//                fontWeight = FontWeight.Bold
//            )
//            Spacer(modifier = Modifier.height(12.dp))
//            StationDetailText("Make", make.toString())
//            StationDetailText("Type", type.toString())
//            StationDetailText("Capacity", capacity.toString())
//            StationDetailText("Cost", "$cost")
//        }
//    }
//}

@Composable
fun DisplayPhotos(photo1: ByteArray?, photo2: ByteArray?) {
    val context = LocalContext.current

    fun openFullscreen(byteArray: ByteArray) {
        val tempFile = File(context.cacheDir, "temp_image_${System.currentTimeMillis()}.jpg")
        tempFile.writeBytes(byteArray)

        val intent = Intent(context, FullscreenImageActivity::class.java)
        intent.putExtra("image_path", tempFile.absolutePath)
        context.startActivity(intent)
    }


    fun byteArrayToBitmap(byteArray: ByteArray): Bitmap? {
        return try {
            BitmapFactory.decodeByteArray(byteArray, 0, byteArray.size)
        } catch (e: Exception) {
            Log.e("ImageConversion", "Failed to decode byte array", e)
            null
        }
    }

    Row( modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(16.dp))

    {
        photo1?.let { byteArray ->
            val bitmap1 = remember(byteArray) { byteArrayToBitmap(byteArray) }
            bitmap1?.let { bmp ->
                Image(
                    bitmap = bmp.asImageBitmap(),
                    contentDescription = "Photo 1",
                    modifier = Modifier
                        .weight(1f)
                        .aspectRatio(1f)
                        .clip(RoundedCornerShape(16.dp))
                        .clickable { openFullscreen(byteArray) }
                )
            }
        }

        photo2?.let { byteArray ->
            val bitmap2 = remember(byteArray) { byteArrayToBitmap(byteArray) }
            bitmap2?.let { bmp ->
                Image(
                    bitmap = bmp.asImageBitmap(),
                    contentDescription = "Photo 2",
                    modifier = Modifier
                        .weight(1f)
                        .aspectRatio(1f)
                        .clip(RoundedCornerShape(12.dp))
                        .clickable { openFullscreen(byteArray) }
                )
            }
        }
    }
}
