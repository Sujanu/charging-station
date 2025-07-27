package com.example.chargingstation.activites

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.chargingstation.ChargingStation
import com.example.chargingstation.activites.ui.theme.ChargingStationTheme


class UserInfo : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {

        val dbHelper = ChargingStation(this)

        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ChargingStationTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
//                    UserField(db = dbHelper)
                }
            }
        }
    }
}
//
//@OptIn(ExperimentalMaterial3Api::class)
//@Composable
//fun UserField(db: ChargingStation?) {
//    val name = remember { mutableStateOf("") }
//    val email = remember { mutableStateOf("") }
//    val password = remember { mutableStateOf("") }
//    val passwordError = remember { mutableStateOf(false) }
//    val passwordErrors = remember { mutableStateOf(false) }
//    val confirmPassword = remember { mutableStateOf("") }
//    val showPassword = remember { mutableStateOf(false) }
//    val showConfirmPassword = remember { mutableStateOf(false) }
//    val phone = remember { mutableStateOf("") }
//    val confirmPasswordError = remember { mutableStateOf(false) }
//
//    val nameFocus = remember { FocusRequester() }
//    val emailFocus = remember { FocusRequester() }
//    val passwordFocus = remember { FocusRequester() }
//    val confirmPasswordFocus = remember { FocusRequester() }
//    val phoneFocus = remember { FocusRequester() }
//    val context = LocalContext.current
//
//    Scaffold(
//        topBar = {
//            TopAppBar(
//                title = { Text("User Info") },
//                actions = {
//                    IconButton(onClick = {
//                        context.startActivity(Intent(context, LoginPage::class.java))
//                    }) {
//                        Icon(imageVector = Icons.Default.Home, contentDescription = "Home")
//                    }
//                }
//            )
//        }
//    ) {innerPadding ->
//            Column(
//                modifier = Modifier
//                    .padding(innerPadding)
//                    .fillMaxSize()
//                    .verticalScroll(rememberScrollState())
//                    .padding(16.dp),
//                verticalArrangement = Arrangement.Top,
//                horizontalAlignment = Alignment.Start
//            ) {
//                Text(
//                    "Station Information",
//                    style = MaterialTheme.typography.titleMedium,
//                    fontSize = 20.sp,
//                    modifier = Modifier.padding(bottom = 8.dp)
//                )
//
//                // Station Name
//                OutlinedTextField(
//                    value = name.value,
//                    onValueChange = { name.value = it },
//                    label = { Text("Username") },
//                    singleLine = true,
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .focusRequester(nameFocus),
//                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
//                    keyboardActions = KeyboardActions(onNext = { emailFocus.requestFocus() })
//                )
//
//                // Email
//                OutlinedTextField(
//                    value = email.value,
//                    onValueChange = { email.value = it },
//                    label = { Text("Email") },
//                    singleLine = true,
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .focusRequester(emailFocus),
//                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
//                    keyboardActions = KeyboardActions(onNext = { passwordFocus.requestFocus() })
//                )
//
//                // Password with show/hide toggle
//                OutlinedTextField(
//                    value = password.value,
//                    onValueChange = {
//                        password.value = it
//                        passwordError.value = it.length < 8
//                        passwordErrors.value = it.length > 16
//                    },
//                    label = { Text("Password") },
//                    singleLine = true,
//                    visualTransformation = if (showPassword.value) VisualTransformation.None else PasswordVisualTransformation(),
//                    trailingIcon = {
//                        val icon =
//                            if (showPassword.value) Icons.Filled.VisibilityOff else Icons.Filled.Visibility
//                        IconButton(onClick = { showPassword.value = !showPassword.value }) {
//                            Icon(
//                                imageVector = icon,
//                                contentDescription = "Toggle Password Visibility"
//                            )
//                        }
//                    },
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .focusRequester(passwordFocus),
//                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
//                    keyboardActions = KeyboardActions(onNext = { confirmPasswordFocus.requestFocus() })
//                )
//                if (passwordError.value) {
//                    Text(
//                        text = "Password must be at least 8 characters",
//                        color = MaterialTheme.colorScheme.error,
//                        fontSize = 12.sp
//                    )
//                }
//                if (passwordErrors.value) {
//                    Text(
//                        text = "Password must not exceed 16 characters",
//                        color = MaterialTheme.colorScheme.error,
//                        fontSize = 12.sp
//                    )
//                }
//
//                // Confirm Password
//                OutlinedTextField(
//                    value = confirmPassword.value,
//                    onValueChange = {
//                        confirmPassword.value = it
//                        confirmPasswordError.value = it != password.value || it.length < 8
//                    },
//                    label = { Text("Confirm Password") },
//                    singleLine = true,
//                    visualTransformation = if (showConfirmPassword.value) VisualTransformation.None else PasswordVisualTransformation(),
//                    trailingIcon = {
//                        val icon =
//                            if (showConfirmPassword.value) Icons.Default.VisibilityOff else Icons.Default.Visibility
//                        IconButton(onClick = {
//                            showConfirmPassword.value = !showConfirmPassword.value
//                        }) {
//                            Icon(imageVector = icon, contentDescription = "Toggle Confirm Password")
//                        }
//                    },
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .focusRequester(confirmPasswordFocus),
//                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
//                    keyboardActions = KeyboardActions(onNext = { phoneFocus.requestFocus() })
//                )
//                if (confirmPasswordError.value) {
//                    Text(
//                        text = if (confirmPassword.value.length < 8)
//                            "Confirm password must be at least 8 characters"
//                        else
//                            "Passwords do not match",
//                        color = MaterialTheme.colorScheme.error,
//                        fontSize = 12.sp
//                    )
//                }
//                if (confirmPassword.value.isNotEmpty() && confirmPassword.value != password.value) {
//                    Text(
//                        text = "Passwords do not match",
//                        color = MaterialTheme.colorScheme.error,
//                        modifier = Modifier.padding(top = 4.dp)
//                    )
//                }
//
//                // Phone Number
//                OutlinedTextField(
//                    value = phone.value,
//                    onValueChange = { phone.value = it },
//                    label = { Text("Phone Number") },
//                    singleLine = true,
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .focusRequester(phoneFocus),
//                    keyboardOptions = KeyboardOptions( keyboardType = KeyboardType.Phone,imeAction = ImeAction.Next),
//                    keyboardActions = KeyboardActions(onDone = { })
//                )
//
//            Row(
//                modifier = Modifier.fillMaxWidth(),
//                horizontalArrangement = Arrangement.SpaceBetween,
//                verticalAlignment =  Alignment.CenterVertically
//
//            )
//            {
//                Button(
//                    onClick = {
//                        if (
//                            name.value.isNotEmpty() && email.value.isNotEmpty()
//                            && password.value.isNotEmpty() && phone.value.isNotEmpty()
//                            && password.value.length in 8..16 && password.value == confirmPassword.value
//                            )
//                        {try {
//                            val phoneLong = phone.value.toLong()
//                            db?.insertUser(
//                                username = name.value,
//                                password = password.value,
//                                email = email.value,
//                                phone = phoneLong
//                            )
//                            Toast.makeText(context, "Account Created", Toast.LENGTH_SHORT).show()
//                            context.startActivity(Intent(context, LoginPage::class.java))
//                        } catch (e: Exception) {
//                            Toast.makeText(context, "Phone must be a valid number", Toast.LENGTH_SHORT).show()
//                        }
//                        } else {
//                            Toast.makeText(context, "Please fill all fields correctly", Toast.LENGTH_SHORT).show()
//                        }
//                    },
//                    colors = ButtonDefaults.buttonColors(
//                        containerColor = Color.Transparent, // Matches background
//                        contentColor = Color.Black // Text color
//                    ),
//                    elevation = null,
//                ) {
//                    Text("Save",
//                        style = MaterialTheme.typography.titleMedium,
//                        fontSize = 20.sp
//                    )
//                }
//                Button(
//                    onClick = {
//                        name.value = ""
//                        email.value = ""
//                        password.value = ""
//                        passwordError.value = false
//                        passwordErrors.value = false
//                        confirmPassword.value = ""
//                        phone.value = ""
//                    },
//                    colors = ButtonDefaults.buttonColors(
//                        containerColor = Color.Transparent, // Matches background
//                        contentColor = Color.Black // Text color
//                    ),
//                    elevation = null,
//                ) {
//                    Text("Clear All",
//                        style = MaterialTheme.typography.titleMedium,
//                        fontSize = 20.sp
//                    )
//                }
//            }
//        }
//    }
//}



