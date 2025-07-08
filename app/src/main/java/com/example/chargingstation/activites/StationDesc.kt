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
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
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
import com.example.chargingstation.activites.ui.theme.ChargingStationTheme

class StationDesc : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val dbHelper = ChargingStation(this)
        enableEdgeToEdge()
        setContent {
            ChargingStationTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    StationDesc(db = dbHelper)

                }
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true)
@Composable
fun StationDesc(db: ChargingStation? = null) {

    var costOfElec by remember { mutableStateOf("") }
    var avgMb by remember { mutableStateOf("") }
    var avgCb by remember { mutableStateOf("") }
    var anyChallenge by remember { mutableStateOf("") }

    val context = LocalContext.current


    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Charging Station") },
                actions = {
                    Button(onClick = {
                        context.startActivity(Intent(context, MainActivity::class.java))
                    }) {
                        Text("Home")
                    }

                    Button(onClick = {
                        context.startActivity(Intent(context, Station1::class.java))
                    }) {
                        Text(text = "Station 1")
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
                }
            )
        }
    )
    { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(innerPadding)
                .padding(16.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start
        )
        {


            OutlinedTextField(
                value = costOfElec,
                onValueChange = { costOfElec = it },
                label = { Text("Cost of Electricity per month ") }
            )

            OutlinedTextField(
                value = avgMb,
                onValueChange = { avgMb = it },
                label = { Text(text = "Average no. of Micro bus per day") }
            )

            OutlinedTextField(
                value = avgCb,
                onValueChange = { avgCb = it },
                label = { Text(text = "Average no. of Car bus per day") }
            )

            OutlinedTextField(
                value = anyChallenge,
                onValueChange = { anyChallenge = it },
                label = { Text(text = "Any challenges or issues during implementation") }
            )
            Button(
                onClick =
                    {
                        if (costOfElec.isNotEmpty() && avgCb.isNotEmpty() && avgMb.isNotEmpty() &&
                            anyChallenge.isNotEmpty()
                        ) {
                            db?.insertDesc(
                                cost_of_electricty_per_month = costOfElec.toInt(),
                                average_no_of_micro_bus_per_day = avgMb.toInt(),
                                average_no_of_car_bus_per_day = avgCb.toInt(),
                                any_challenges_or_issues_during_implementaion = anyChallenge
                            )
                            avgCb = ""
                            avgMb = ""
                            costOfElec = ""
                            anyChallenge = ""

                            Toast.makeText(context, "SAVED", Toast.LENGTH_SHORT).show()
                        } else {
                            Toast.makeText(
                                context,
                                "All field's are not filled",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
            )
            {
                Text(text = "Save")
            }

        }

    }
}