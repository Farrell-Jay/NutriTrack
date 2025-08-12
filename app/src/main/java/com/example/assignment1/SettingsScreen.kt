// AI ASSISTED: Some parts of this code have been developed with the assistance of AI
package com.example.assignment1

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.assignment1.ui.theme.Assignment1Theme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.ui.graphics.Color
import androidx.compose.material.icons.outlined.ExitToApp
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Call
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material3.Divider
import androidx.compose.foundation.clickable
import androidx.compose.material3.Surface
import android.content.Intent
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info as FilledInfo
import androidx.compose.material.icons.filled.Person as FilledPerson
import androidx.compose.material.icons.filled.Settings as FilledSettings
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.BottomAppBarDefaults
import androidx.compose.ui.platform.LocalContext
import android.content.Context
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.assignment1.ui.PatientViewModel

class SettingsScreen : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Assignment1Theme {
                SettingsScreenPage()
            }
        }
    }
}

@Composable
fun SettingsScreenPage() {
    val context = LocalContext.current
    val viewModel: PatientViewModel = viewModel()
    val loginPref = context.getSharedPreferences("currentUser", Context.MODE_PRIVATE)
    val userID = loginPref.getString("currentUserID", "ID Not Registered") ?: "ID Not Registered"
    val allPatients by viewModel.allPatients.collectAsState(initial = emptyList())
    val patient = allPatients.find { it.userId == userID }

    Scaffold(
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
        containerColor = Color(0xffFFDBBB)
    ){ innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 16.dp)
        ) {
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Settings",
                style = TextStyle(
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                ),
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(bottom = 32.dp)
            )

            Text(
                text = "ACCOUNT",
                style = TextStyle(
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Gray
                ),
                modifier = Modifier.padding(bottom = 8.dp)
            )

            SettingsItem(icon = Icons.Outlined.Person, text = "${patient?.name}")
            SettingsItem(icon = Icons.Outlined.Call, text = "${patient?.phoneNumber}")
            SettingsItem(icon = Icons.Outlined.Info, text = "${patient?.userId}")

            Divider(modifier = Modifier.padding(vertical = 16.dp))

            Text(
                text = "OTHER SETTINGS",
                style = TextStyle(
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Gray
                ),
                modifier = Modifier.padding(bottom = 8.dp)
            )

            SettingsNavigationItem(icon = Icons.Outlined.ExitToApp, text = "Logout") { context.startActivity(Intent(context,
                LoginScreen::class.java)) }
            SettingsNavigationItem(icon = Icons.Outlined.Person, text = "Clinician Login") { context.startActivity(Intent(context,
                ClinicianLoginScreen::class.java)) }
        }
    }
}

@Composable
fun SettingsItem(icon: androidx.compose.ui.graphics.vector.ImageVector, text: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(imageVector = icon, contentDescription = null, modifier = Modifier.size(24.dp), tint = Color(0xffFF8000))
        Spacer(modifier = Modifier.width(16.dp))
        Text(text = text, style = TextStyle(fontSize = 16.sp, color = Color.Black))
    }
}

@Composable
fun SettingsNavigationItem(icon: androidx.compose.ui.graphics.vector.ImageVector, text: String, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(imageVector = icon, contentDescription = null, modifier = Modifier.size(24.dp), tint = Color(0xffFF8000))
            Spacer(modifier = Modifier.width(16.dp))
            Text(text = text, style = TextStyle(fontSize = 16.sp, color = Color.Black))
        }
        Icon(imageVector = Icons.Default.ArrowForward, contentDescription = null, modifier = Modifier.size(16.dp), tint = Color.Gray)
    }
}