//            ///////////////////////////////////////  Charger ///////////////////////////////////////

//    var charger1 by remember { mutableStateOf(station?.charger1 ?: "") }
//    var chargerMake1 by remember { mutableStateOf(station?.chargerMake1 ?: "") }
//    var chargerType1 by remember { mutableStateOf(station?.chargerType1 ?: "") }
//    var chargerCost1 by remember { mutableStateOf(station?.chargerCost1 ?.toString() ?: "") }
//    var chargerCapacity1 by remember { mutableStateOf(station?.chargerCapacity1 ?: "") }
//
//    var charger2 by remember { mutableStateOf(station?.charger2 ?: "") }
//    var chargerMake2 by remember { mutableStateOf(station?.chargerMake2 ?: "") }
//    var chargerType2 by remember { mutableStateOf(station?.chargerType2 ?: "") }
//    var chargerCost2 by remember { mutableStateOf(station?.chargerCost2?.toString() ?: "") }
//    var chargerCapacity2 by remember { mutableStateOf(station?.chargerCapacity2 ?: "") }
//
//    var charger3 by remember { mutableStateOf(station?.charger3 ?: "") }
//    var chargerMake3 by remember { mutableStateOf(station?.chargerMake3 ?: "") }
//    var chargerType3 by remember { mutableStateOf(station?.chargerType3 ?: "") }
//    var chargerCost3 by remember { mutableStateOf(station?.chargerCost3?.toString() ?: "") }
//    var chargerCapacity3 by remember { mutableStateOf(station?.chargerCapacity3 ?: "") }


