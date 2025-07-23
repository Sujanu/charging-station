package com.example.chargingstation.activites

import android.Manifest
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
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
import androidx.compose.foundation.layout.statusBarsPadding
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
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.chargingstation.ChargingStation
import com.example.chargingstation.R
import com.example.chargingstation.ui.theme.ChargingStationTheme
import kotlinx.coroutines.delay

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
 @Composable
fun MainScreen(db: ChargingStation ) {

    val context = LocalContext.current

    val db = ChargingStation(context)

    Scaffold(

    ) { innerPadding ->
        Box(modifier = Modifier.fillMaxSize()) {
            Image(
                painter = painterResource(id = R.drawable.car),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.matchParentSize()
            )

            TopAppBar(
                title = { Text("Charging Station", color = Color.Black) },
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .statusBarsPadding()
                    .fillMaxWidth(),
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.Transparent
                )


            )

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(innerPadding)
                    .padding(16.dp),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                AllStationsListScreen(db)

            }

            BottomAppBar(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .fillMaxWidth(),
                containerColor = Color.White.copy(alpha = 0.9f), // Optional semi-transparent bg
                actions = {
                    IconButton(
                        onClick = {
                            context.startActivity(Intent(context, Station1::class.java))
                        },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("ADD")
                    }
                }
            )

        }
    }
}

/////////////////// **************** Show Data **************** ///////////////////

@Composable
fun AllStationsListScreen(db: ChargingStation) {

    val stations = remember { (db.getAllChargingStations()) }

    Spacer(modifier = Modifier.height(80.dp))


    var sn = 1

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
                        .align(Alignment.CenterHorizontally)
                        .padding(8.dp)
                        .background(color = Color.White)
                        .pointerInput(Unit) {
                            detectTapGestures(
                                onDoubleTap = {
                                    val intent = Intent(context, DetailPage::class.java)
                                    intent.putExtra("station_id", station.id)
                                    context.startActivity(intent)
                                }
                            )
                        },

                    elevation = CardDefaults.cardElevation(4.dp),

                    ) {
                    Row(
                        modifier = Modifier.padding(12.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {


                        Text(
                            "$sn Station:" +
                                    " ${station.stationName}",
                            style = MaterialTheme.typography.titleMedium,
                            fontSize = 20.sp
                        )

                        /////////////////// *************** Edit Data *************** ///////////////////

                        Spacer(modifier = Modifier.weight(1f))
                    }
                    sn = sn + 1
                }
            }
        }
    }
}