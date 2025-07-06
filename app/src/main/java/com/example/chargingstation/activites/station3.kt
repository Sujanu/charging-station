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

class station3 : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val dbHelper = ChargingStation(this)
        val db = dbHelper.writableDatabase
        enableEdgeToEdge()
        setContent {
            ChargingStationTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    ChargerStation3(db = dbHelper)
                }

               }
            }
        }
    }


@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true)
@Composable
fun ChargerStation3(db: ChargingStation? = null) {

    var charger_cost_3 by remember { mutableStateOf("") }
    var charger_capacity_3 by remember { mutableStateOf("") }
    var charger_3 by remember { mutableStateOf("") }
    var charger_type_3 by remember { mutableStateOf("") }
    var charger_make_3 by remember { mutableStateOf("") }

    val context = LocalContext.current

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Charging Station Charger 2") }
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
            Text(text = ("Charger Information"))

            OutlinedTextField(
                value = charger_capacity_3,
                onValueChange = { charger_capacity_3 = it },
                label = { Text("Charger Capacity 1") }
            )
            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = charger_3,
                onValueChange = { charger_3 = it },
                label = { Text("Charger No ") }
            )
            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = charger_make_3,
                onValueChange = { charger_make_3 = it },
                label = { Text("Charger made") }
            )

            OutlinedTextField(
                value = charger_type_3,
                onValueChange = { charger_type_3 = it },
                label = { Text("Charger type ") }
            )
            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = charger_cost_3,
                onValueChange = { charger_cost_3 = it },
                label = { Text("Charger Cost") }
            )
            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Button(onClick = {
                    if (
                        charger_3.isNotEmpty() && charger_capacity_3.isNotEmpty() && charger_cost_3.isNotEmpty() &&
                        charger_make_3.isNotEmpty() && charger_type_3.isNotEmpty()
                    ) {
                        val chargerno1INt = charger_cost_3.toInt()
                        val chargercostI1nt = charger_cost_3.toInt()

                        db?.insertCharger1(
                            charger_capacity_1 = charger_capacity_3,
                            charger_make_1 = charger_make_3,
                            charger_type_1 = charger_type_3,
                            charger_no_1 = chargerno1INt,
                            charger_cost_1 = chargercostI1nt
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

            }

        }

    }

}