///////////////////////////////////// 1 /////////////////////////////////////

//            Text(text = ("Charger 1"))
//
//            Spacer(modifier = Modifier.height(8.dp))
//
//            OutlinedTextField(
//                value = charger1,
//                onValueChange = { charger1 = it },
//                label = { Text("Charger No ") },
//                singleLine = true,
//                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number, imeAction = ImeAction.Next),
//                keyboardActions = KeyboardActions(onNext = { chargerCapacity1Focus.requestFocus() }),
//                modifier = Modifier.fillMaxWidth().focusRequester(charger1Focus)
//            )
//
//            OutlinedTextField(
//                value = chargerCapacity1,
//                onValueChange = { chargerCapacity1 = it },
//                label = { Text("Charger Capacity ") },
//                singleLine = true,
//                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
//                keyboardActions = KeyboardActions(onNext = { chargerMake1Focus.requestFocus() }),
//                modifier = Modifier.fillMaxWidth().focusRequester(chargerCapacity1Focus)
//            )
//
//            OutlinedTextField(
//                value = chargerMake1,
//                onValueChange = { chargerMake1 = it },
//                label = { Text("Charger Made") },
//                singleLine = true,
//                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
//                keyboardActions = KeyboardActions(onNext = { chargerType1Focus.requestFocus() }),
//                modifier = Modifier.fillMaxWidth().focusRequester(chargerMake1Focus)
//            )
//
//            OutlinedTextField(
//                value = chargerType1,
//                onValueChange = { chargerType1 = it },
//                label = { Text("Charger Type") },
//                singleLine = true,
//                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
//                keyboardActions = KeyboardActions(onNext = { chargerCost1Focus.requestFocus() }),
//                modifier = Modifier.fillMaxWidth().focusRequester(chargerType1Focus)
//            )
//
//            OutlinedTextField(
//                value = chargerCost1,
//                onValueChange = { chargerCost1 = it },
//                label = { Text("Charger Cost") },
//                singleLine = true,
//                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number, imeAction = ImeAction.Next),
//                keyboardActions = KeyboardActions(onNext = { charger2Focus.requestFocus() }),
//                modifier = Modifier.fillMaxWidth().focusRequester(chargerCost1Focus)
//            )
//
//            ///////////////////////////////////// 1 /////////////////////////////////////
//
//            Spacer(modifier = Modifier.height(8.dp))
//
//            ///////////////////////////////////// 2 /////////////////////////////////////
//
//            Text(text = ("Charger 2"))
//
//            OutlinedTextField(
//                value = charger2,
//                onValueChange = { charger2 = it },
//                label = { Text("Charger No 2") },
//                singleLine = true,
//                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number, imeAction = ImeAction.Next),
//                keyboardActions = KeyboardActions(onNext = { chargerCapacity2Focus.requestFocus() }),
//                modifier = Modifier.fillMaxWidth().focusRequester(charger2Focus)
//            )
//
//            OutlinedTextField(
//                value = chargerCapacity2,
//                onValueChange = { chargerCapacity2 = it },
//                label = { Text("Charger Capacity 2") },
//                singleLine = true,
//                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
//                keyboardActions = KeyboardActions(onNext = { chargerMake2Focus.requestFocus() }),
//                modifier = Modifier.fillMaxWidth().focusRequester(chargerCapacity2Focus)
//            )
//
//            OutlinedTextField(
//                value = chargerMake2,
//                onValueChange = { chargerMake2 = it },
//                label = { Text("Charger Made 2") },
//                singleLine = true,
//                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
//                keyboardActions = KeyboardActions(onNext = { chargerType2Focus.requestFocus() }),
//                modifier = Modifier.fillMaxWidth().focusRequester(chargerMake2Focus)
//            )
//
//            OutlinedTextField(
//                value = chargerType2,
//                onValueChange = { chargerType2 = it },
//                label = { Text("Charger Type 2") },
//                singleLine = true,
//                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
//                keyboardActions = KeyboardActions(onNext = { chargerCost2Focus.requestFocus() }),
//                modifier = Modifier.fillMaxWidth().focusRequester(chargerType2Focus)
//            )
//
//            OutlinedTextField(
//                value = chargerCost2,
//                onValueChange = { chargerCost2 = it },
//                label = { Text("Charger Cost 2") },
//                singleLine = true,
//                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number, imeAction = ImeAction.Next),
//                keyboardActions = KeyboardActions(onNext = { charger3Focus.requestFocus() }),
//                modifier = Modifier.fillMaxWidth().focusRequester(chargerCost2Focus)
//            )
//
//            ///////////////////////////////////// 2 /////////////////////////////////////
//
//            Spacer(modifier = Modifier.height(8.dp))
//
//            ///////////////////////////////////// 3 /////////////////////////////////////
//
//            Text(text = ("Charger 3"))
//
//            OutlinedTextField(
//                value = charger3,
//                onValueChange = { charger3 = it },
//                label = { Text("Charger No 3") },
//                singleLine = true,
//                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number, imeAction = ImeAction.Next),
//                keyboardActions = KeyboardActions(onNext = { chargerCapacity3Focus.requestFocus() }),
//                modifier = Modifier.fillMaxWidth().focusRequester(charger3Focus)
//            )
//
//            OutlinedTextField(
//                value = chargerCapacity3,
//                onValueChange = { chargerCapacity3 = it },
//                label = { Text("Charger Capacity 3") },
//                singleLine = true,
//                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
//                keyboardActions = KeyboardActions(onNext = { chargerMake3Focus.requestFocus() }),
//                modifier = Modifier.fillMaxWidth().focusRequester(chargerCapacity3Focus)
//            )
//
//            OutlinedTextField(
//                value = chargerMake3,
//                onValueChange = { chargerMake3 = it },
//                label = { Text("Charger Made 3") },
//                singleLine = true,
//                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
//                keyboardActions = KeyboardActions(onNext = { chargerType3Focus.requestFocus() }),
//                modifier = Modifier.fillMaxWidth().focusRequester(chargerMake3Focus)
//            )
//
//            OutlinedTextField(
//                value = chargerType3,
//                onValueChange = { chargerType3 = it },
//                label = { Text("Charger Type 3") },
//                singleLine = true,
//                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
//                keyboardActions = KeyboardActions(onNext = { chargerCost3Focus.requestFocus() }),
//                modifier = Modifier.fillMaxWidth().focusRequester(chargerType3Focus)
//            )
//
//            OutlinedTextField(
//                value = chargerCost3,
//                onValueChange = { chargerCost3 = it },
//                label = { Text("Charger Cost 3") },
//                singleLine = true,
//                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number, imeAction = ImeAction.Next),
//                keyboardActions = KeyboardActions(onNext = { electricityCostFocus.requestFocus() }),
//                modifier = Modifier.fillMaxWidth().focusRequester(chargerCost3Focus)
//            )
//
//            ///////////////////////////////////////  3 ///////////////////////////////////////