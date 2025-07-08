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

class Station1 : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val dbHelper = ChargingStation(this)
        enableEdgeToEdge()
        setContent {
            ChargingStationTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    ChargerStation1(db = dbHelper)
                }
            }
        }
    }
}



@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true)
@Composable
fun ChargerStation1(db: ChargingStation? = null) {

    var chargerCost1 by remember { mutableStateOf("") }
    var chargerCapacity1 by remember { mutableStateOf("") }
    var charger1 by remember { mutableStateOf("") }
    var chargerType1 by remember { mutableStateOf("") }
    var chargerMake1 by remember { mutableStateOf("") }

    val context = LocalContext.current

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Charging Station Charger 1") },
                actions = {
                    val  context = LocalContext.current

                    Button(onClick = {
                        context.startActivity(Intent(context, MainActivity::class.java))
                    }) {
                        Text("Home")
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
                        Text(text = "Station Info")
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
        )
        {
            Text(text = ("Charger Information"))

            OutlinedTextField(
                value = chargerCapacity1,
                onValueChange = { chargerCapacity1 = it },
                label = { Text("Charger Capacity ") }
            )
            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = charger1,
                onValueChange = { charger1 = it },
                label = { Text("Charger No ") }
            )
            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = chargerMake1,
                onValueChange = { chargerMake1 = it },
                label = { Text("Charger made") }
            )

            OutlinedTextField(
                value = chargerType1,
                onValueChange = { chargerType1 = it },
                label = { Text("Charger type ") }
            )
            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = chargerCost1,
                onValueChange = { chargerCost1 = it },
                label = { Text("Charger Cost") }
            )
            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Button(onClick = {
                    if (
                        charger1.isNotEmpty() && chargerCapacity1.isNotEmpty() && chargerCost1.isNotEmpty() &&
                        chargerMake1.isNotEmpty() && chargerType1.isNotEmpty()
                    ) {
                        val chargerno1INt = chargerCost1.toLong()
                        val chargercostI1nt = chargerCost1.toLong()

                        db?.insertCharger1(
                            chargerCapacity1 = chargerCapacity1,
                            chargerMake1 = chargerMake1,
                            chargerType1 = chargerType1,
                            charger1 = chargerno1INt,
                            chargerCost1 = chargercostI1nt
                        )

                        charger1 = ""
                        chargerCost1 = ""
                        chargerMake1 = ""
                        chargerMake1  = ""
                        chargerCapacity1 = ""

                        Toast.makeText(context, "SAVED", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(context, "All field are not filled", Toast.LENGTH_SHORT)
                            .show()
                    }
                })
                {
                    Text(text = "SAVE")
                }

            }
        }

    }

}