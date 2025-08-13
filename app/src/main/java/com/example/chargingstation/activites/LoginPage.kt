package com.example.chargingstation.activites

import android.os.Bundle
import android.content.Intent
import android.util.Log
import android.widget.Toast

import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.text.input.*
import androidx.compose.ui.res.painterResource
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextDecoration

import com.example.chargingstation.R
import com.example.chargingstation.database.ChargingStation
import com.example.chargingstation.activites.ui.theme.ChargingStationTheme

class LoginPage : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val dbHelper = ChargingStation(this)
        val stationId = intent.getIntExtra("station_id", -1)


        val allStations = dbHelper.getAllChargingStations()
        allStations.forEach {
            Log.d("StationData", it.toString())
        }

        enableEdgeToEdge()
        setContent {
            ChargingStationTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    LoginField(db = dbHelper)
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginField(db: ChargingStation?) {
    val username = remember { mutableStateOf("") }
    val password = remember { mutableStateOf("") }

    val usernameFocusRequester = remember { FocusRequester() }
    val passwordFocusRequester = remember { FocusRequester() }

    val confirmPasswordError = remember { mutableStateOf(false) }

    var passwordVisible by remember { mutableStateOf(false) }

    val context = LocalContext.current

    Scaffold{
        innerPadding ->
        Box(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.car),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.matchParentSize()
            )

            TopAppBar(
                title = { Text("Charging Station", color = Color.Black) },
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .statusBarsPadding()
                    .fillMaxWidth(),
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.Transparent
                )
            )


            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally

            ) {
                Card(
                    modifier = Modifier
                        .fillMaxWidth(0.85f)
                        .offset(y = (-30).dp) // Move 20.dp upward
                        .padding(16.dp),
                    elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = Color.Transparent
                    ),
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        verticalArrangement = Arrangement.Top,
                        horizontalAlignment = Alignment.CenterHorizontally
                    )
                    {
                        /////////////////// **************** Text Field *************** ///////////////////

                        Text(
                            "Login In ",
                            style = MaterialTheme.typography.titleMedium,
                            fontSize = 20.sp
                        )
                        OutlinedTextField(
                            value = username.value,
                            onValueChange = { username.value = it },
                            label = { Text("Username") },
                            singleLine = true,
                            modifier = Modifier
                                .fillMaxWidth()
                                .focusRequester(usernameFocusRequester),
                            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                            keyboardActions = KeyboardActions(onNext = {
                                passwordFocusRequester.requestFocus()
                            })
                        )

                        OutlinedTextField(
                            value = password.value,
                            onValueChange = {
                                password.value = it
                                confirmPasswordError.value = it != password.value || it.length < 8
                            },
                            label = { Text("Password") },
                            singleLine = true,
                            visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                            trailingIcon = {
                                val image =
                                    if (passwordVisible) Icons.Filled.VisibilityOff else Icons.Filled.Visibility
                                val description =
                                    if (passwordVisible) "Hide password" else "Show password"

                                IconButton(onClick = { passwordVisible = !passwordVisible }) {
                                    Icon(imageVector = image, contentDescription = description)
                                }
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .focusRequester(passwordFocusRequester),
                            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                            keyboardActions = KeyboardActions(onDone = {
                                // Handle login
                            })
                        )
                        if (confirmPasswordError.value) {
                            Text(
                                text = if (password.value.length < 8)
                                    "Password must be at least 8 characters"
                                else
                                    "Passwords do not match",
                                color = MaterialTheme.colorScheme.error,
                                fontSize = 12.sp
                            )
                        }

                        /////////////////// **************** Button *************** ///////////////////

                        Button(
                            onClick = {
                                val user =
                                    db?.validateUser(username.value.trim(), password.value.trim())
                                if (user != null) {
                                    Toast.makeText(context, "Login successful", Toast.LENGTH_SHORT)
                                        .show()
                                    context.startActivity(Intent(context, MainActivity::class.java))
                                } else {
                                    Toast.makeText(
                                        context,
                                        "Invalid username or password",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                            },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color.Transparent,
                                contentColor = Color.Black
                            ),
                        )
                        {
                            Text(
                                "Login",
                                style = MaterialTheme.typography.titleMedium.copy(
                                    textDecoration = TextDecoration.Underline
                                ),
                                fontSize = 20.sp
                            )
                        }

                        /////////////////// **************** Button *************** ///////////////////


                        Button(
                            onClick = {
                                context.startActivity(Intent(context, SignUp::class.java))
                            },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color.Transparent, // Matches background
                                contentColor = Color.Black// Text color
                            ),
                            elevation = null,
                        )
                        {
                            Text(
                                "Don't have account?",
                                style = MaterialTheme.typography.titleMedium.copy(
                                    textDecoration = TextDecoration.Underline
                                ),
                                fontSize = 20.sp
                            )
                        }

                        /////////////////// **************** Button *************** ///////////////////

                        Button(
                            onClick = {
                                context.startActivity(Intent(context, Charger::class.java))
                            },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color.Transparent, // Matches background
                                contentColor = Color.Black// Text color
                            ),
                            elevation = null,
                        )
                        {
                            Text(
                                "Charger",
                                style = MaterialTheme.typography.titleMedium.copy(
                                    textDecoration = TextDecoration.Underline
                                ),
                                fontSize = 20.sp
                            )
                        }
                    }
                }
            }
        }
    }
}
