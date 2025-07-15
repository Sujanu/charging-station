package com.example.chargingstation.activites

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
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
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import com.example.chargingstation.ChargingStation
import com.example.chargingstation.model.ChargingStationData
import com.example.chargingstation.ui.theme.ChargingStationTheme
import com.example.chargingstation.utils.GPSFetcher
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.IOException
import java.io.InputStream
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.UUID

class Station1 : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

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
        val stationId = intent.getIntExtra("station_id", -1)

        val station = if (stationId != -1) dbHelper.getStationById(stationId) else null


        val allStations = dbHelper.getAllChargingStations()
        allStations.forEach {
            Log.d("StationData", it.toString())
        }

        enableEdgeToEdge()
        setContent {
            ChargingStationTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    ChargerStation1(db = dbHelper, station = station)
                }
            }
        }
    }
}

/////////////////// ****************  *************** ///////////////////

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChargerStation1(db: ChargingStation?, station: ChargingStationData? = null) {



    var stationName by remember { mutableStateOf(station?.stationName ?: "") }

    val temp = uidCreator()
    val iduu by remember { mutableStateOf(station?.uuid) }
    var owner by remember { mutableStateOf(station?.owner ?: "") }
    var contact by remember { mutableStateOf(station?.contact?.toString() ?: "") }
    var location by remember { mutableStateOf(station?.location ?: "") }
    var latitude by remember { mutableStateOf(station?.latitude?.toString() ?: "") }
    var longitude by remember { mutableStateOf(station?.longitude?.toString() ?: "") }
    var elevation by remember { mutableStateOf(station?.elevation?.toString() ?: "") }
    var dateTime by remember { mutableStateOf(station?.dateTime ?: "") }

    var charger1 by remember { mutableStateOf(station?.charger1 ?: "") }
    var chargerCapacity1 by remember { mutableStateOf(station?.chargerCapacity1 ?: "") }
    var chargerMake1 by remember { mutableStateOf(station?.chargerMake1 ?: "") }
    var chargerType1 by remember { mutableStateOf(station?.chargerType1 ?: "") }
    var chargerCost1 by remember { mutableStateOf(station?.chargerCost1?.toString() ?: "") }

    var charger2 by remember { mutableStateOf(station?.charger2 ?: "") }
    var chargerCapacity2 by remember { mutableStateOf(station?.chargerCapacity2 ?: "") }
    var chargerMake2 by remember { mutableStateOf(station?.chargerMake2 ?: "") }
    var chargerType2 by remember { mutableStateOf(station?.chargerType2 ?: "") }
    var chargerCost2 by remember { mutableStateOf(station?.chargerCost2?.toString() ?: "") }

    var charger3 by remember { mutableStateOf(station?.charger3 ?: "") }
    var chargerCapacity3 by remember { mutableStateOf(station?.chargerCapacity3 ?: "") }
    var chargerMake3 by remember { mutableStateOf(station?.chargerMake3 ?: "") }
    var chargerType3 by remember { mutableStateOf(station?.chargerType3 ?: "") }
    var chargerCost3 by remember { mutableStateOf(station?.chargerCost3?.toString() ?: "") }

    var photo1 by remember { mutableStateOf<ByteArray?> (null)}
    var photo2 by remember { mutableStateOf<ByteArray?>  (null)}

    var costOfElec by remember {
        mutableStateOf(
            station?.electricityCostPerMonth?.toString() ?: ""
        )
    }

    var avgMb by remember { mutableStateOf(station?.microBusPerDay?.toString() ?: "") }
    var avgCb by remember { mutableStateOf(station?.carBusPerDay?.toString() ?: "") }
    var anyChallenge by remember { mutableStateOf(station?.challenges ?: "") }

    val context = LocalContext.current

    var photoUri by remember { mutableStateOf<Uri?>(null) }

    val permissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (!isGranted) {
            Toast.makeText(context, "Camera permission denied", Toast.LENGTH_SHORT).show()
        }
    }


    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Charging Station Charger 1") },
                actions = {
                    IconButton(onClick = {
                        context.startActivity(Intent(context, MainActivity::class.java))
                    }
                    )
                    {
                        Icon(
                            imageVector = Icons.Filled.Home,
                            contentDescription = "Home"
                        )
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
        )
        {
            ///////////////////////////////////// Station Information /////////////////////////////////////
            Text("Station Information", modifier = Modifier.padding(bottom = 8.dp))

            OutlinedTextField(
                value = stationName,
                onValueChange = { stationName = it },
                label = { Text("Charging Station Name")},
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = owner,
                onValueChange = { owner = it },
                label = { Text("Owner Name") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = contact,
                onValueChange = { contact = it },
                label = { Text("Contact No.") },
                singleLine = true,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Phone
                ),
                modifier = Modifier
                    .fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = location,
                onValueChange = { location = it },
                label = { Text("Location") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )
            Box(
                modifier = Modifier,
            ) {

                Column {


                    Button(onClick = {
                        val currentDateTime =
                            SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(
                                Date()
                            )
                        dateTime = currentDateTime

                        val gps = GPSFetcher(context)
                        gps.fetchLocation { lat, lon, elev ->
                            latitude = lat.toString()
                            longitude = lon.toString()
                            elevation = String.format(Locale.US, "%.2f", elev)
                        }

                    })
                    {
                        Text("Get GPS Location")
                    }

                    OutlinedTextField(
                        value = latitude,
                        onValueChange = {},
                        label = { Text("Latitude") },
                        modifier = Modifier.fillMaxWidth(),
                        enabled = false
                    )

                    OutlinedTextField(
                        value = longitude,
                        onValueChange = {},
                        label = { Text("Longitude") },
                        modifier = Modifier.fillMaxWidth(),
                        enabled = false
                    )

                    OutlinedTextField(
                        value = elevation,
                        onValueChange = {},
                        label = { Text("Elevation") },
                        modifier = Modifier.fillMaxWidth(),
                        enabled = false
                    )

                    OutlinedTextField(
                        value = dateTime,
                        onValueChange = {},
                        label = { Text("Date & time") },
                        modifier = Modifier.fillMaxWidth(),
                        enabled = false
                    )
                }
                Spacer(modifier = Modifier.height(8.dp))
            }

            ///////////////////////////////////// Station Information /////////////////////////////////////

            Spacer(modifier = Modifier.height(8.dp))

            ///////////////////////////////////// 1 /////////////////////////////////////

            Text(text = ("Charger 1"))

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = charger1,
                onValueChange = { charger1 = it },
                label = { Text("Charger No ") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                singleLine = true,
                modifier = Modifier.fillMaxWidth()

            )
            Spacer(modifier = Modifier.height(8.dp))


            OutlinedTextField(
                value = chargerCapacity1,
                onValueChange = { chargerCapacity1 = it },
                label = { Text("Charger Capacity ") },
                singleLine = true,
                modifier = Modifier
                    .fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = chargerMake1,
                onValueChange = { chargerMake1 = it },
                label = { Text("Charger made") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = chargerType1,
                onValueChange = { chargerType1 = it },
                label = { Text("Charger type ") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = chargerCost1,
                onValueChange = { chargerCost1 = it },
                label = { Text("Charger Cost") },
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.fillMaxWidth()
            )

            ///////////////////////////////////// 1 /////////////////////////////////////

            Spacer(modifier = Modifier.height(8.dp))

            ///////////////////////////////////// 2 /////////////////////////////////////

            Text(text = ("Charger 2"))

            OutlinedTextField(
                value = charger2,
                onValueChange = { charger2 = it },
                label = { Text("Charger No ") },
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = chargerCapacity2,
                onValueChange = { chargerCapacity2 = it },
                label = { Text("Charger Capacity ") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = chargerMake2,
                onValueChange = { chargerMake2 = it },
                label = { Text("Charger made") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = chargerType2,
                onValueChange = { chargerType2 = it },
                label = { Text("Charger type ") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = chargerCost2,
                onValueChange = { chargerCost2 = it },
                label = { Text("Charger Cost") },
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.fillMaxWidth()
            )
            ///////////////////////////////////// 2 /////////////////////////////////////

            Spacer(modifier = Modifier.height(8.dp))

            ///////////////////////////////////// 3 /////////////////////////////////////

            Text(text = ("Charger 3"))

            OutlinedTextField(
                value = charger3,
                onValueChange = { charger3 = it },
                label = { Text("Charger No ") },
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),

                modifier = Modifier.fillMaxWidth()

            )
            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = chargerCapacity3,
                onValueChange = { chargerCapacity3 = it },
                singleLine = true,
                label = { Text("Charger Capacity ") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = chargerMake3,
                onValueChange = { chargerMake3 = it },
                label = { Text("Charger made") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = chargerType3,
                onValueChange = { chargerType3 = it },
                label = { Text("Charger type ") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = chargerCost3,
                onValueChange = { chargerCost3 = it },
                label = { Text("Charger Cost") },
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.fillMaxWidth()

            )
            ///////////////////////////////////////  3 ///////////////////////////////////////

            Spacer(modifier = Modifier.height(8.dp))

            ///////////////////////////////////// Station Description /////////////////////////////////////

            Text(text = "Station Description")

            OutlinedTextField(
                value = costOfElec,
                onValueChange = { costOfElec = it },
                label = { Text("Cost of Electricity per month ") },
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier
                    .fillMaxWidth()
            )

            OutlinedTextField(
                value = avgMb,
                onValueChange = { avgMb = it },
                label = { Text(text = "Average no. of Micro bus per day") },
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),

                modifier = Modifier
                    .fillMaxWidth()
            )


            OutlinedTextField(
                value = avgCb,
                onValueChange = { avgCb = it },
                label = { Text(text = "Average no. of Car bus per day") },
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.fillMaxWidth()

            )

            OutlinedTextField(
                value = anyChallenge,
                onValueChange = { anyChallenge = it },
                singleLine = true,
                label = { Text(text = "Any challenges or issues during implementation") },
                        modifier = Modifier
                    .fillMaxWidth()
            )



            ///////////////////////////////////// Station Description /////////////////////////////////////

            Spacer(modifier = Modifier.height(8.dp))

            ///////////////////////////////////// SAVE /////////////////////////////////////
            Column {

                Spacer(modifier = Modifier.height(8.dp))
                ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

                Button(onClick = {
                    if (
                        charger1.isNotEmpty() && chargerCapacity1.isNotEmpty() && chargerCost1.isNotEmpty() &&
                        chargerMake1.isNotEmpty() && chargerType1.isNotEmpty() && owner.isNotEmpty() &&
                        contact.isNotEmpty() && stationName.isNotEmpty() && location.isNotEmpty() &&
                        longitude.isNotEmpty() && charger2.isNotEmpty() && chargerCapacity2.isNotEmpty() &&
                        chargerCost2.isNotEmpty() && chargerMake2.isNotEmpty() && chargerType2.isNotEmpty() &&
                        charger3.isNotEmpty() && chargerCapacity3.isNotEmpty() && chargerCost3.isNotEmpty() &&
                        chargerMake3.isNotEmpty() && chargerType3.isNotEmpty() && costOfElec.isNotEmpty() &&
                        avgCb.isNotEmpty() && avgMb.isNotEmpty() && anyChallenge.isNotEmpty()
                    ) {
                        val contactInt = contact.toLong()
                        val longitudeDouble = longitude.toDouble()
                        val latitudeDouble = latitude.toDouble()
                        val elevationDouble = elevation.toDouble()

                        val chargerCostInt1 = chargerCost1.toLong()

                        val chargerCostInt2 = chargerCost2.toLong()

                        val chargerCostInt3 = chargerCost3.toLong()

                        val iduuu = iduu.toString()

                        val newStation = ChargingStationData(
                            id = station?.id ?: 0,
                            uuid = iduuu,
                            owner = owner,
                            stationName = stationName,
                            contact = contactInt,
                            location = location,
                            longitude = longitudeDouble,
                            latitude = latitudeDouble,
                            elevation = elevationDouble,
                            dateTime = dateTime,

                            charger1 = charger1,
                            chargerCapacity1 = chargerCapacity1,
                            chargerMake1 = chargerMake1,
                            chargerType1 = chargerType1,
                            chargerCost1 = chargerCostInt1,

                            charger2 = charger2,
                            chargerCapacity2 = chargerCapacity2,
                            chargerMake2 = chargerMake2,
                            chargerType2 = chargerType2,
                            chargerCost2 = chargerCostInt2,

                            charger3 = charger3,
                            chargerCapacity3 = chargerCapacity3,
                            chargerMake3 = chargerMake3,
                            chargerType3 = chargerType3,
                            chargerCost3 = chargerCostInt3,

                            electricityCostPerMonth = costOfElec.toInt(),
                            microBusPerDay = avgMb.toInt(),
                            carBusPerDay = avgCb.toInt(),
                            challenges = anyChallenge
//                            photo1 = photo1,
//                            photo2 = photo2
                        )

                        if (station == null) {
                            // INSERT
                            db?.insertCharger1(
                                uuid = newStation.uuid,
                                owner = newStation.owner,
                                contact = newStation.contact,
                                stationName = newStation.stationName,
                                location = newStation.location,
                                longitude = newStation.longitude,
                                latitude = newStation.latitude,
                                elevation = newStation.elevation,
                                dateTime = newStation.dateTime,

                                chargerCapacity1 = newStation.chargerCapacity1,
                                charger1 = newStation.charger1.toLong(),
                                chargerMake1 = newStation.chargerMake1,
                                chargerType1 = newStation.chargerType1,
                                chargerCost1 = newStation.chargerCost1,

                                chargerCapacity2 = newStation.chargerCapacity2,
                                charger2 = newStation.charger2.toLong(),
                                chargerMake2 = newStation.chargerMake2,
                                chargerType2 = newStation.chargerType2,
                                chargerCost2 = newStation.chargerCost2,

                                chargerCapacity3 = newStation.chargerCapacity3,
                                charger3 = newStation.charger3.toLong(),
                                chargerMake3 = newStation.chargerMake3,
                                chargerType3 = newStation.chargerType3,
                                chargerCost3 = newStation.chargerCost3,

                                costOfElectrictyEerMonth = newStation.electricityCostPerMonth,
                                averageNoOfMicroBusPerDay = newStation.microBusPerDay,
                                averageNoOfCarBusPerDay = newStation.carBusPerDay,
                                anyChallengesOrIssuesDuringImplementaion = newStation.challenges,
//                                photo1 = newStation.photo1,
//                                photo2 = newStation.photo2
                            )
                            Toast.makeText(context, "Station inserted!", Toast.LENGTH_SHORT).show()
                        } else {
                            // UPDATE
                            val updated = db?.updateChargingStation(newStation)
                            if (updated == true) {
                                Toast.makeText(context, "Station updated!", Toast.LENGTH_SHORT)
                                    .show()
                            } else {
                                Toast.makeText(context, "Update failed!", Toast.LENGTH_SHORT).show()
                            }
                        }

                        context.startActivity(Intent(context, MainActivity::class.java))
                    } else {
                        Toast.makeText(context, "Fill all fields", Toast.LENGTH_SHORT).show()
                    }
                }) {
                    Text("Save")
                }
            }
        }
    }
}

@Composable
fun uidCreator(): String {
    return UUID.randomUUID().toString().toUpperCase()

}

fun uriToByteArray(context: Context, uri: Uri): ByteArray? {
    return try {
        val inputStream: InputStream? = context.contentResolver.openInputStream(uri)
        inputStream?.use {
            val outputStream = ByteArrayOutputStream()
            val buffer = ByteArray(4 * 1024) // 4KB buffer
            var bytesRead: Int
            while (it.read(buffer).also { bytesRead = it } != -1) {
                outputStream.write(buffer, 0, bytesRead)
            }
            outputStream.toByteArray()
        }
    } catch (e: IOException) {
        e.printStackTrace()
        null // Return null on error
    }
}

//@Preview
//@Composable
//fun CameraCaptureButton() {
//    val context = LocalContext.current
//    var imageUri by remember { mutableStateOf<Uri?>(null) }
//    var permissionGranted by remember { mutableStateOf(false) }
//
//    val cameraLauncher = rememberLauncherForActivityResult(ActivityResultContracts.TakePicture()) { success ->
//        if (success) {
//            Toast.makeText(context, "Image saved to: $imageUri", Toast.LENGTH_SHORT).show()
//        }
//    }
//
//    val permissionLauncher = rememberLauncherForActivityResult(
//        ActivityResultContracts.RequestPermission()
//    ) { isGranted ->
//        permissionGranted = isGranted
//    }
//
//    LaunchedEffect(Unit) {
//        permissionGranted = ContextCompat.checkSelfPermission(
//            context, Manifest.permission.CAMERA
//        ) == PackageManager.PERMISSION_GRANTED
//    }
//
//    Column(modifier = Modifier.padding(16.dp)) {
//        Button(onClick = {
//            if (permissionGranted) {
//                val photoFile = File(
//                    context.getExternalFilesDir(null),
//                    "camera_photo_${System.currentTimeMillis()}.jpg"
//                )
//
//                val uri = FileProvider.getUriForFile(
//                    context,
//                    "${context.packageName}.provider",
//                    photoFile
//                )
//
//                imageUri = uri
//                cameraLauncher.launch(uri)
//            } else {
//                permissionLauncher.launch(Manifest.permission.CAMERA)
//            }
//        }
//        ) {
//            Text(text = "Open Camera")
//        }
//
//
//
//    }
//}


