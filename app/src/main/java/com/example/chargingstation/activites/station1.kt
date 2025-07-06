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

class station1 : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val dbHelper = ChargingStation(this)
        val db = dbHelper.writableDatabase
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

    var charger_cost_1 by remember { mutableStateOf("") }
    var charger_capacity_1 by remember { mutableStateOf("") }
    var charger_1 by remember { mutableStateOf("") }
    var charger_type1 by remember { mutableStateOf("") }
    var charger_make1 by remember { mutableStateOf("") }

    val context = LocalContext.current

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Charging Station Charger 1") }
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
                value = charger_capacity_1,
                onValueChange = { charger_capacity_1 = it },
                label = { Text("Charger Capacity 1") }
            )
            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = charger_1,
                onValueChange = { charger_1 = it },
                label = { Text("Charger No ") }
            )
            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = charger_make1,
                onValueChange = { charger_make1 = it },
                label = { Text("Charger made") }
            )

            OutlinedTextField(
                value = charger_type1,
                onValueChange = { charger_type1 = it },
                label = { Text("Charger type ") }
            )
            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = charger_cost_1,
                onValueChange = { charger_cost_1 = it },
                label = { Text("Charger Cost") }
            )
            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Button(onClick = {
                    if (
                        charger_1.isNotEmpty() && charger_capacity_1.isNotEmpty() && charger_cost_1.isNotEmpty() &&
                        charger_make1.isNotEmpty() && charger_type1.isNotEmpty()
                    ) {
                        val chargerno1INt = charger_1.toInt()
                        val chargercostI1nt = charger_cost_1.toInt()

                        db?.insertCharger1(
                            charger_capacity_1 = charger_capacity_1,
                            charger_make_1 = charger_make1,
                            charger_type_1 = charger_type1,
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