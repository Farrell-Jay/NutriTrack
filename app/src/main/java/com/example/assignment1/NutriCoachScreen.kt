// AI ASSISTED: Some parts of this code have been developed with the assistance of AI
package com.example.assignment1

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.assignment1.ui.NutriCoachViewModel
import com.example.assignment1.ui.PatientViewModel
import com.example.assignment1.ui.theme.Assignment1Theme
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.TextButton
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.collectAsState

class NutriCoachScreen : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Assignment1Theme {
                NutriCoachScreenPage()
            }
        }
    }
}

@Composable
fun NutriCoachScreenPage() {
    val context = LocalContext.current
    var fruitName by remember { mutableStateOf("banana") }
    val viewModel: NutriCoachViewModel = viewModel()
    val patientViewModel: PatientViewModel = viewModel()
    val fruitState by viewModel.fruitState.collectAsState()
    val tip by viewModel.motivationalMessage.collectAsState()
    val loginPref = context.getSharedPreferences("currentUser", Context.MODE_PRIVATE)
    val userID = loginPref.getString("currentUserID", "ID Not Registered") ?: "ID Not Registered"
    val allPatients by patientViewModel.allPatients.collectAsState(initial = emptyList())
    val patient = allPatients.find { it.userId == userID }
    val allFoodIntakePatients by patientViewModel.getAllFoodIntakes().collectAsState(initial = emptyList())
    val patientFoodIntake = allFoodIntakePatients.find { it.patientId == userID }
    val allNutriCoachTips by patientViewModel.getAllNutriCoachTips().collectAsState(initial = emptyList())
    val patientTips = allNutriCoachTips.find { it.patientId == userID }

    // Dialog state
    var showTipsDialog by remember { mutableStateOf(false) }

    // Compile patient data into a single string for AI analysis
    val patientDataString = " Does the patient eat fruits? ${patientFoodIntake?.fruits}\n" +
            "Does the patient eat vegetables? ${patientFoodIntake?.vegetables}\n" +
            "Does the patient eat grains? ${patientFoodIntake?.grains}\n" +
            "Does the patient eat Red Meat? ${patientFoodIntake?.redMeat}\n" +
            " Does the patient eat Sea Food? ${patientFoodIntake?.seafood}\n" +
            " Does the patient eat Poultry? ${patientFoodIntake?.poultry}\n" +
            " Does the patient eat Fish? ${patientFoodIntake?.fish}\n" +
            " Does the patient eat Eggs? ${patientFoodIntake?.eggs}\n" +
            " Does the patient eat Nuts/Seeds? ${patientFoodIntake?.nutsSeeds}\n" +
            " The patient goes to sleep at: ? ${patientFoodIntake?.sleepTime}\n" +
            " The patient wakes up at:  ${patientFoodIntake?.wakeUpTime}\n" +
            " The patient eats their biggest meal at:  ${patientFoodIntake?.biggestMealTime}\n"

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
    ){ paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .padding(16.dp)
                .verticalScroll(rememberScrollState())
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "NutriCoach",
                style = TextStyle(fontSize = 28.sp, fontWeight = FontWeight.Bold)
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = fruitName,
                onValueChange = { fruitName = it },
                label = { Text("Fruit Name", fontWeight = FontWeight.Bold,
                    color = Color(0xffFF8000)) },
                singleLine = true,
                modifier = Modifier.fillMaxWidth(0.9f),
                shape = RoundedCornerShape(8.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color(0xffFF8000),
                    unfocusedBorderColor = Color(0xffFF8000).copy(alpha = 0.5f),
                    focusedLabelColor = Color(0xffFF8000),
                    unfocusedLabelColor = Color(0xffFF8000).copy(alpha=0.5f)
                )
            )

            Button(
                onClick = { viewModel.fetchFruit(fruitName) },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xffFF8000)),
                modifier = Modifier.align(Alignment.End).padding(end = 24.dp)
            ) {
                Icon(Icons.Default.Search, contentDescription = "Details", tint = Color.White)
                Spacer(modifier = Modifier.width(4.dp))
                Text("Details", style = TextStyle(
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                ))
            }

            fruitState?.let { fruit ->
                val details = mapOf(
                    "Name" to fruit.name,
                    "Family" to fruit.family,
                    "Genus" to fruit.genus,
                    "Order" to fruit.order,
                    "Calories" to fruit.nutritions.calories.toString(),
                    "Carbohydrates" to fruit.nutritions.carbohydrates.toString(),
                    "Protein" to fruit.nutritions.protein.toString(),
                    "Fat" to fruit.nutritions.fat.toString(),
                    "Sugar" to fruit.nutritions.sugar.toString()
                )

                details.forEach { (label, value) ->
                    Surface(
                        shape = RoundedCornerShape(8.dp),
                        tonalElevation = 4.dp,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 2.dp)
                    ) {
                        Row(
                            modifier = Modifier.padding(12.dp),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(text = "$label:", fontWeight = FontWeight.Bold)
                            Text(text = value)
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = {
                    viewModel.fetchMotivationalMessage("AIzaSyDH4cCfdBeoncyeTSG7RLkgNrxkGcdxY8A", patientDataString, userID)
                    Toast.makeText(context, "Fetching new tip...", Toast.LENGTH_SHORT).show()
                },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xffFF8000)),
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth()
            ) {
                Text(
                    text = "Get New Fruit Tip",
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                )
            }

            tip?.let {
                Surface(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 8.dp),
                    shape = RoundedCornerShape(12.dp),
                    color = Color(0xffFF8000)
                ) {
                    Text(
                        text = it,
                        modifier = Modifier.padding(16.dp),
                        color = Color.White,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = { showTipsDialog = true },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xffFF8000)),
                modifier = Modifier.fillMaxWidth(0.8f)
            ) {
                Text("Show All Tips", style = TextStyle(
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                ))
            }
        }
    }

    if (showTipsDialog) {
        AlertDialog(
            onDismissRequest = { showTipsDialog = false },
            title = {
                Text(
                    text = "Your Tips History",
                    style = MaterialTheme.typography.headlineSmall.copy(
                        fontWeight = FontWeight.Bold,
                        color = Color(0xffFF8000)
                    )
                )
            },
            text = {
                LazyColumn(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(patientTips?.tips ?: emptyList()) { tip ->
                        Surface(
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(8.dp),
                            color = Color(0xffFF8000).copy(alpha = 0.1f)
                        ) {
                            Text(
                                text = tip,
                                modifier = Modifier.padding(16.dp),
                                style = MaterialTheme.typography.bodyMedium
                            )
                        }
                    }
                }
            },
            confirmButton = {
                TextButton(onClick = { showTipsDialog = false }) {
                    Text(
                        "Close",
                        style = MaterialTheme.typography.labelLarge.copy(
                            color = Color(0xffFF8000)
                        )
                    )
                }
            }
        )
    }
}

@Composable
fun BottomNavItem(label: String, icon: androidx.compose.ui.graphics.vector.ImageVector, onClick: () -> Unit) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        IconButton(onClick = onClick, modifier = Modifier.size(40.dp)) {
            Icon(icon, contentDescription = label, tint = Color.White, modifier = Modifier.size(24.dp))
        }
        Text(text = label, fontSize = 12.sp, color = Color.White)
    }
}
