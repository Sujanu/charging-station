package np.com.softwel.suswa_nwash.activites

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import np.com.softwel.suswa_nwash.ui.theme.SUSWA_NWASHTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()
        setContent {
            SUSWA_NWASHTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    DisplayScreen()
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true)
@Composable
fun DisplayScreen() {
    val context = LocalContext.current

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("SUSWA NWASH") }
            )
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Text(
                    text = "Report Type",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 20.sp
                )

                Button(
                    onClick = {
                        context.startActivity(Intent(context, MonthlyReport::class.java))
                    },
                    modifier = Modifier.fillMaxWidth(0.8f),
                    shape = RectangleShape
                ) {
                    Text("Monthly Reporting")
                }

                Button(
                    onClick = {
                        context.startActivity(Intent(context, FieldSurvey::class.java))
                    },
                    modifier = Modifier.fillMaxWidth(0.8f),
                    shape = RectangleShape
                ) {
                    Text("Field Survey")
                }

            }
        }
    }
}
