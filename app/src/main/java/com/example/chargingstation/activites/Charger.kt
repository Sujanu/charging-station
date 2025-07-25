package com.example.chargingstation.activites

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.chargingstation.ChargingStation
import com.example.chargingstation.activites.ui.theme.ChargingStationTheme
import com.example.chargingstation.model.ChargerData
import com.example.chargingstation.model.ChargingStationData
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.foundation.layout.*

class Charger : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val dbHelper = ChargingStation(this)
        val stationId = intent.getIntExtra("station_id", -1)

        val station = if (stationId != -1) {
            dbHelper.getChargerById(stationId)

        } else {
            null
        }

        val allStations = dbHelper.getAllChargingStations()
        allStations.forEach {
            Log.d("StationData", it.toString())
        }

        enableEdgeToEdge()
        setContent {
            ChargingStationTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    ChargerScreen(db = dbHelper, station = station)
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChargerScreen(db: ChargingStation?, station: ChargerData? = null) {

    var charger by remember { mutableStateOf("") }
    var chargerMake by remember { mutableStateOf(station?.chargerMake ?: "") }
    var chargerType by remember { mutableStateOf(station?.chargerType ?: "") }
    var chargerCost by remember { mutableStateOf(station?.chargerCost?.toString() ?: "") }
    var chargerCapacity by remember { mutableStateOf(station?.chargerCapacity ?: "") }

    val chargerFocus = remember { FocusRequester() }
    val chargerMakeFocus = remember { FocusRequester() }
    val chargerTypeFocus = remember { FocusRequester() }
    val chargerCostFocus = remember { FocusRequester() }
    val chargerCapacityFocus = remember { FocusRequester() }

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
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(innerPadding)
                .padding(16.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start
        ) {
            Text(text = ("Charger"))

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = charger,
                onValueChange = { charger = it },
                label = { Text("Charger No ") },
                singleLine = true,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Next
                ),
                keyboardActions = KeyboardActions(onNext = { chargerCapacityFocus.requestFocus() }),
                modifier = Modifier
                    .fillMaxWidth()
                    .focusRequester(chargerFocus)
            )

            OutlinedTextField(
                value = chargerCapacity,
                onValueChange = { chargerCapacity = it },
                label = { Text("Charger Capacity ") },
                singleLine = true,
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                keyboardActions = KeyboardActions(onNext = { chargerMakeFocus.requestFocus() }),
                modifier = Modifier
                    .fillMaxWidth()
                    .focusRequester(chargerCapacityFocus)
            )

            OutlinedTextField(
                value = chargerMake,
                onValueChange = { chargerMake = it },
                label = { Text("Charger Made") },
                singleLine = true,
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                keyboardActions = KeyboardActions(onNext = { chargerTypeFocus.requestFocus() }),
                modifier = Modifier
                    .fillMaxWidth()
                    .focusRequester(chargerMakeFocus)
            )

            ChargerTypeDropdown(
                selectedType = chargerType,
                onTypeSelected = { chargerType = it }
            )

            OutlinedTextField(
                value = chargerCost,
                onValueChange = { chargerCost = it },
                label = { Text("Charger Cost") },
                singleLine = true,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Next
                ),
                keyboardActions = KeyboardActions(onNext = { chargerFocus.requestFocus() }),
                modifier = Modifier
                    .fillMaxWidth()
                    .focusRequester(chargerCostFocus)
            )

            Button(
                onClick = {
                    if(
                        charger.isNotEmpty() &&
                        chargerCapacity.isNotEmpty() &&
                        chargerMake.isNotEmpty() &&
                        chargerType.isNotEmpty() &&
                        chargerCost.isNotEmpty()
                    ){

                        val chargerData = ChargerData(
                            id = station?.id ?: 0,
                            charger = charger.toInt(),
                            chargerCapacity = chargerCapacity.toString(),
                            chargerMake = chargerMake,
                            chargerType = chargerType,
                            chargerCost = chargerCost.toLong()
                        )
// Save to DB
                        if (station == null) {
                            // Add new charger
                            val id = db?.addCharger(chargerData)?: -1L
                            if (id != -1L) {
                                Toast.makeText(context, "Charger Added Successfully!", Toast.LENGTH_SHORT).show()
                                charger=""
                                chargerCapacity=""
                                chargerType=""
                                chargerMake=""
                                chargerCost=""

                            } else {
                                Toast.makeText(context, "Failed to Add Charger", Toast.LENGTH_SHORT).show()
                            }
                        } else {
                            // Update existing charger
                            chargerData.id = station.id
                            val success = db?.updateCharger(chargerData) ?: false
                            if (success) {
                                Toast.makeText(context, "Charger Updated Successfully!", Toast.LENGTH_SHORT).show()
                            } else {
                                Toast.makeText(context, "Failed to Update Charger", Toast.LENGTH_SHORT).show()
                            }
                        }
                    } else {
                        Toast.makeText(context, "Please fill all fields", Toast.LENGTH_SHORT).show()
                    }
                }
            ) {
                Text("Save")
            } /////////////////////////// BUTTON ///////////////////////////


        } /////////////////////////// COLUMN ///////////////////////////
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChargerTypeDropdown(
    selectedType: String,
    onTypeSelected: (String) -> Unit
) {
    val options = listOf("Level 1","Level 2", "Level 3 (DC Fast Charging)")
    var expanded by remember { mutableStateOf(false) }

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = !expanded }
    ) {
        OutlinedTextField(
            value = selectedType,
            onValueChange = {},
            readOnly = true,
            label = { Text("Charger Type") },
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
            },
            modifier = Modifier
                .menuAnchor()
                .fillMaxWidth()
        )

        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            options.forEach { selectionOption ->
                DropdownMenuItem(
                    text = { Text(selectionOption) },
                    onClick = {
                        onTypeSelected(selectionOption)
                        expanded = false
                    }
                )
            }
        }
    }
}
