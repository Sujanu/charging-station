package com.example.chargingstation.activites


import android.content.Intent
import android.os.Bundle
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
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
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
                }
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true)
@Composable
fun MainScreen(db: ChargingStation? = null) {

    val context = LocalContext.current

    var owner by remember { mutableStateOf("") }
    var disp by remember { mutableStateOf("") }

    val options = listOf(
        "Charging Station Description",
        "Charger 1",
        "Charger 2",
        "Charger 3",
        "Station Info"
    )
    var expanded by remember { mutableStateOf(false) }
    var selectedOptionText by remember { mutableStateOf(options[0]) }
    var showDialog by remember { mutableStateOf(false) }

    val optionAdd =
        listOf("Station Info", "Charger 1", "Charger 2", "Charger 3", "Station Description")
    var expandedAdd by remember { mutableStateOf(false) }
    var selectedOptionTextAdd by remember { mutableStateOf(options[0]) }


    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Charging Station") }
            )
        },


        /////////////////// **************** bottom bar *************** ///////////////////
        bottomBar = {
            BottomAppBar(
                actions = {

                    IconButton(
                        onClick = {
                            context.startActivity(Intent(context, Station1::class.java ))
                        },
                        modifier = Modifier.fillMaxWidth()
                    )
                    {
                        Icon(
                            imageVector = Icons.Filled.Add,
                            contentDescription = "Add"
                        )

                    }
                }
            )

        }

        /////////////////

    )
    { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .verticalScroll(rememberScrollState())
                .padding(innerPadding)
                .padding(16.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Spacer(modifier = Modifier.height(16.dp))
            OutlinedTextField(
                value = owner,
                onValueChange = {owner = it},
                label = { Text("owner") }
            )
            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}