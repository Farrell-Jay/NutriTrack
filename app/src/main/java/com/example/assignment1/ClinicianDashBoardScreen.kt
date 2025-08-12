// AI ASSISTED: Some parts of this code have been developed with the assistance of AI
package com.example.assignment1

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Search
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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.assignment1.ui.NutriCoachViewModel
import com.example.assignment1.ui.PatientViewModel

class ClinicianDashBoardScreen : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Assignment1Theme {
                ClinicianDashBoardScreenPage()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ClinicianDashBoardScreenPage() {
    val context = LocalContext.current
    val viewModel: PatientViewModel = viewModel()
    val nutriViewModel: NutriCoachViewModel = viewModel()
    val allPatients by viewModel.allPatients.collectAsState(initial = emptyList())
    val pattern by nutriViewModel.patternMessage.collectAsState()
    val pattern2 by nutriViewModel.patternMessage2.collectAsState()
    val pattern3 by nutriViewModel.patternMessage3.collectAsState()

    val averageHeifaMale = allPatients
        .filter { it.sex == "Male" && it.heifaTotalScoreMale != null }
        .map { it.heifaTotalScoreMale!! }
        .average()
        .takeIf { it.isNaN().not() }
        ?.toFloat() ?: 0.0f

    val averageHeifaFemale = allPatients
        .filter { it.sex == "Female" && it.heifaTotalScoreFemale != null }
        .map { it.heifaTotalScoreFemale!! }
        .average()
        .takeIf { it.isNaN().not() }
        ?.toFloat() ?: 0.0f 

    Scaffold(
        containerColor = Color(0xffFFDBBB),
        topBar = {
            TopAppBar(
                title = { Text("Clinician Dashboard", style = TextStyle(fontSize = 20.sp, fontWeight = FontWeight.Bold)) },
                navigationIcon = {
                    IconButton(onClick = { context.startActivity(Intent(context, ClinicianLoginScreen::class.java)) }) {
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
                .padding(horizontal = 16.dp)
                .verticalScroll(rememberScrollState()),
        ) {
            Spacer(modifier = Modifier.height(16.dp))

            // Average HEIFA fields
            OutlinedTextField(
                value = String.format("%.1f", averageHeifaMale), // Display calculated average male score
                onValueChange = {},
                label = { Text("Average HEIFA (Male)", style = TextStyle(fontWeight = FontWeight.Bold)) },
                readOnly = true,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp),
                 textStyle = TextStyle(color = Color.Black, fontSize = 18.sp, fontWeight = FontWeight.Bold)
            )
            OutlinedTextField(
                value = String.format("%.1f", averageHeifaFemale), // Display calculated average female score
                onValueChange = {},
                label = { Text("Average HEIFA (Female)", style = TextStyle(fontWeight = FontWeight.Bold)) },
                readOnly = true,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp),
                 textStyle = TextStyle(color = Color.Black, fontSize = 18.sp, fontWeight = FontWeight.Bold)
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Compile patient data into a single string for AI analysis
            val patientDataString = buildString {
                allPatients.forEachIndexed { index, patient ->
                    append("Patient ${index + 1}:\n")
                    append("  Name: ${patient.name}\n")
                    append("  UserID: ${patient.userId}\n")
                    append("  Phone: ${patient.phoneNumber}\n")
                    append("  Sex: ${patient.sex}\n")
                    append("  HEIFA Total Score (Male): ${patient.heifaTotalScoreMale}\n")
                    append("  HEIFA Total Score (Female): ${patient.heifaTotalScoreFemale}\n")
                    append("  Discretionary HEIFA Score (Male): ${patient.discretionaryHeifaScoreMale}\n")
                    append("  Discretionary HEIFA Score (Female): ${patient.discretionaryHeifaScoreFemale}\n")
                    append("  Discretionary Serve Size: ${patient.discretionaryServeSize}\n")
                    append("  Vegetables HEIFA Score (Male): ${patient.vegetablesHeifaScoreMale}\n")
                    append("  Vegetables HEIFA Score (Female): ${patient.vegetablesHeifaScoreFemale}\n")
                    append("  Vegetables With Legumes Allocated Serve Size: ${patient.vegetablesWithLegumesAllocatedServeSize}\n")
                    append("  Legumes Allocated Vegetables: ${patient.legumesAllocatedVegetables}\n")
                    append("  Vegetables Variations Score: ${patient.vegetablesVariationsScore}\n")
                    append("  Vegetables Cruciferous: ${patient.vegetablesCruciferous}\n")
                    append("  Vegetables Tuber And Bulb: ${patient.vegetablesTuberAndBulb}\n")
                    append("  Vegetables Other: ${patient.vegetablesOther}\n")
                    append("  Legumes: ${patient.legumes}\n")
                    append("  Vegetables Green: ${patient.vegetablesGreen}\n")
                    append("  Vegetables Red And Orange: ${patient.vegetablesRedAndOrange}\n")
                    append("  Fruit HEIFA Score (Male): ${patient.fruitHeifaScoreMale}\n")
                    append("  Fruit HEIFA Score (Female): ${patient.fruitHeifaScoreFemale}\n")
                    append("  Fruit Serve Size: ${patient.fruitServeSize}\n")
                    append("  Fruit Variations Score: ${patient.fruitVariationsScore}\n")
                    append("  Fruit Pome: ${patient.fruitPome}\n")
                    append("  Fruit Tropical And Subtropical: ${patient.fruitTropicalAndSubtropical}\n")
                    append("  Fruit Berry: ${patient.fruitBerry}\n")
                    append("  Fruit Stone: ${patient.fruitStone}\n")
                    append("  Fruit Citrus: ${patient.fruitCitrus}\n")
                    append("  Fruit Other: ${patient.fruitOther}\n")
                    append("  Grains And Cereals HEIFA Score (Male): ${patient.grainsAndCerealsHeifaScoreMale}\n")
                    append("  Grains And Cereals HEIFA Score (Female): ${patient.grainsAndCerealsHeifaScoreFemale}\n")
                    append("  Grains And Cereals Serve Size: ${patient.grainsAndCerealsServeSize}\n")
                    append("  Grains And Cereals Non Whole Grains: ${patient.grainsAndCerealsNonWholeGrains}\n")
                    append("  Whole Grains HEIFA Score (Male): ${patient.wholeGrainsHeifaScoreMale}\n")
                    append("  Whole Grains HEIFA Score (Female): ${patient.wholeGrainsHeifaScoreFemale}\n")
                    append("  Whole Grains Serve Size: ${patient.wholeGrainsServeSize}\n")
                    append("  Meat And Alternatives HEIFA Score (Male): ${patient.meatAndAlternativesHeifaScoreMale}\n")
                    append("  Meat And Alternatives HEIFA Score (Female): ${patient.meatAndAlternativesHeifaScoreFemale}\n")
                    append("  Meat And Alternatives With Legumes Allocated Serve Size: ${patient.meatAndAlternativesWithLegumesAllocatedServeSize}\n")
                    append("  Legumes Allocated Meat And Alternatives: ${patient.legumesAllocatedMeatAndAlternatives}\n")
                    append("  Dairy And Alternatives HEIFA Score (Male): ${patient.dairyAndAlternativesHeifaScoreMale}\n")
                    append("  Dairy And Alternatives HEIFA Score (Female): ${patient.dairyAndAlternativesHeifaScoreFemale}\n")
                    append("  Dairy And Alternatives Serve Size: ${patient.dairyAndAlternativesServeSize}\n")
                    append("  Sodium HEIFA Score (Male): ${patient.sodiumHeifaScoreMale}\n")
                    append("  Sodium HEIFA Score (Female): ${patient.sodiumHeifaScoreFemale}\n")
                    append("  Sodium Mg Milligrams: ${patient.sodiumMgMilligrams}\n")
                    append("  Alcohol HEIFA Score (Male): ${patient.alcoholHeifaScoreMale}\n")
                    append("  Alcohol HEIFA Score (Female): ${patient.alcoholHeifaScoreFemale}\n")
                    append("  Alcohol Standard Drinks: ${patient.alcoholStandardDrinks}\n")
                    append("  Water HEIFA Score (Male): ${patient.waterHeifaScoreMale}\n")
                    append("  Water HEIFA Score (Female): ${patient.waterHeifaScoreFemale}\n")
                    append("  Water: ${patient.water}\n")
                    append("  Water Total Ml: ${patient.waterTotalMl}\n")
                    append("  Beverage Total Ml: ${patient.beverageTotalMl}\n")
                    append("  Sugar HEIFA Score (Male): ${patient.sugarHeifaScoreMale}\n")
                    append("  Sugar HEIFA Score (Female): ${patient.sugarHeifaScoreFemale}\n")
                    append("  Sugar: ${patient.sugar}\n")
                    append("  Saturated Fat HEIFA Score (Male): ${patient.saturatedFatHeifaScoreMale}\n")
                    append("  Saturated Fat HEIFA Score (Female): ${patient.saturatedFatHeifaScoreFemale}\n")
                    append("  Saturated Fat: ${patient.saturatedFat}\n")
                    append("  Unsaturated Fat HEIFA Score (Male): ${patient.unsaturatedFatHeifaScoreMale}\n")
                    append("  Unsaturated Fat HEIFA Score (Female): ${patient.unsaturatedFatHeifaScoreFemale}\n")
                    append("  Unsaturated Fat Serve Size: ${patient.unsaturatedFatServeSize}\n")
                    append("---\n") // Separator for patients
                }
            }

            // Find Data Pattern Button
            Button(
                onClick = {
                    nutriViewModel.fetchPatternMessage(
                        apiKey = "AIzaSyDH4cCfdBeoncyeTSG7RLkgNrxkGcdxY8A",
                        dataset = patientDataString
                    )
                     Toast.makeText(context, "Fetching data patterns...", Toast.LENGTH_SHORT).show()
                          },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xffFF8000)),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "Find Data Pattern",
                    tint = Color.White
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text("Find Data Pattern", style = TextStyle(fontSize = 18.sp, fontWeight = FontWeight.Bold, color = Color.White))
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Data Pattern Cards (Placeholder)
            pattern?.let {
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

            pattern2?.let {
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

            pattern3?.let {
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

            Spacer(modifier = Modifier.weight(1f))

            // Done Button
            Button(
                onClick = { context.startActivity(Intent(context, SettingsScreen::class.java)) },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xffFF8000)),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
                    .padding(bottom = 16.dp)
            ) {
                Text("Done", style = TextStyle(fontSize = 18.sp, fontWeight = FontWeight.Bold, color = Color.White))
            }
        }
    }
}