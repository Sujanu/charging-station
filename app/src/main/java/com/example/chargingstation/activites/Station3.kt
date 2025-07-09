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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.chargingstation.ChargingStation
import com.example.chargingstation.ui.theme.ChargingStationTheme

class Station3 : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val dbHelper = ChargingStation(this)
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

    var chargerCost3 by remember { mutableStateOf("") }
    var chargerCapacity3 by remember { mutableStateOf("") }
    var charger3 by remember { mutableStateOf("") }
    var chargerType3 by remember { mutableStateOf("") }
    var chargerMake3 by remember { mutableStateOf("") }

    val context = LocalContext.current

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Charging Station Charger 3") },
                actions = {

                    IconButton(onClick = {
                        context.startActivity(Intent(context, MainActivity::class.java))
                    }) {
                        Icon(
                            imageVector = Icons.Filled.Home,
                            contentDescription = "Home"
                        )
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
          //////////////////////////////////////////////////////////////

            OutlinedTextField(
                value = chargerCapacity3,
                onValueChange = { chargerCapacity3 = it },
                label = { Text("Charger Capacity ") }
            )
            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = charger3,
                onValueChange = { charger3 = it },
                label = { Text("Charger No ") }
            )
            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = chargerMake3,
                onValueChange = { chargerMake3 = it },
                label = { Text("Charger made") }
            )

            OutlinedTextField(
                value = chargerType3,
                onValueChange = { chargerType3 = it },
                label = { Text("Charger type ") }
            )
            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = chargerCost3,
                onValueChange = { chargerCost3 = it },
                label = { Text("Charger Cost") }
            )
            //////////////////////////////////////////////////////////////
            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Button(onClick = {
                    if (
                        charger3.isNotEmpty() && chargerCapacity3.isNotEmpty() && chargerCost3.isNotEmpty() &&
                        chargerMake3.isNotEmpty() && chargerType3.isNotEmpty()
                    ) {
                        val chargerno1INt = chargerCost3.toLong()
                        val chargercostI1nt = chargerCost3.toLong()

                        db?.insertCharger3(
                            chargerCapacity3 = chargerCapacity3,
                            chargerMake3 = chargerMake3,
                            chargerType3 = chargerType3,
                            charger3 = chargerno1INt,
                            chargerCost3 = chargercostI1nt
                        )
                        charger3 = ""
                        chargerCost3 = ""
                        chargerMake3 = ""
                        chargerType3 = ""
                        chargerCapacity3 = ""

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

