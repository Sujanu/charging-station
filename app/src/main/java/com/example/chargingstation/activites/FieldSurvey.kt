package np.com.softwel.suswa_nwash.activites

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.content.ContentValues
import android.os.Bundle
import android.widget.DatePicker
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.util.Calendar
import np.com.softwel.suswa_nwash.activites.ui.theme.SUSWA_NWASHTheme
import com.example.chargingstation.activites.CameraCaptureScreen
import np.com.softwel.suswa_nwash.model.FieldData
import np.com.softwel.suswa_nwash.database.FieldSurveyDbHelper
import np.com.softwel.suswa_nwash.database.FieldSurveyDbHelper.Companion.FIELDSURVEYDATA

class FieldSurvey : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val dbHelper = FieldSurveyDbHelper(this)
        val db = dbHelper.writableDatabase

        val fieldId = intent.getIntExtra("field_id", -1)


        val field = if (fieldId != -1) {
            dbHelper.getFiledSurveyById(fieldId)

        } else {
            null
        }

        enableEdgeToEdge()
        setContent {
            SUSWA_NWASHTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    TechnicalSocialDetails(db = dbHelper, field = field)
                }
            }
        }
    }
}

@SuppressLint("DefaultLocale")
@Composable
fun TechnicalSocialDetails(db: FieldSurveyDbHelper, field: FieldData?) {

    ///////////////////////////////// Technical Details  /////////////////////////////////

    var LGName by remember { mutableStateOf(field?.lgName ?: "") }
    var report by remember { mutableStateOf(field?.report ?: "") }
    var beneficiaryHHs by remember { mutableStateOf(field?.BenHH?.toString() ?: "") }
    var beneficiaryPop by remember { mutableStateOf(field?.BenPop?.toString() ?: "") }
    var hasIssue by remember { mutableStateOf<Boolean?>(null) }
    var majorTechnicalIssues by remember { mutableStateOf(field?.issues ?: "") }
    var selectedStructure by remember { mutableStateOf(field?.structureName ?: "") }
    var selectedScheme by remember { mutableStateOf(field?.scheme ?: "") }
    var selectedWardno by remember { mutableStateOf(field?.ward.toString()) }
    var proposedSolutions by remember { mutableStateOf(field?.solution ?: "") }

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

    val context = LocalContext.current

    val calendar = Calendar.getInstance()
    val year = calendar.get(Calendar.YEAR)
    val month = calendar.get(Calendar.MONTH)
    val day = calendar.get(Calendar.DAY_OF_MONTH)
    val todayDate = remember {
        String.format("%02d/%02d/%04d", day, month + 1, year)
    }
    var fieldVisitDate by remember { mutableStateOf(todayDate) }

    val datePickerDialog = remember {
        DatePickerDialog(
            context,
            { _: DatePicker, y: Int, m: Int, d: Int ->
                fieldVisitDate = String.format("%02d/%02d/%04d", d, m + 1, y)
            },
            year,
            month,
            day
        )
    }

    ///////////////////////////////// SOCIAL DETAILS /////////////////////////////////

    var selectedFunctional by  remember { mutableStateOf(field?.functionalStatus ?:"")}
    var selectedRegistrationrenewal by  remember { mutableStateOf(field?.registrationRenewalWUSC ?:"")}
    var selectedWUSCmeeting by  remember { mutableStateOf(field?.regularityMeetingWUSC ?:"")}
    var selectedWUSCcompleteness by  remember { mutableStateOf(field?.completenessWUSC ?:"")}
    var selectedVMWMobilization by  remember { mutableStateOf(field?.appointmentMobilization ?:"")}
    var selectedPlan by  remember { mutableStateOf(field?.planPreparationImplementation ?:"")}
    var selectedAGM by  remember { mutableStateOf(field?.AGM ?:"")}
    var selectedReview by  remember { mutableStateOf(field?.annualReview ?:"")}
    var selectedManagement by  remember { mutableStateOf(field?.accountManagement ?:"")}
    var selectedProcedure by  remember { mutableStateOf(field?.OMprodureImplementation ?:"")}
    var selectedPart  by  remember { mutableStateOf(field?.sparePartManagement ?:"")}
    var selectedAffiliation by  remember { mutableStateOf(field?. affiliation?:"")}
    var selectedTraffic by  remember { mutableStateOf(field?.OMfundTraffic ?:"")}
    var selectedFund by  remember { mutableStateOf(field?.OmfundStatus?:"")}
    var selectedQuantity by  remember { mutableStateOf(field?.waterQuantity ?:"")}
    var selectedAccessibility by  remember { mutableStateOf(field?.waterAccessibility ?:"")}
    var selectedQuality by  remember { mutableStateOf(field?.waterQuality ?:"")}
    var selectedAvailability by  remember { mutableStateOf(field?.waterAvailability ?:"")}
    var selectedRatio by  remember { mutableStateOf(field?.incomeExpenditureRatio ?:"")}
    var selectedSSC by  remember { mutableStateOf(field?.SSCreporting ?:"")}

    ///////////////////////////////// ///////////////////////////////// /////////////////////////////////

    val yesnoOptions = listOf("Yes", "No")

    var showErrors by remember { mutableStateOf(false) } // ← Controls when to show errors

    Scaffold { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(innerPadding)
                .padding(16.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start
        ) {  /////////////////////////// MAIN COLUMN ///////////////////////////////////////


            Column(modifier = Modifier.fillMaxWidth())
            {////////////////////////////////// TECHNICAL DETAIL COLUMN ///////////////////////////

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
                            OutlinedTextField(
                                value = fieldVisitDate,
                                onValueChange = {},
                                label = { Text("Date") },
                                readOnly = true,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable { datePickerDialog.show() },
                                trailingIcon = {
                                    Icon(
                                        imageVector = Icons.Default.DateRange,
                                        contentDescription = "Select Date"
                                    )
                                }
                            )

                        }

                        Spacer(modifier = Modifier.height(8.dp))

                        Text("LG Name")
                        Spacer(modifier = Modifier.height(8.dp))

                        Text("Select Ward No")
                        CustomDropdown(
                            label = "List of Scheme [Selected Ward]",
                            options = listOf("1", "2", "3"),
                            selectedOption = selectedWardno,
                            required = true,
                            showError = showErrors,
                            errorMessage = "Required",
                            onSelectionChanged = { newValue -> selectedWardno = newValue }
                        )
                        Spacer(modifier = Modifier.height(8.dp))

                        Text("Scheme Name")
                        CustomDropdown(
                            label = "List of Scheme [Selected Ward]",
                            options = listOf("ktm", "lalitpur", "bkt"),
                            selectedOption = selectedScheme,
                            required = true,
                            showError = showErrors,
                            errorMessage = "Required",
                            onSelectionChanged = { newValue -> selectedScheme = newValue }
                        )
                        Spacer(modifier = Modifier.height(8.dp))

                        Text("Beneficiary HHS")
                        OutlinedTextField(
                            value = beneficiaryHHs,
                            onValueChange = { beneficiaryHHs = it },
                            label = { Text("Households") },
                            modifier = Modifier.fillMaxWidth(),
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        Text("Beneficiary Pop")
                        Text("Beneficiary Population")
                        OutlinedTextField(
                            value = beneficiaryPop,
                            onValueChange = { beneficiaryPop = it },
                            label = { Text("Population") },
                            modifier = Modifier.fillMaxWidth(),
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        Column {
                            Text("Structure Name")
                            CustomDropdown(
                                label = "",
                                options = structureOption,
                                selectedOption = selectedStructure,
                                required = true,
                                errorMessage = "Required",
                                showError = showErrors,
                                onSelectionChanged = { newValue -> selectedStructure = newValue }
                            )

                            Spacer(modifier = Modifier.height(8.dp))

                            if (selectedStructure.isNotEmpty()) {
                                Text("Is there any issue?")
                                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                                    Button(
                                        onClick = { hasIssue = true },
                                        shape = RectangleShape,
                                        enabled = hasIssue != true
                                    ) {
                                        Text("Yes")
                                    }
                                    Button(
                                        onClick = { hasIssue = false },
                                        enabled = hasIssue != false,
                                        shape = RectangleShape
                                    ) {
                                        Text("No")
                                    }
                                }

                                Spacer(modifier = Modifier.height(8.dp))
                            }

                            if (hasIssue == true) {
                                Text("Major Technical Issues")
                                Text("पानी संचालनमा देखिएका मुख्य मुख्य प्राविधिक समस्याहरु उल्लेख गर्नुहोस।")
                                OutlinedTextField(
                                    value = majorTechnicalIssues,
                                    onValueChange = { majorTechnicalIssues = it },
                                    label = { Text("Issue") },
                                    modifier = Modifier.fillMaxWidth()
                                )

                                Spacer(modifier = Modifier.height(8.dp))

                                Text("Proposed Solutions")
                                Text("समाधानको उपायहरु उल्लेख गर्दा लागत इस्टीमेट निकाल्न सहयोग पुग्ने गरी परिमाण, इकाइ र Dimension प्रष्ट रुपमा उल्लेख गर्नुपर्नेछ।")
                                OutlinedTextField(
                                    value = proposedSolutions,
                                    onValueChange = { proposedSolutions = it },
                                    label = { Text("Suggested Solution") },
                                    modifier = Modifier.fillMaxWidth()
                                )

                                Spacer(modifier = Modifier.height(8.dp))

                                Text(
                                    "Structure Photo",
                                    style = MaterialTheme.typography.titleMedium.copy(
                                        textDecoration = TextDecoration.Underline
                                    ),
                                    fontSize = 20.sp
                                )
                                CameraCaptureScreen()


                                Text(text = buildAnnotatedString {
                                    append(
                                        "संरचनाहरुको फोटोमा प्राविधिक समस्या "
                                    )
                                    withStyle(style = SpanStyle(textDecoration = TextDecoration.Underline)) {
                                        append("चर्केको,भत्केको,चुहीएको.........) देखिने गरी लिनुपर्ने।")
                                    }
                                }
                                )

                                ///////////////////////////////// Technical DETAILS /////////////////////////////////

                            }
                        }
                    }
                }
            }
            ///////////////////////////////// SOCIAL DETAILS /////////////////////////////////

            Column(modifier = Modifier.fillMaxWidth())
            { ////////////////////////////////// SOCIAL DETAIL COLUMN ///////////////////////////
                Text(
                    text = "Social Details",
                    style = MaterialTheme.typography.headlineSmall, // Use MaterialTheme typography
                    modifier = Modifier.padding(bottom = 8.dp)
                )
                Card(modifier = Modifier.fillMaxWidth()) { // Use fillMaxWidth for the card
                    Column(modifier = Modifier.padding(16.dp)) { // Add padding to the card's content
                        Text("Functional Status")
                        CustomDropdown(
                            label = "",
                            options = listOf(
                                "Functional",
                                "Functional needs repair",
                                "not Functional"
                            ),
                            selectedOption = selectedFunctional,
                            required = true,
                            errorMessage = "Required",
                            showError = showErrors,
                            onSelectionChanged = { newValue -> selectedFunctional = newValue }
                        )
                        ///////////////////////////////////////////////////////////////////
                        Spacer(modifier = Modifier.height(8.dp))

                        Text("WUSC Registration/Renewal")
                        CustomDropdown(
                            label = "",
                            options = yesnoOptions,
                            selectedOption = selectedRegistrationrenewal,
                            required = true,
                            errorMessage = "Required",
                            showError = showErrors,
                            onSelectionChanged = { newValue -> selectedRegistrationrenewal = newValue }
                        )
                        ///////////////////////////////////////////////////////////////////
                        Spacer(modifier = Modifier.height(8.dp))

                        Text("WUSC Completeness")
                        CustomDropdown(
                            label = "",
                            options = yesnoOptions,
                            selectedOption = selectedWUSCcompleteness,
                            required = true,
                            errorMessage = "Required",
                            showError = showErrors,
                            onSelectionChanged = { newValue -> selectedWUSCcompleteness = newValue }
                        )
                        ///////////////////////////////////////////////////////////////////
                        Spacer(modifier = Modifier.height(8.dp))

                        Text("WUSC meeting regularity")
                        CustomDropdown(
                            label = "",
                            options = listOf("Regular", "As per requirement", "No meeting"),
                            selectedOption = selectedWUSCmeeting,
                            required = true,
                            errorMessage = "Required",
                            showError = showErrors,
                            onSelectionChanged = { newValue -> selectedWUSCmeeting = newValue }
                        )
                        ///////////////////////////////////////////////////////////////////
                        Spacer(modifier = Modifier.height(8.dp))

                        Text("VMW appointment and mobilization")
                        CustomDropdown(
                            label = "",
                            options = listOf(
                                "Appointed and Mobilized",
                                "Appointed but not mobilized",
                                "No VMW"
                            ),
                            selectedOption = selectedVMWMobilization,
                            required = true,
                            errorMessage = "Required",
                            showError = showErrors,
                            onSelectionChanged = { newValue -> selectedVMWMobilization = newValue }
                        )

                        ///////////////////////////////////////////////////////////////////
                        Spacer(modifier = Modifier.height(8.dp))

                        Text("WSP plan preparation and implementation")
                        CustomDropdown(
                            label = "",
                            options = listOf(
                                "Plan prepared and implemented",
                                " plan prepared",
                                " plan not prepared"
                            ),
                            selectedOption = selectedPlan,
                            required = true,
                            errorMessage = "Required",
                            showError = showErrors,
                            onSelectionChanged = { newValue -> selectedPlan = newValue }
                        )
                        ///////////////////////////////////////////////////////////////////
                        Spacer(modifier = Modifier.height(8.dp))

                        Text("O&M procedure implementation")
                        CustomDropdown(
                            label = "",
                            options = listOf("procedure implemented", "no procedure"),
                            selectedOption = selectedProcedure,
                            required = true,
                            errorMessage = "Required",
                            showError = showErrors,
                            onSelectionChanged = { newValue -> selectedProcedure = newValue }
                        )
                        ///////////////////////////////////////////////////////////////////
                        Spacer(modifier = Modifier.height(8.dp))

                        Text("AGM")
                        CustomDropdown(
                            label = "",
                            options = listOf("AGM happened in last 12 months", "No AGM  "),
                            selectedOption = selectedAGM,
                            required = true,
                            errorMessage = "Required",
                            showError = showErrors,
                            onSelectionChanged = { newValue -> selectedAGM = newValue }
                        )
                        ///////////////////////////////////////////////////////////////////
                        Spacer(modifier = Modifier.height(8.dp))

                        Text("Spare part management")
                        CustomDropdown(
                            label = "",
                            options = listOf("well managed", "no spare parts"),
                            selectedOption = selectedPart,
                            required = true,
                            errorMessage = "Required",
                            showError = showErrors,
                            onSelectionChanged = { newValue -> selectedPart = newValue }
                        )
                        ///////////////////////////////////////////////////////////////////
                        Spacer(modifier = Modifier.height(8.dp))

                        Text("O&M and WSP plan annual review")
                        CustomDropdown(
                            label = "",
                            options = listOf("Reviewed", "Not reviewed"),
                            selectedOption = selectedReview,
                            required = true,
                            errorMessage = "Required",
                            showError = showErrors,
                            onSelectionChanged = { newValue -> selectedReview = newValue }
                        )
                        ///////////////////////////////////////////////////////////////////
                        Spacer(modifier = Modifier.height(8.dp))

                        Text("Account management")
                        CustomDropdown(
                            label = "",
                            options = listOf(" Good", "Satisfactory", "Poor"),
                            selectedOption = selectedManagement,
                            required = true,
                            errorMessage = "Required",
                            showError = showErrors,
                            onSelectionChanged = { newValue -> selectedManagement = newValue }
                        )
                        ///////////////////////////////////////////////////////////////////
                        Spacer(modifier = Modifier.height(8.dp))

                        Text("Affiliation with a cooperative")
                        CustomDropdown(
                            label = "",
                            options = listOf("Affiliated", "Not Affiliated"),
                            selectedOption = selectedAffiliation,
                            required = true,
                            errorMessage = "Required",
                            showError = showErrors,
                            onSelectionChanged = { newValue -> selectedAffiliation = newValue }
                        )
                        ///////////////////////////////////////////////////////////////////
                        Spacer(modifier = Modifier.height(8.dp))

                        Text("Active O&M fund and regular tariff collection")
                        CustomDropdown(
                            label = "",
                            options = listOf(
                                "Active O&M fund",
                                " Regular water tariff",
                                " both ",
                                "none of them"
                            ),
                            selectedOption = selectedTraffic,
                            required = true,
                            errorMessage = "Required",
                            showError = showErrors,
                            onSelectionChanged = { newValue -> selectedTraffic = newValue }
                        )
                        ///////////////////////////////////////////////////////////////////
                        Spacer(modifier = Modifier.height(8.dp))

                        Text("O&M fund status")
                        CustomDropdown(
                            label = "",
                            options = listOf(
                                "Locally mobilized/Cash in hand ",
                                "deposited in the bank",
                                "Deposited in the cooperative"
                            ),
                            selectedOption = selectedFund,
                            required = true,
                            errorMessage = "Required",
                            showError = showErrors,
                            onSelectionChanged = { newValue -> selectedFund = newValue }
                        )
                        ///////////////////////////////////////////////////////////////////
                        Spacer(modifier = Modifier.height(8.dp))

                        Text("Is water quantity adequate?")
                        CustomDropdown(
                            label = "",
                            options = yesnoOptions,
                            selectedOption = selectedQuantity,
                            required = true,
                            errorMessage = "Required",
                            showError = showErrors,
                            onSelectionChanged = { newValue -> selectedQuantity = newValue }
                        )
                        ///////////////////////////////////////////////////////////////////
                        Spacer(modifier = Modifier.height(8.dp))

                        Text("Is water accessible within 15 min?")
                        CustomDropdown(
                            label = "",
                            options = yesnoOptions,
                            selectedOption = selectedAccessibility,
                            required = true,
                            errorMessage = "Required",
                            showError = showErrors,
                            onSelectionChanged = { newValue -> selectedAccessibility = newValue }
                        )
                        ///////////////////////////////////////////////////////////////////
                        Spacer(modifier = Modifier.height(8.dp))

                        Text("Is water available all year round?")
                        CustomDropdown(
                            label = "",
                            options = yesnoOptions,
                            selectedOption = selectedAvailability,
                            required = true,
                            errorMessage = "Required",
                            showError = showErrors,
                            onSelectionChanged = { newValue -> selectedAvailability = newValue }
                        )
                        ///////////////////////////////////////////////////////////////////
                        Spacer(modifier = Modifier.height(8.dp))

                        Text("Does water quality as per NDWQS?")
                        CustomDropdown(
                            label = "",
                            options = yesnoOptions,
                            selectedOption = selectedQuality,
                            required = true,
                            errorMessage = "Required",
                            showError = showErrors,
                            onSelectionChanged = { newValue -> selectedQuality = newValue }
                        )
                        ///////////////////////////////////////////////////////////////////
                        Spacer(modifier = Modifier.height(8.dp))

                        Text(
                            text = "Yearly income and expenditure ratio?",
                            modifier = Modifier.height(26.dp)
                        )
                        CustomDropdown(
                            label = "",
                            options = listOf(
                                "less than 20%",
                                " 21% - 50%",
                                " 51% - 90%",
                                " more than 90"
                            ),
                            selectedOption = selectedRatio,
                            required = true,
                            errorMessage = "Required",
                            showError = showErrors,
                            onSelectionChanged = { newValue -> selectedRatio = newValue }
                        )

                        ///////////////////////////////////////////////////////////////////
                        Spacer(modifier = Modifier.height(8.dp))

                        Text("RegularSSC reporting")
                        CustomDropdown(
                            label = "",
                            options = listOf(
                                "Regular reporting as defined",
                                " Irregular reporting",
                                " No reporting at all"
                            ),
                            selectedOption = selectedSSC,
                            required = true,
                            errorMessage = "Required",
                            showError = showErrors,
                            onSelectionChanged = { newValue -> selectedSSC = newValue }
                        )
                    }
                }
            }

            fun validateForm(): Boolean {
                var isValid = true

                if (fieldVisitDate.isBlank()) isValid = false
                if (selectedWardno.isBlank()) isValid = false
                if (selectedScheme.isBlank()) isValid = false
                if (selectedStructure.isBlank()) isValid = false

                if (hasIssue == true) {
                    if (majorTechnicalIssues.isBlank()) isValid = false
                    if (proposedSolutions.isBlank()) isValid = false
                }

                // Social Details validation
                if (selectedFunctional.isBlank()) isValid = false
                if (selectedRegistrationrenewal.isBlank()) isValid = false
                if (selectedWUSCcompleteness.isBlank()) isValid = false
                if (selectedWUSCmeeting.isBlank()) isValid = false
                if (selectedVMWMobilization.isBlank()) isValid = false
                if (selectedPlan.isBlank()) isValid = false
                if (selectedProcedure.isBlank()) isValid = false
                if (selectedAGM.isBlank()) isValid = false
                if (selectedPart.isBlank()) isValid = false
                if (selectedReview.isBlank()) isValid = false
                if (selectedManagement.isBlank()) isValid = false
                if (selectedAffiliation.isBlank()) isValid = false
                if (selectedTraffic.isBlank()) isValid = false
                if (selectedFund.isBlank()) isValid = false
                if (selectedQuantity.isBlank()) isValid = false
                if (selectedAccessibility.isBlank()) isValid = false
                if (selectedAvailability.isBlank()) isValid = false
                if (selectedQuality.isBlank()) isValid = false
                if (selectedRatio.isBlank()) isValid = false
                if (selectedSSC.isBlank()) isValid = false

                return isValid
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceEvenly
            )
            {
                Button(
                    onClick = {
                        showErrors = true
                        if (validateForm()) {

                            val data = FieldData(
                                id = 0,
                                lgName = LGName,
                                ward = selectedWardno.toIntOrNull() ?: 0,
                                scheme = selectedScheme,
                                surveyDate = fieldVisitDate,
                                BenHH = beneficiaryHHs.toLongOrNull() ?: 0L,
                                BenPop = beneficiaryPop.toLongOrNull() ?: 0L,
                                report = report,
                                structureName = selectedStructure,
                                issues = if (hasIssue == true) majorTechnicalIssues else "",
                                solution = "",
                                functionalStatus = selectedFunctional,
                                registrationRenewalWUSC = selectedRegistrationrenewal,
                                completenessWUSC = selectedWUSCcompleteness,
                                regularityMeetingWUSC = selectedWUSCmeeting,
                                appointmentMobilization = selectedVMWMobilization,
                                planPreparationImplementation = selectedPlan,
                                OMprodureImplementation = selectedProcedure,
                                AGM = selectedAGM,
                                sparePartManagement = selectedPart,
                                annualReview = selectedReview,
                                accountManagement = selectedManagement,
                                affiliation = selectedAffiliation,
                                OMfundTraffic = selectedTraffic,
                                OmfundStatus = selectedFund,
                                waterQuantity = selectedQuantity,
                                waterAccessibility = selectedAccessibility,
                                waterAvailability = selectedAvailability,
                                waterQuality = selectedQuality,
                                incomeExpenditureRatio = selectedRatio,
                                SSCreporting = selectedSSC
                            )

                            val contentValues = ContentValues().apply {
//                                put("uuid", uuid)
                                put("lgName", data.lgName)
                                put("ward", data.ward)
                                put("schemeCode", data.scheme)
                                put("survey_date", data.surveyDate)
                                put("benHH", data.BenHH)
                                put("benPop", data.BenPop)
                                put("structure_name", data.structureName)
                                put("issues", data.issues)
                                put("solutions", data.solution)
                                put("functional_status", data.functionalStatus)
                                put("wusc_Registration_Renewal", data.registrationRenewalWUSC)
                                put("wusc_Completeness", data.completenessWUSC)
                                put("wusc_meeting_regularity", data.regularityMeetingWUSC)
                                put("vmw_appointment_and_mobilization", data.appointmentMobilization)
                                put("wsp_plan_preparation_and_implementation", data.planPreparationImplementation)
                                put("om_procedure_implementation", data.OMprodureImplementation)
                                put("agm", data.AGM)
                                put("spare_part_management", data.sparePartManagement)
                                put("om_and_WSP_plan_annual_review", data.annualReview)
                                put("account_management", data.accountManagement)
                                put("affiliation_with_a_cooperative", data.affiliation)
                                put("active_OM_fund_and_regular_tariff_collection", data.OMfundTraffic)
                                put("om_fund_status", data.OmfundStatus)
                                put("is_water_quantity_adequate", data.waterQuantity)
                                put("is_water_accessible_within_15_min", data.waterAccessibility)
                                put("is_water_available_all_year_round", data.waterAvailability)
                                put("does_water_quality_as_per_NDWQS", data.waterQuality)
                                put("yearlyIncomeAndExpenditureRatio", data.incomeExpenditureRatio)
                                put("regular_SSC_reporting", data.SSCreporting)
//                                put("surveyUser", surveyUser)
                            }

                            val dbHelper = FieldSurveyDbHelper(context)

                            val result = dbHelper.insertDataInTable(contentValues, FIELDSURVEYDATA)

                            if (result) {
                                Toast.makeText(context, "Saved successfully!", Toast.LENGTH_SHORT).show()
                            } else {
                                Toast.makeText(context, "Failed to save data", Toast.LENGTH_SHORT).show()
                            }

                        } else {
                            Toast.makeText(context, "Please fill all required fields", Toast.LENGTH_SHORT).show()
                        }
                    },
                    shape = RectangleShape
                ) {
                    Text("Save")
                }

                Button(onClick = {}, shape = RectangleShape) { Text("Cancel") } }

        }  /////////////////////////// MAIN COLUMN END ///////////////////////////////////////
    }
}
