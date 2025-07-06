package com.example.chargingstation.activites


import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val dbHelper = ChargingStation(this)
        val db = dbHelper.writableDatabase
        enableEdgeToEdge()
        setContent {
            ChargingStationTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    MainScreen(db = dbHelper)
                    StationEditScreen(db = dbHelper, stationId = 1)
                }
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true)
@Composable
fun MainScreen(db: ChargingStation? = null) {

    var station_name by remember { mutableStateOf("") }
    var owner by remember { mutableStateOf("") }
    var contact by remember { mutableStateOf("") }
    var location by remember { mutableStateOf("") }


    var latitude by remember { mutableStateOf("") }
    var longitude by remember { mutableStateOf("") }
    var elevation by remember { mutableStateOf("") }

    var charger_cost1 by remember { mutableStateOf("") }
    var charger_capacity1 by remember { mutableStateOf("") }
    var charger_1 by remember { mutableStateOf("") }
    var charger_type1 by remember { mutableStateOf("") }
    var charger_make1 by remember { mutableStateOf("") }


    var charger_capacity2  by remember { mutableStateOf("") }
    var charger_2 by remember { mutableStateOf("") }
    var charger_type2 by remember { mutableStateOf("") }
    var charger_make2 by remember { mutableStateOf("") }
    var charger_cost2 by remember { mutableStateOf("") }


    var charger_capacity3 by remember { mutableStateOf("") }
    var charger_3 by remember { mutableStateOf("") }
    var charger_type3 by remember { mutableStateOf("") }
    var charger_make3 by remember { mutableStateOf("") }
    var charger_cost3 by remember { mutableStateOf("") }


    val context = LocalContext.current

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Charging Station") }
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
            Row (modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween) {
                Button(onClick = {})
                {
                    Text(text = "ADD")
                }

                Button(onClick = {})
                {
                    Text(text = "EDIT")
                }
            }

            TextField(value = owner,
                onValueChange = {owner = it},
                label = { Text("heeeeeeeeeeeeyyyyyyyyyyy") }
                )

        }

        }
    }
@Composable
fun StationEditScreen(db: ChargingStation, stationId: Int) {
    var stationName by remember { mutableStateOf("") }

    // Load the station name only once
    LaunchedEffect(stationId) {
        stationName = db.getStationNameById(stationId) ?: ""
    }

    OutlinedTextField(
        value = stationName,
        onValueChange = { stationName = it },
        label = { Text("Station Name") },
        modifier = Modifier.fillMaxWidth()
    )
}
