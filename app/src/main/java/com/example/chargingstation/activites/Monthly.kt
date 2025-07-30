package np.com.softwel.suswa_nwash.activites

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
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
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.chargingstation.ui.theme.ChargingStationTheme
import np.com.softwel.suswa_nwash.activites.ui.theme.SUSWA_NWASHTheme

class MonthlyReport : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ChargingStationTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    Monthly()
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
@Preview(showBackground = true)
fun Monthly() {

    var lgName = remember { mutableStateOf("") }
    var ward = remember { mutableStateOf("") }
    var year = remember { mutableStateOf("") }
    var month = remember { mutableStateOf("") }
    var schemeName = remember { mutableStateOf("") }
    var rvtsFunctional = remember { mutableStateOf("") }

    val monthOption = listOf(
        "January", "February", "March", "April", "May", "June",
        "July", "August", "September", "October", "November", "December"
    )

    val yearOption =(1950..2025).map { it.toString() }

    val rvtsOptions = listOf("Yes", "No")
    var selectedRVTS by remember { mutableStateOf("") }
    var selectedYear by remember { mutableStateOf("") }
    var selectedMonth by remember { mutableStateOf("") }


    val context = LocalContext.current

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Monthly Reporting") }
            )
        }
    )
    { innerPadding ->
        Box(modifier = Modifier.fillMaxSize())
        {
            Card(
                colors = CardDefaults.cardColors(containerColor = Color(0xFFF8F9FA)),
            ) {
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
                    Text("LG Name:")

                    OutlinedTextField(
                        value = lgName.value,
                        onValueChange = { lgName.value = it },
                        label = { Text("LOG IN USER BASE") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(26.dp)

                    )
                    Spacer(modifier = Modifier.height(8.dp))


                    Text("Select Ward Nos.")

                    OutlinedTextField(
                        value = ward.value,
                        onValueChange = { ward.value = it },
                        label = { Text("LOG IN USER BASE") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(26.dp)
                    )
                    Spacer(modifier = Modifier.height(8.dp))


                    Text("Scheme Name:")

                    OutlinedTextField(
                        value = schemeName.value,
                        onValueChange = { schemeName.value = it },
                        label = { Text("LOG IN USER BASE") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(26.dp)

                    )
                    Spacer(modifier = Modifier.height(8.dp))


                    Text("Reporting Year:")

                    CustomDropdown(
                        label = "",
                        options =yearOption,
                        selectedOption = selectedYear,
                        onOptionSelected = { selectedYear = it }
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text("Reporting Month:")

                    CustomDropdown(
                        label = "",
                        options =monthOption,
                        selectedOption = selectedMonth,
                        onOptionSelected = { selectedMonth = it }
                    )
                    Spacer(modifier = Modifier.height(8.dp))


                    Text("Are all RVTs Functional")
                    CustomDropdown(
                        label = "",
                        options =rvtsOptions,
                        selectedOption = selectedRVTS,
                        onOptionSelected = { selectedRVTS = it }
                    )
                }
            }
            Card {
                Column {
                    Row {
                        Text("RVTS Location Name")
                        Text("Functional Status")
                    }
                }
            }
        }
    }
}
