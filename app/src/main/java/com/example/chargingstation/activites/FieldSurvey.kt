package np.com.softwel.suswa_nwash.activites

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.chargingstation.ui.theme.ChargingStationTheme
import np.com.softwel.suswa_nwash.activites.ui.theme.SUSWA_NWASHTheme

class FieldSurvey : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ChargingStationTheme  {
                Surface(modifier = Modifier.fillMaxSize()) {
                    Survey()
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun Survey() {
    Scaffold { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(innerPadding)
                .padding(16.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start
        ) {
            TechnicalDetails()
            HorizontalDivider(modifier = Modifier.padding(vertical = 16.dp)) // Add a divider for separation
            SocialDetails()
        }
    }
}


@Composable
fun TechnicalDetails() {
    // You can remove the Scaffold and Box from here since Survey() now handles the main layout
    // and scrolling.

    var FieldVisitDate = remember { mutableStateOf("") }
    var lgName = remember { mutableStateOf("") }
    var ward = remember { mutableStateOf("") }
    var schemeName = remember { mutableStateOf("") }
    var beneficiaryHHs = remember { mutableStateOf("") }
    var beneficiaryPop = remember { mutableStateOf("") }

    var structureName = remember { mutableStateOf("") }
    var majorTechnicalIssues = remember { mutableStateOf("") }
    var proposedSolutions = remember { mutableStateOf("") }

    var structurePhoto = remember { mutableStateOf("") }
    var selectedStructure by remember { mutableStateOf("") }

    var structureOption = listOf(
        "स्रोत तथा इन्टेक",
        "कलेक्सन च्याम्बर",
        "डिस्ट्रीव्युसन च्याम्बर",
        "एअर भल्भ च्याम्बर",
        "वास आउट भल्भ च्याम्बर",
        "आई.सि.",
        "मुख्य पाइप लाइन",
        "रिजर्वभ्वायर टंकी",
        "धारा पानी वितरण च्याम्बर(जक्सन बक्स)",
        "सेक्सनल भल्भ च्याम्बर",
        "शाखा पाइप लाइन",
        "सोलार प्यानल (सोलार पावर पोइन्ट)",
        "विधुत ट्रान्सफर्मर (विधुतिय पावर पोइन्ट)",
        "पम्प स्टेशन (केशिगं पाइप)",
        "ओभरहेड लाइन (पम्पिगं-ओभर हेड टंकी)",
        "पाइपलाइन क्रसिंग",
        "इनलाइन क्लोरिनेसन",
        "धारा (सामुदायिक/संस्थागत)",
        "बि.पी.टि",
        "काँडे तार (घेराबार)",
        "अन्य",

        )

    Column(modifier = Modifier.fillMaxWidth()) { // Use fillMaxWidth for the column inside
        Text(
            text = "Technical Details",
            style = MaterialTheme.typography.headlineSmall, // Use MaterialTheme typography
            modifier = Modifier.padding(bottom = 8.dp)
        )

        Card(modifier = Modifier.fillMaxWidth()) { // Use fillMaxWidth for the card
            Column(modifier = Modifier.padding(16.dp))
            { // Add padding to the card's content
                Column {
                    Text("Field Visit Date")

                }

                Spacer(modifier = Modifier.height(8.dp))


                Text("LG Name")
                Spacer(modifier = Modifier.height(8.dp))


                Text("Select Ward No")
                Spacer(modifier = Modifier.height(8.dp))


                Text("Scheme Name")
                Spacer(modifier = Modifier.height(8.dp))


                Text("Beneficiary HHS")
                Spacer(modifier = Modifier.height(8.dp))


                Text("Beneficiary Pop")
                Spacer(modifier = Modifier.height(8.dp))


                Text("Structure Name")
                CustomDropdown(
                    label = "",
                    options = structureOption,
                    selectedOption = selectedStructure,
                    onOptionSelected = { selectedStructure = it }
                )

            }
        }
    }
}

@Composable
fun SocialDetails() {
    // Similar to TechnicalDetails, remove the Scaffold and Box.
    // The state variables are still needed for your actual UI logic.

    var fuctionalStatus = remember { mutableStateOf("") }
    var WUSCregistrationRenewal = remember { mutableStateOf("") }
    var WUSCcompleteness = remember { mutableStateOf("") }
    var WUSCmeeting = remember { mutableStateOf("") }
    var VMWappointmentMobilization = remember { (mutableStateOf("")) }
    var WSPplanPreparationImplementation = remember { mutableStateOf("") }
    var OMprocedureImplementation = remember { mutableStateOf("") }
    var AGM = remember { mutableStateOf("") }
    var SparePartManagement = remember { mutableStateOf("") }
    var OMandWSPplanAnnualReview = remember { mutableStateOf("") }
    var AccountManagement = remember { mutableStateOf("") }
    var AffiliationWithaCooperative = remember { mutableStateOf("") }
    var OMfundStatus = remember { mutableStateOf("") }

    var IsWaterQuantityAdequate = remember { mutableStateOf("") }
    var IsWaterAccessibleWithin = remember { mutableStateOf("") }
    var IsWaterAvailableAllYear = remember { mutableStateOf("") }
    var DoesWterQuality = remember { mutableStateOf("") }

    var expanded by remember { mutableStateOf(false) }

    var YearlyIncomeExpenditureEatio = remember { mutableStateOf("") }
    var RegularSSCreporting = remember { mutableStateOf("") }
    var ActiveOMfundAndRegularTariffCollection = remember { mutableStateOf("") }

    var selcetedFunctional by remember { mutableStateOf("") }
    var selcetedRegistrationrenewal by remember { mutableStateOf("") }
    var selcetedWUSCmeeting by remember { mutableStateOf("") }
    var selcetedWUSCcompleteness by remember { mutableStateOf("") }
    var selcetedVMWMobilization by remember { mutableStateOf("") }
    var selcetedPlan by remember { mutableStateOf("") }
    var selcetedAGM by remember { mutableStateOf("") }
    var selcetedReview by remember { mutableStateOf("") }
    var selcetedManagement by remember { mutableStateOf("") }
    var selectedProcedure by remember { mutableStateOf("") }
    var selectedPart by remember { mutableStateOf("") }
    var selectedAffiliation by remember { mutableStateOf("") }
    var selectedTraffic by remember { mutableStateOf("") }
    var selectedFund by remember { mutableStateOf("") }

    var selectedQuantity by remember { mutableStateOf("") }
    var selectedAccessibility by remember { mutableStateOf("") }
    var selectedQuality by remember { mutableStateOf("") }
    var selectedAvailability by remember { mutableStateOf("") }
    var selectedRatio by remember { mutableStateOf("") }
    var selectedSSC by remember { mutableStateOf("") }


    val yesnoOptions = listOf("Yes", "No")

    Column(modifier = Modifier.fillMaxWidth()) { // Use fillMaxWidth for the column inside
        Text(
            text = "Social Details",
            style = MaterialTheme.typography.headlineSmall,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        Card(modifier = Modifier.fillMaxWidth()) { // Use fillMaxWidth for the card
            Column(modifier = Modifier.padding(16.dp)) { // Add padding to the card's content
                Text("Functional Status")
                CustomDropdown(
                    label = "",
                    options = listOf("Functional", "Functional needs repair", "not Functional"),
                    selectedOption = selcetedFunctional,
                    onOptionSelected = { selcetedFunctional = it }
                )
                ///////////////////////////////////////////////////////////////////
                Spacer(modifier = Modifier.height(8.dp))


                Text("WUSC Registration/Renewal")
                CustomDropdown(
                    label = "",
                    options = yesnoOptions,
                    selectedOption = selcetedRegistrationrenewal,
                    onOptionSelected = { selcetedRegistrationrenewal = it }
                )
                ///////////////////////////////////////////////////////////////////
                Spacer(modifier = Modifier.height(8.dp))


                Text("WUSC Completeness")
                CustomDropdown(
                    label = "",
                    options = yesnoOptions,
                    selectedOption = selcetedWUSCcompleteness,
                    onOptionSelected = { selcetedWUSCcompleteness = it }
                )
                ///////////////////////////////////////////////////////////////////
                Spacer(modifier = Modifier.height(8.dp))


                Text("WUSC meeting regularity")
                CustomDropdown(
                    label = "",
                    options = listOf("Regular", "As per requirement", "No meeting"),
                    selectedOption = selcetedWUSCmeeting,
                    onOptionSelected = { selcetedWUSCmeeting = it }
                )
                ///////////////////////////////////////////////////////////////////
                Spacer(modifier = Modifier.height(8.dp))


                Text("VMW appointment and mobilization")
                CustomDropdown(
                    label = "",
                    options = listOf("Appointed and Mobilized","Appointed but not mobilized", "No VMW"),
                    selectedOption = selcetedVMWMobilization,
                    onOptionSelected = { selcetedVMWMobilization = it }
                )

                ///////////////////////////////////////////////////////////////////
                Spacer(modifier = Modifier.height(8.dp))


                Text("WSP plan preparation and implementation")
                CustomDropdown(
                    label = "",
                    options = listOf("Plan prepared and implemented"," plan prepared"," plan not prepared"),
                    selectedOption = selcetedPlan,
                    onOptionSelected = { selcetedPlan = it }
                )
                ///////////////////////////////////////////////////////////////////
                Spacer(modifier = Modifier.height(8.dp))


                Text("O&M procedure implementation")
                CustomDropdown(
                    label = "",
                    options = listOf("procedure implemented", "no procedure"),
                    selectedOption = selectedProcedure,
                    onOptionSelected = { selectedProcedure = it }
                )
                ///////////////////////////////////////////////////////////////////
                Spacer(modifier = Modifier.height(8.dp))


                Text("AGM")
                CustomDropdown(
                    label = "",
                    options = listOf("AGM happened in last 12 months", "No AGM  "),
                    selectedOption = selcetedAGM,
                    onOptionSelected = { selcetedAGM = it }
                )
                ///////////////////////////////////////////////////////////////////
                Spacer(modifier = Modifier.height(8.dp))


                Text("Spare part management")
                CustomDropdown(
                    label = "",
                    options = listOf("well managed","no spare parts"),
                    selectedOption = selectedPart,
                    onOptionSelected = { selectedPart = it }
                )
                ///////////////////////////////////////////////////////////////////
                Spacer(modifier = Modifier.height(8.dp))


                Text("O&M and WSP plan annual review")
                CustomDropdown(
                    label = "",
                    options = listOf("Reviewed", "Not reviewed"),
                    selectedOption = selcetedReview,
                    onOptionSelected = { selcetedReview = it }
                )
                ///////////////////////////////////////////////////////////////////
                Spacer(modifier = Modifier.height(8.dp))


                Text("Account management")
                CustomDropdown(
                    label = "",
                    options = listOf(" Good" ,"Satisfactory","Poor"),
                    selectedOption = selcetedManagement,
                    onOptionSelected = { selcetedManagement = it }
                )
                ///////////////////////////////////////////////////////////////////
                Spacer(modifier = Modifier.height(8.dp))


                Text("Affiliation with a cooperative")
                CustomDropdown(
                    label = "",
                    options = listOf("Affiliated", "Not Affiliated"),
                    selectedOption = selectedAffiliation,
                    onOptionSelected = { selectedAffiliation = it }
                )
                ///////////////////////////////////////////////////////////////////
                Spacer(modifier = Modifier.height(8.dp))


                Text("Active O&M fund and regular tariff collection")
                CustomDropdown(
                    label = "",
                    options = listOf("Active O&M fund"," Regular water tariff"," both ","none of them"),
                    selectedOption = selectedTraffic,
                    onOptionSelected = { selectedTraffic = it }
                )
                ///////////////////////////////////////////////////////////////////
                Spacer(modifier = Modifier.height(8.dp))


                Text("O&M fund status")
                CustomDropdown(
                    label = "",
                    options = listOf("Locally mobilized/Cash in hand ","deposited in the bank","Deposited in the cooperative"),
                    selectedOption = selectedFund,
                    onOptionSelected = { selectedFund = it }
                )
                ///////////////////////////////////////////////////////////////////
                Spacer(modifier = Modifier.height(8.dp))


                Text("Is water quantity adequate?")
                CustomDropdown(
                    label = "",
                    options = yesnoOptions,
                    selectedOption = selectedQuantity,
                    onOptionSelected = { selectedQuantity = it }
                )
                ///////////////////////////////////////////////////////////////////
                Spacer(modifier = Modifier.height(8.dp))


                Text("Is water accessible within 15 min?")
                CustomDropdown(
                    label = "",
                    options = yesnoOptions,
                    selectedOption = selectedAccessibility,
                    onOptionSelected = { selectedAccessibility = it }
                )
                ///////////////////////////////////////////////////////////////////
                Spacer(modifier = Modifier.height(8.dp))


                Text("Is water available all year round?")
                CustomDropdown(
                    label = "",
                    options = yesnoOptions,
                    selectedOption = selectedAvailability,
                    onOptionSelected = { selectedAvailability= it }
                )
                ///////////////////////////////////////////////////////////////////
                Spacer(modifier = Modifier.height(8.dp))


                Text("Does water quality as per NDWQS?")
                CustomDropdown(
                    label = "",
                    options = yesnoOptions,
                    selectedOption = selectedQuality,
                    onOptionSelected = { selectedQuality = it }
                )
                ///////////////////////////////////////////////////////////////////
                Spacer(modifier = Modifier.height(8.dp))


                Text(text ="Yearly income and expenditure ratio?",
                    modifier = Modifier.height(26.dp)
                )
                CustomDropdown(
                    label = "",
                    options = listOf("less than 20%", " 21% - 50%"," 51% - 90%" ," more than 90" ),
                    selectedOption = selectedRatio,
                    onOptionSelected = { selectedRatio = it }
                )
                ///////////////////////////////////////////////////////////////////
                Spacer(modifier = Modifier.height(8.dp))


                Text("RegularSSC reporting")
                CustomDropdown(
                    label = "",
                    options = listOf("Regular reporting as defined"," Irregular reporting"," No reporting at all"),
                    selectedOption = selectedSSC,
                    onOptionSelected = { selectedSSC = it }
                )

            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomDropdown(
    label: String,
    options: List<String>,
    selectedOption: String,
    onOptionSelected: (String) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    Box(modifier = Modifier
        .fillMaxWidth()
        .padding(vertical = 8.dp)) {

        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = { expanded = !expanded }
        ) {
            OutlinedTextField(
                value = selectedOption,
                onValueChange = {},
                readOnly = true,
                label = { if (label.isNotEmpty()) Text(label) },
                trailingIcon = {
                    ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
                },
                modifier = Modifier
                    .menuAnchor()
                    .height(26.dp)
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
                            onOptionSelected(selectionOption)
                            expanded = false
                        }
                    )
                }
            }
        }
    }
}
