package com.example.chargingstation.activites

import android.Manifest
import android.content.pm.PackageManager
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.compose.runtime.*
import androidx.core.content.ContextCompat
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.FileProvider
import coil.compose.rememberAsyncImagePainter
import com.example.chargingstation.ChargingStation
import com.example.chargingstation.ui.theme.ChargingStationTheme
import java.io.File

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val dbHelper = ChargingStation(this)
        enableEdgeToEdge()
        setContent {
            ChargingStationTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    MainScreen(db = dbHelper)
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true)
@Composable
fun MainScreen(db: ChargingStation? = null) {

    val context = LocalContext.current

    val db = ChargingStation(context)


    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Charging Station") })
        },

        /////////////////// **************** bottom bar *************** ///////////////////
        bottomBar = {
            BottomAppBar(
                actions = {

                    IconButton(
                        onClick = {
                            context.startActivity(Intent(context, Station1::class.java))
                        }, modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("ADD")

                    }
                }
            )
        }

        /////////////////
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(innerPadding)
                .padding(16.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            AllStationsListScreen(db)

            CameraCaptureButton()

        }
    }
}

/////////////////// **************** Show Data **************** ///////////////////

/// No Preview ///
@Composable
fun AllStationsListScreen(db: ChargingStation) {

    val stations = remember { (db.getAllChargingStations()) }
    var stationToDelete by remember { mutableStateOf<Int?>(null) }

    val context = LocalContext.current

    Column(
        modifier = Modifier
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
    ) {
        if (stations.isEmpty()) {
            Text(
                text = "No Charging Stations Available",
                style = MaterialTheme.typography.titleMedium,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(24.dp)
            )
        } else {
            stations.forEach { station ->

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    elevation = CardDefaults.cardElevation(4.dp),

                    ) {
                    Row(
                        modifier = Modifier.padding(12.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {

                        Text(
                            "Station: ${station.stationName}",
                            style = MaterialTheme.typography.titleMedium,
                            fontSize = 20.sp
                        )

                        /////////////////// *************** Edit Data *************** ///////////////////

                        Spacer(modifier = Modifier.weight(1f))

                        IconButton(onClick = {
                            val intent = Intent(context, Station1::class.java)
                            intent.putExtra("station_id", station.id)
                            context.startActivity(intent)
                        }
                        )
                        {
                            Icon(
                                modifier = Modifier
                                    .size(20.dp),
                                imageVector = Icons.Filled.Edit,
                                contentDescription = "Edit"
                            )
                        }

                        IconButton(onClick = {
                            stationToDelete = station.id
                        }
                        )
                        {
                            Icon(
                                modifier = Modifier.size(20.dp),
                                imageVector = Icons.Filled.Delete,
                                contentDescription = "Delete"
                            )
                        }

                    }
                }
            }
        }
    }

/////////////////// *************** Alert Dialog *************** ///////////////////

    stationToDelete?.let { id ->
        AlertDialog(
            onDismissRequest = { stationToDelete = null },
            title = { Text("Confirm Deletion") },
            text = { Text("Are you sure you want to delete this station?") },
            confirmButton = {
                TextButton(
                    onClick = {
                        val deleted = db.deleteTaskById(id)
                        Toast.makeText(
                            context, "Deleted $deleted station(s)", Toast.LENGTH_SHORT
                        ).show()
                        stationToDelete = null
                        context.startActivity(Intent(context, MainActivity::class.java))
                    }) {
                    Text("Yes")
                }
            },
            dismissButton = {
                TextButton(onClick = { stationToDelete = null }) {
                    Text("Cancel")
                }
            }
        )
    }
}

@Composable
fun CameraCaptureButton() {
    val context = LocalContext.current
    var imageUri by remember { mutableStateOf<Uri?>(null) }
    var permissionGranted by remember { mutableStateOf(false) }

    val cameraLauncher =
        rememberLauncherForActivityResult(ActivityResultContracts.TakePicture()) { success ->
            if (success) {
                Toast.makeText(context, "Image captured", Toast.LENGTH_SHORT).show()
            } else {
                imageUri = null
            }
        }

    val permissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        permissionGranted = isGranted
        if (!isGranted) {
            Toast.makeText(context, "Camera permission denied", Toast.LENGTH_SHORT).show()
        }
    }

    LaunchedEffect(Unit) {
        permissionGranted = ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.CAMERA
        ) == PackageManager.PERMISSION_GRANTED
    }

    Column(modifier = Modifier.padding(16.dp)) {
        Button(onClick = {
            if (permissionGranted) {
                // ✅ Generate a new file each time
                val photoFile = File(
                    context.getExternalFilesDir("Pictures"),
                    "camera_photo_${System.currentTimeMillis()}.jpg"
                )

                val uri = FileProvider.getUriForFile(
                    context,
                    "${context.packageName}.provider",
                    photoFile
                )

                imageUri = uri
                cameraLauncher.launch(uri)
            } else {
                permissionLauncher.launch(Manifest.permission.CAMERA)
            }
        }) {
            Text("Open Camera")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Box(){

            // Image Preview
            imageUri?.let { uri ->
                Image(
                    painter = rememberAsyncImagePainter(uri),
                    contentDescription = "Captured Photo",
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(250.dp),
                    contentScale = ContentScale.Crop
                )
        }
    }
    }
}
