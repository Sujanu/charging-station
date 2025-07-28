package com.example.chargingstation.activites

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.FileProvider
import com.example.chargingstation.ChargingStation
import com.example.chargingstation.R
import com.example.chargingstation.model.ChargingStationData
import com.example.chargingstation.ui.theme.ChargingStationTheme
import com.example.chargingstation.utils.GPSFetcher
import java.io.ByteArrayOutputStream
import java.io.File
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.UUID

class Station1 : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val dbHelper = ChargingStation(this)
        val stationId = intent.getIntExtra("station_id", -1)

        val station = if (stationId != -1) {
            dbHelper.getStationById(stationId)

        } else{ null}

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

    val temp = station?.uuid ?: uidCreator()

    var stationName by remember { mutableStateOf(station?.stationName ?: "") }
    var owner by remember { mutableStateOf(station?.owner ?: "") }
    var contact by remember { mutableStateOf(station?.contact?.toString() ?: "") }
    var location by remember { mutableStateOf(station?.location ?: "") }
    var longitude by remember { mutableStateOf(station?.longitude?.toString() ?: "") }
    var elevation by remember { mutableStateOf(station?.elevation?.toString() ?: "") }
    var dateTime by remember { mutableStateOf(station?.dateTime ?: "") }
    var latitude by remember { mutableStateOf(station?.latitude?.toString() ?: "") }

    var costOfElec by remember {  mutableStateOf(station?.electricityCostPerMonth?.toString() ?: "") }

    var avgMb by remember { mutableStateOf(station?.microBusPerDay?.toString() ?: "") }
    var avgCb by remember { mutableStateOf(station?.carBusPerDay?.toString() ?: "") }
    var anyChallenge by remember { mutableStateOf(station?.challenges ?: "") }

    var photo1 by remember { mutableStateOf(station?.photo1) }
    var photo2 by remember { mutableStateOf(station?.photo2) }

    val focusManager = LocalFocusManager.current

    val stationNameFocusRequester = remember { FocusRequester() }
    val ownerFocusRequester = remember { FocusRequester() }
    val contactFocusRequester = remember { FocusRequester() }
    val locationFocusRequester = remember { FocusRequester() }

    val charger1Focus = remember { FocusRequester() }

    val electricityCostFocus = remember { FocusRequester() }
    val avgMbFocus = remember { FocusRequester() }
    val avgCbFocus = remember { FocusRequester() }
    val anyChallengeFocus = remember { FocusRequester() }


    CaptureImageAsBitmapScreen(
        initialPhoto1 = photo1,
        initialPhoto2 = photo2,
        onPhoto1Captured = { photo1 = it },
        onPhoto2Captured = { photo2 = it }
    )

    val context = LocalContext.current

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Charging Station Info") },
                actions = {
                    IconButton(onClick = {
                        context.startActivity(Intent(context, MainActivity::class.java))
                    }
                    ) {
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
        Box(modifier = Modifier.fillMaxSize()) {
//            Image(
//                painter = painterResource(id = R.drawable.charger),
//                contentDescription = null,
//                contentScale = ContentScale.Crop,
//                modifier = Modifier.matchParentSize()
//            )
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

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = Color.Transparent // Make card background transparent
                    ),
                    elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
                ) {
                    Text(
                        "Station Information", modifier = Modifier.padding(bottom = 8.dp),
                        style = MaterialTheme.typography.titleMedium.copy(
                            textDecoration = TextDecoration.Underline
                        ),
                        fontSize = 20.sp
                    )

                    OutlinedTextField(
                        value = stationName,
                        onValueChange = { stationName = it },
                        label = { Text("Charging Station Name") },
                        singleLine = true,
                        modifier = Modifier
                            .fillMaxWidth()
                            .focusRequester(stationNameFocusRequester),
                        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                        keyboardActions = KeyboardActions(onNext = { ownerFocusRequester.requestFocus() })
                    )

                    OutlinedTextField(
                        value = owner,
                        onValueChange = { owner = it },
                        label = { Text("Owner Name") },
                        singleLine = true,
                        modifier = Modifier
                            .fillMaxWidth()
                            .focusRequester(ownerFocusRequester),
                        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                        keyboardActions = KeyboardActions(onNext = { contactFocusRequester.requestFocus() })
                    )

                    OutlinedTextField(
                        value = contact,
                        onValueChange = { contact = it },
                        label = { Text("Contact No.") },
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Phone,
                            imeAction = ImeAction.Next
                        ),
                        keyboardActions = KeyboardActions(onNext = { locationFocusRequester.requestFocus() }),
                        modifier = Modifier
                            .fillMaxWidth()
                            .focusRequester(contactFocusRequester)
                    )

                    OutlinedTextField(
                        value = location,
                        onValueChange = { location = it },
                        label = { Text("Location") },
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                        keyboardActions = KeyboardActions(onNext = { charger1Focus.requestFocus() }),
                        modifier = Modifier
                            .fillMaxWidth()
                            .focusRequester(locationFocusRequester)
                    )
                }
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = Color.Transparent // Make card background transparent
                    ),
                    elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
                ) {

                    Box(
                        modifier = Modifier,
                    ) {

                        Column {

                            Button(
                                onClick = {
                                    val currentDateTime =
                                        SimpleDateFormat(
                                            "yyyy-MM-dd HH:mm:ss",
                                            Locale.getDefault()
                                        ).format(
                                            Date()
                                        )
                                    dateTime = currentDateTime

                                    val gps = GPSFetcher(context)
                                    gps.fetchLocation { lat, lon, elev ->
                                        latitude = lat.toString()
                                        longitude = lon.toString()
                                        elevation = String.format(Locale.US, "%.2f", elev)
                                    }

                                },
                                modifier = Modifier.focusRequester(locationFocusRequester),
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = Color.Transparent, // Matches background
                                    contentColor = Color.Black// Text color
                                )
                            )
                            {
                                Text(
                                    "Get GPS Location",
                                    style = MaterialTheme.typography.titleMedium.copy(
                                        textDecoration = TextDecoration.Underline
                                    ),
                                    fontSize = 18.sp
                                )
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
                }

                ///////////////////////////////////// Station Information /////////////////////////////////////

                Spacer(modifier = Modifier.height(8.dp))

                Spacer(modifier = Modifier.height(8.dp))

                ///////////////////////////////////// Station Description /////////////////////////////////////
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = Color.Transparent // Make card background transparent
                    ),
                    elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
                ) {
                    Text(
                        text = "Station Description",
                        style = MaterialTheme.typography.titleMedium.copy(
                            textDecoration = TextDecoration.Underline
                        ),
                        fontSize = 20.sp
                    )

                    OutlinedTextField(
                        value = costOfElec,
                        onValueChange = { costOfElec = it },
                        label = { Text("Cost of Electricity per month ") },
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Number,
                            imeAction = ImeAction.Next
                        ),
                        keyboardActions = KeyboardActions(onNext = { avgMbFocus.requestFocus() }),
                        modifier = Modifier
                            .fillMaxWidth()
                            .focusRequester(electricityCostFocus)
                    )

                    OutlinedTextField(
                        value = avgMb,
                        onValueChange = { avgMb = it },
                        label = { Text("Average no. of Micro bus per day") },
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Number,
                            imeAction = ImeAction.Next
                        ),
                        keyboardActions = KeyboardActions(onNext = { avgCbFocus.requestFocus() }),
                        modifier = Modifier
                            .fillMaxWidth()
                            .focusRequester(avgMbFocus)
                    )

                    OutlinedTextField(
                        value = avgCb,
                        onValueChange = { avgCb = it },
                        label = { Text("Average no. of Car bus per day") },
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Number,
                            imeAction = ImeAction.Next
                        ),
                        keyboardActions = KeyboardActions(onNext = { anyChallengeFocus.requestFocus() }),
                        modifier = Modifier
                            .fillMaxWidth()
                            .focusRequester(avgCbFocus)
                    )

                    OutlinedTextField(
                        value = anyChallenge,
                        onValueChange = { anyChallenge = it },
                        label = { Text("Any challenges or issues during implementation") },
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                        keyboardActions = KeyboardActions(onDone = { focusManager.clearFocus() }),
                        modifier = Modifier
                            .fillMaxWidth()
                            .focusRequester(anyChallengeFocus)
                    )
                }
                Spacer(modifier = Modifier.height(8.dp))

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = Color.Transparent // Make card background transparent
                    ),
                    elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
                ) {
                    Text(
                        text = "Take Photo",
                        style = MaterialTheme.typography.titleMedium.copy(
                            textDecoration = TextDecoration.Underline
                        ),
                        fontSize = 20.sp
                    )

                    CaptureImageAsBitmapScreen(
                        initialPhoto1 = photo1,
                        initialPhoto2 = photo2,
                        onPhoto1Captured = { photo1 = it },
                        onPhoto2Captured = { photo2 = it }
                    )
                }

                ///////////////////////////////////// Station Description /////////////////////////////////////

                Spacer(modifier = Modifier.height(8.dp))

                ///////////////////////////////////// SAVE /////////////////////////////////////
                Column {

                    Spacer(modifier = Modifier.height(8.dp))

                    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

                    Button(
                        onClick = {
                            if (
                                owner.isNotEmpty() && contact.isNotEmpty() && stationName.isNotEmpty() &&
                                location.isNotEmpty() && longitude.isNotEmpty() &&
                                costOfElec.isNotEmpty() && avgCb.isNotEmpty() && avgMb.isNotEmpty() && anyChallenge.isNotEmpty()

                            ) {
                                val contactInt = contact.toLong()
                                val longitudeDouble = longitude.toDouble()
                                val latitudeDouble = latitude.toDouble()
                                val elevationDouble = elevation.toDouble()

                                val uuid = temp

                                val newStation = photo1?.let {
                                    photo2?.let { it1 ->
                                        ChargingStationData(

                                            id = station?.id ?: 0,
                                            uuid = uuid,
                                            owner = owner,
                                            stationName = stationName,
                                            contact = contactInt,
                                            location = location,
                                            longitude = longitudeDouble,
                                            latitude = latitudeDouble,
                                            elevation = elevationDouble,
                                            dateTime = dateTime,

                                            electricityCostPerMonth = costOfElec.toInt(),
                                            microBusPerDay = avgMb.toInt(),
                                            carBusPerDay = avgCb.toInt(),
                                            challenges = anyChallenge,
                                            photo1 = photo1!!,
                                            photo2 = photo2!!,
                                        )
                                    }
                                }

                                if (station == null) {
                                    // INSERT
                                    if (newStation != null) {
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

                                            costOfElectrictyEerMonth = newStation.electricityCostPerMonth,
                                            averageNoOfMicroBusPerDay = newStation.microBusPerDay,
                                            averageNoOfCarBusPerDay = newStation.carBusPerDay,
                                            anyChallengesOrIssuesDuringImplementaion = newStation.challenges,
                                            photo1 = newStation.photo1,
                                            photo2 = newStation.photo2
                                        )
                                    }
                                    Toast.makeText(context, "Station inserted!", Toast.LENGTH_SHORT)
                                        .show()
                                } else {
                                    // UPDATE
                                    if (newStation != null) {
                                        val updated = db?.updateChargingStation(newStation)
                                        if (updated == true) {
                                            Toast.makeText(
                                                context,
                                                "Station updated!",
                                                Toast.LENGTH_SHORT
                                            ).show()
                                        } else {
                                            Toast.makeText(
                                                context,
                                                "Update failed!",
                                                Toast.LENGTH_SHORT
                                            ).show()
                                        }
                                    }
                                }

                                context.startActivity(Intent(context, MainActivity::class.java))
                            } else {
                                Toast.makeText(context, "Fill all fields", Toast.LENGTH_SHORT)
                                    .show()
                            }
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.Transparent, // Matches background
                            contentColor = Color.Black// Text color
                        ),
                        elevation = null,
                    ) {
                        Text(
                            "Save",
                            style = MaterialTheme.typography.titleMedium.copy(
                                textDecoration = TextDecoration.Underline
                            ),
                            fontSize = 20.sp
                        )
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    Button(
                        onClick = {
                            val intent = Intent(context, Charger::class.java)
                            intent.putExtra("uuid", temp) // `temp` holds the UUID
                            context.startActivity(intent)
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.Transparent, // Matches background
                            contentColor = Color.Black// Text color
                        )
                    ) {
                        Text(
                            "Add Charger",
                            style = MaterialTheme.typography.titleMedium.copy(
                                textDecoration = TextDecoration.Underline
                            ),
                            fontSize = 18.sp
                        )
                    }
                }

            }
        }
    }
}

