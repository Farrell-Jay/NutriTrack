// AI ASSISTED: Some parts of this code have been developed with the assistance of AI
package com.example.assignment1

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.assignment1.ui.theme.Assignment1Theme
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import android.widget.Toast

class ClinicianLoginScreen : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Assignment1Theme {
                ClinicianLoginScreenPage()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ClinicianLoginScreenPage() {
    val context = LocalContext.current
    var clinicianKey by remember { mutableStateOf("") }

    Scaffold(
        containerColor = Color(0xffFFDBBB),
        topBar = {
            TopAppBar(
                title = {},
                navigationIcon = {
                    IconButton(onClick = { context.startActivity(Intent(context, SettingsScreen::class.java)) }) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color(0xffFFDBBB))
            )
        },
        bottomBar = {
            BottomAppBar(
                modifier = Modifier.height(80.dp),
                containerColor = Color(0xffFF8000),
                content = {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier.weight(1f)
                        ) {
                            IconButton(
                                modifier = Modifier.size(40.dp),
                                onClick = { context.startActivity(Intent(context, HomeScreen::class.java)) }
                            ) {
                                Icon(
                                    Icons.Filled.Home,
                                    contentDescription = "Go Home",
                                    tint = Color.White,
                                    modifier = Modifier.size(24.dp)
                                )
                            }
                            Text(
                                "Home",
                                style = TextStyle(
                                    fontSize = 12.sp,
                                    color = Color.White
                                )
                            )
                        }
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier.weight(1f)
                        ) {
                            IconButton(
                                modifier = Modifier.size(40.dp),
                                onClick = { context.startActivity(Intent(context, InsightScreen::class.java)) }
                            ) {
                                Icon(
                                    Icons.Filled.Info,
                                    contentDescription = "Insights Page",
                                    tint = Color.White,
                                    modifier = Modifier.size(24.dp)
                                )
                            }
                            Text(
                                "Insights",
                                style = TextStyle(
                                    fontSize = 12.sp,
                                    color = Color.White
                                )
                            )
                        }
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier.weight(1f)
                        ) {
                            IconButton(
                                modifier = Modifier.size(40.dp),
                                onClick = { context.startActivity(Intent(context, NutriCoachScreen::class.java)) }
                            ) {
                                Icon(
                                    Icons.Filled.Person,
                                    contentDescription = "NutriCoach Page",
                                    tint = Color.White,
                                    modifier = Modifier.size(24.dp)
                                )
                            }
                            Text(
                                "NutriCoach",
                                style = TextStyle(
                                    fontSize = 12.sp,
                                    color = Color.White
                                )
                            )
                        }
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier.weight(1f)
                        ) {
                            IconButton(
                                modifier = Modifier.size(40.dp),
                                onClick = { context.startActivity(Intent(context, SettingsScreen::class.java)) }
                            ) {
                                Icon(
                                    Icons.Filled.Settings,
                                    contentDescription = "Settings Page",
                                    tint = Color.White,
                                    modifier = Modifier.size(24.dp)
                                )
                            }
                            Text(
                                "Settings",
                                style = TextStyle(
                                    fontSize = 12.sp,
                                    color = Color.White
                                )
                            )
                        }
                    }
                }
            )
        },
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            Spacer(modifier = Modifier.height(64.dp)) // Adjust spacing from top
            Text(
                text = "Clinician Login",
                style = TextStyle(
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                ),
                modifier = Modifier.padding(bottom = 32.dp)
            )

            // Clinician Key Input
            Column(
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "Clinician Key",
                    style = TextStyle(
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    ),
                    modifier = Modifier.padding(bottom = 4.dp)
                )
                OutlinedTextField(
                    value = clinicianKey,
                    onValueChange = { clinicianKey = it },
                    label = { Text("Enter your clinician key") },
                    modifier = Modifier.fillMaxWidth(),
                    textStyle = TextStyle(color = Color.Black)
                )
            }

            Spacer(modifier = Modifier.height(32.dp))

            // Clinician Login Button
            Button(
                onClick = { 
                    if (clinicianKey == "dollar-entry-apples") {
                        context.startActivity(Intent(context, ClinicianDashBoardScreen::class.java))
                    } else {
                        Toast.makeText(context, "Incorrect clinician key", Toast.LENGTH_SHORT).show()
                    }
                },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xffFF8000)), // Use a purple color similar to the image
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.ArrowForward,
                    contentDescription = "Login",
                    tint = Color.White,
                    modifier = Modifier.size(24.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text("Clinician Login", style = TextStyle(fontSize = 18.sp, color = Color.White))
            }
        }
    }
}