@Composable
fun uidCreator(): String {
    return UUID.randomUUID().toString().uppercase()
}

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

@Composable
fun CaptureImageAsBitmapScreen(
    initialPhoto1: ByteArray?,
    initialPhoto2: ByteArray?,
    onPhoto1Captured: (ByteArray?) -> Unit,
    onPhoto2Captured: (ByteArray?) -> Unit
) {
    val context = LocalContext.current
    var photoUri by remember { mutableStateOf<Uri?>(null) }

    var byteArray1 by remember { mutableStateOf(initialPhoto1) }
    var byteArray2 by remember { mutableStateOf(initialPhoto2) }
    var isCapturingFirstPhoto by remember { mutableStateOf(true) }

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicture()
    ) { success ->
        if (success && photoUri != null) {
            val inputStream = context.contentResolver.openInputStream(photoUri!!)
            val newBitmap = BitmapFactory.decodeStream(inputStream)

            if (newBitmap != null) {
                val compressedByteArray = bitmapToByteArray(newBitmap)
                if (isCapturingFirstPhoto) {
                    byteArray1 = compressedByteArray
                    onPhoto1Captured(compressedByteArray)
                } else {
                    byteArray2 = compressedByteArray
                    onPhoto2Captured(compressedByteArray)
                }
            } else {
                Toast.makeText(context, "Failed to decode image", Toast.LENGTH_SHORT).show()
            }
        } else {
            Toast.makeText(context, "Photo was not taken", Toast.LENGTH_SHORT).show()
        }
    }

    fun takePhoto(isFirst: Boolean) {
        isCapturingFirstPhoto = isFirst
        val file = File(context.cacheDir, "camera_photo_${System.currentTimeMillis()}.jpg")
        val uri = FileProvider.getUriForFile(
            context,
            "${context.packageName}.provider",
            file
        )
        photoUri = uri
        launcher.launch(uri)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        PhotoDisplay(
            label = "Photo 1",
            byteArray = byteArray1,
            onDelete = {
                byteArray1 = null
                onPhoto1Captured(null)
            }
        )

        Button(
            onClick = { takePhoto(true) },
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Transparent, // Matches background
                contentColor = Color.Black// Text color
            ),
            elevation = null,
            enabled = byteArray1 == null,
            modifier = Modifier.padding(vertical = 8.dp)
        ) { Text("Capture First Photo",
            style = MaterialTheme.typography.titleMedium.copy(
                textDecoration = TextDecoration.Underline
            ),
        )}

        Spacer(modifier = Modifier.height(16.dp))

        PhotoDisplay(
            label = "Photo 2",
            byteArray = byteArray2,
            onDelete = {
                byteArray2 = null
                onPhoto2Captured(null)
            }
        )
        Button(
            onClick = { takePhoto(false) },
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Transparent, // Matches background
                contentColor = Color.Black// Text color
            ),
            elevation = null,
            enabled = byteArray1 != null && byteArray2 == null,
            modifier = Modifier.padding(vertical = 8.dp)
        ) { Text("Capture Second Photo",
            style = MaterialTheme.typography.titleMedium.copy(
                textDecoration = TextDecoration.Underline
            ),
            fontSize = 18.sp
        )}
    }
}

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

fun byteArrayToBitmap(byteArray: ByteArray): Bitmap? { // Return nullable Bitmap?
    return try {
        BitmapFactory.decodeByteArray(byteArray, 0, byteArray.size)
    } catch (e: Exception) {
         Log.e("ImageConversion", "Failed to decode byte array", e)
        null
    }
}

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

fun bitmapToByteArray(bitmap: Bitmap): ByteArray {
    val stream = ByteArrayOutputStream()
    bitmap.compress(Bitmap.CompressFormat.JPEG, 60, stream)
    return stream.toByteArray()
}

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

@Composable
private fun PhotoDisplay(label: String, byteArray: ByteArray?, onDelete: () -> Unit) {
    if (byteArray != null) {
        // Use our safe conversion function
        val bitmap = remember(byteArray) { byteArrayToBitmap(byteArray) }

        if (bitmap != null) {
            Box(modifier = Modifier.fillMaxWidth()) {
                Image(
                    bitmap = bitmap.asImageBitmap(),
                    contentDescription = label,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(300.dp)
                        .padding(8.dp)
                )
                IconButton(
                    onClick = onDelete,
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(4.dp)
                ) {
                    Icon(Icons.Default.Delete, contentDescription = "Delete $label")
                }
            }
        } else {
            // Show an error if the byte array is invalid
            Text("Error displaying $label. Data might be corrupt.")
        }
    }
}

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


