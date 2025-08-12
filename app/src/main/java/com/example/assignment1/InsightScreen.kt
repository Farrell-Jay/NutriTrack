// AI ASSISTED: Some parts of this code have been developed with the assistance of AI
package com.example.assignment1

import android.content.Context
import android.os.Bundle
import android.content.Intent
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.background
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.dp
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.material3.Text
import com.example.assignment1.ui.theme.Assignment1Theme
import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.File
import kotlin.math.exp
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.BottomAppBarDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.platform.LocalContext
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Slider
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.assignment1.ui.PatientViewModel
import com.example.assignment1.ui.theme.Assignment1Theme

class InsightScreen : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            InsightsDisplay()
        }
    }
}

@Composable
fun Greeting5(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview5() {
    Assignment1Theme {
        Greeting5("Android")
    }
}

@Composable
fun InsightsDisplay() {
    val context = LocalContext.current
    val viewModel: PatientViewModel = viewModel()
    val loginPref = context.getSharedPreferences("currentUser", Context.MODE_PRIVATE)
    val userID = loginPref.getString("currentUserID", "ID Not Registered") ?: "ID Not Registered"
    val allPatients by viewModel.allPatients.collectAsState(initial = emptyList())
    val patient = allPatients.find { it.userId == userID }
    val patientSex = patient?.sex?.lowercase()
    
//    // Debug logging
//    println("Patient found: ${patient != null}")
//    println("Patient sex: $patientSex")
    
    val scoreList = if (patient != null && (patientSex == "male" || patientSex == "female")) {
        listOf(
            "Discretionary Score" to if (patientSex == "male") patient.discretionaryHeifaScoreMale else patient.discretionaryHeifaScoreFemale,
            "Vegetables Score" to if (patientSex == "male") patient.vegetablesHeifaScoreMale else patient.vegetablesHeifaScoreFemale,
            "Fruit Score" to if (patientSex == "male") patient.fruitHeifaScoreMale else patient.fruitHeifaScoreFemale,
            "Grains & Cereals Score" to if (patientSex == "male") patient.grainsAndCerealsHeifaScoreMale else patient.grainsAndCerealsHeifaScoreFemale,
            "Whole Grains Score" to if (patientSex == "male") patient.wholeGrainsHeifaScoreMale else patient.wholeGrainsHeifaScoreFemale,
            "Meat & Alternatives Score" to if (patientSex == "male") patient.meatAndAlternativesHeifaScoreMale else patient.meatAndAlternativesHeifaScoreFemale,
            "Dairy & Alternatives Score" to if (patientSex == "male") patient.dairyAndAlternativesHeifaScoreMale else patient.dairyAndAlternativesHeifaScoreFemale,
            "Sodium Score" to if (patientSex == "male") patient.sodiumHeifaScoreMale else patient.sodiumHeifaScoreFemale,
            "Alcohol Score" to if (patientSex == "male") patient.alcoholHeifaScoreMale else patient.alcoholHeifaScoreFemale,
            "Water Score" to if (patientSex == "male") patient.waterHeifaScoreMale else patient.waterHeifaScoreFemale,
            "Sugar Score" to if (patientSex == "male") patient.sugarHeifaScoreMale else patient.sugarHeifaScoreFemale,
            "Saturated Fat Score" to if (patientSex == "male") patient.saturatedFatHeifaScoreMale else patient.saturatedFatHeifaScoreFemale,
            "Unsaturated Fat Score" to if (patientSex == "male") patient.unsaturatedFatHeifaScoreMale else patient.unsaturatedFatHeifaScoreFemale
        )
    } else {
        emptyList()
    }
    
//    // Debug logging
//    println("Score list size: ${scoreList.size}")
//    scoreList.forEach { (label, value) ->
//        println("$label: $value")
//    }

    val totalHeifaScore = when (patientSex) {
        "male" -> patient?.heifaTotalScoreMale?.toString() ?: "N/A"
        "female" -> patient?.heifaTotalScoreFemale?.toString() ?: "N/A"
        else -> "N/A"
    }

    Surface (
        modifier = Modifier.fillMaxSize(),
        color = Color(0xffFFDBBB)
    ){
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
        ) { innerPadding ->
            Column(
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally
            ){
                Spacer(modifier = Modifier.height(45.dp))

                Text(
                    text = "Food Score",
                    style = TextStyle(
                        fontSize = 25.sp,
                        fontWeight = FontWeight.ExtraBold,
                    )
                )

                Spacer(modifier = Modifier.height(25.dp))

                if (scoreList.isEmpty()) {
                    Text(
                        text = "No scores available. Please complete the food intake questionnaire.",
                        style = TextStyle(
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.Red
                        ),
                        modifier = Modifier.padding(16.dp)
                    )
                } else {
                    scoreList.forEach { (label, value) ->
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp, vertical = 12.dp)
                                .background(
                                    color = Color(0x33FFFFFF),
                                    shape = RoundedCornerShape(8.dp)
                                )
                                .padding(12.dp)
                        ) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = label,
                                    style = TextStyle(
                                        fontSize = 16.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = Color(0xffFF8000)
                                    )
                                )
                                Text(
                                    text = "${value ?: "N/A"}/100",
                                    style = TextStyle(
                                        fontSize = 16.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = Color(0xffFF8000)
                                    )
                                )
                            }
                            Spacer(modifier = Modifier.height(8.dp))
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(12.dp)
                                    .background(
                                        color = Color(0x33FF8000),
                                        shape = RoundedCornerShape(6.dp)
                                    )
                            ) {
                                LinearProgressIndicator(
                                    progress = (value?.toFloat() ?: 0f) / 100f,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(12.dp),
                                    color = Color(0xffFF8000),
                                    trackColor = Color.Transparent
                                )
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(20.dp))

                Text(
                    text = "Total HEIFA Score",
                    style = TextStyle(
                        fontSize = 24.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = Color(0xffFF8000)
                    )
                )

                Spacer(modifier = Modifier.height(16.dp))

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                        .background(
                            color = Color(0x33FFFFFF),
                            shape = RoundedCornerShape(12.dp)
                        )
                        .padding(16.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Overall Score",
                            style = TextStyle(
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xffFF8000)
                            )
                        )
                        Text(
                            text = "${totalHeifaScore}/100",
                            style = TextStyle(
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xffFF8000)
                            )
                        )
                    }
                    Spacer(modifier = Modifier.height(12.dp))
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(16.dp)
                            .background(
                                color = Color(0x33FF8000),
                                shape = RoundedCornerShape(8.dp)
                            )
                    ) {
                        LinearProgressIndicator(
                            progress = (totalHeifaScore.toFloatOrNull() ?: 0f) / 100f,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(16.dp),
                            color = Color(0xffFF8000),
                            trackColor = Color.Transparent
                        )
                    }
                }

                Spacer(modifier = Modifier.height(32.dp))

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    Button(
                        onClick = { /* TODO: Implement share functionality */ },
                        modifier = Modifier
                            .weight(1f)
                            .padding(end = 8.dp)
                            .height(50.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xffFF8000)
                        ),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Text(
                            text = "Share with Someone",
                            style = TextStyle(
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.White
                            )
                        )
                    }

                    Button(
                        onClick = { context.startActivity(Intent(context, NutriCoachScreen::class.java)) },
                        modifier = Modifier
                            .weight(1f)
                            .padding(start = 8.dp)
                            .height(50.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xffFF8000)
                        ),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Text(
                            text = "Improve my Diet",
                            style = TextStyle(
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.White
                            )
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}

//
//
//
//@Composable
//fun readFoodScoreInsightsCSV(context: Context, fileName: String): List<Float> {
//    var foodScoreValues = mutableListOf<Float>()
//    var assets = context.assets
//    val loginPref = context.getSharedPreferences("UserInfo", Context.MODE_PRIVATE)
//    val userID = loginPref.getString("currentUserID", "ID Not Registered")
//    try {
//        val inputStream = assets.open(fileName)
//        val reader = BufferedReader(InputStreamReader(inputStream))
//        reader.useLines { lines ->
//            lines.drop(1).forEach { line -> //skip the first line & reads each line
//                val values = line.split(",") // Split each line
//                if (values.size > 1 && values[2].trim() == "Male" && values[1].trim() == userID) {
//                    val vegScore = values[8].trim().toFloat()
//                    val fruitScore = values[19].trim().toFloat()
//                    val grainScore = values[29].trim().toFloat()
//                    val wholeGrainScore = values[33].trim().toFloat()
//                    val meatScore = values[36].trim().toFloat()
//                    val dairyScore = values[40].trim().toFloat()
//                    val waterScore = values[49].trim().toFloat()
//                    val unsaturatedFatScore = values[60].trim().toFloat()
//                    val sodiumScore = values[43].trim().toFloat()
//                    val sugarScore = values[54].trim().toFloat()
//                    val alcoholScore = values[46].trim().toFloat()
//                    val discretionaryFoodScore = values[5].trim().toFloat()
//                    val totalScore = values[3].trim().toFloat()
//                    foodScoreValues.add(vegScore)
//                    foodScoreValues.add(fruitScore)
//                    foodScoreValues.add(grainScore)
//                    foodScoreValues.add(wholeGrainScore)
//                    foodScoreValues.add(meatScore)
//                    foodScoreValues.add(dairyScore)
//                    foodScoreValues.add(waterScore)
//                    foodScoreValues.add(unsaturatedFatScore)
//                    foodScoreValues.add(sodiumScore)
//                    foodScoreValues.add(sugarScore)
//                    foodScoreValues.add(alcoholScore)
//                    foodScoreValues.add(discretionaryFoodScore)
//                    foodScoreValues.add(totalScore)
//                    return foodScoreValues
//                }
//                else if (values.size > 1 && values[2].trim() == "Female" && values[1].trim() == userID){
//                    val vegScore = values[9].trim().toFloat()
//                    val fruitScore = values[20].trim().toFloat()
//                    val grainScore = values[30].trim().toFloat()
//                    val wholeGrainScore = values[34].trim().toFloat()
//                    val meatScore = values[37].trim().toFloat()
//                    val dairyScore = values[41].trim().toFloat()
//                    val waterScore = values[50].trim().toFloat()
//                    val unsaturatedFatScore = values[61].trim().toFloat()
//                    val sodiumScore = values[44].trim().toFloat()
//                    val sugarScore = values[55].trim().toFloat()
//                    val alcoholScore = values[47].trim().toFloat()
//                    val discretionaryFoodScore = values[6].trim().toFloat()
//                    val totalScore = values[4].trim().toFloat()
//                    foodScoreValues.add(vegScore)
//                    foodScoreValues.add(fruitScore)
//                    foodScoreValues.add(grainScore)
//                    foodScoreValues.add(wholeGrainScore)
//                    foodScoreValues.add(meatScore)
//                    foodScoreValues.add(dairyScore)
//                    foodScoreValues.add(waterScore)
//                    foodScoreValues.add(unsaturatedFatScore)
//                    foodScoreValues.add(sodiumScore)
//                    foodScoreValues.add(sugarScore)
//                    foodScoreValues.add(alcoholScore)
//                    foodScoreValues.add(discretionaryFoodScore)
//                    foodScoreValues.add(totalScore)
//                    return foodScoreValues
//                }
//                else{
//
//                }
//            }
//        }
//
//    } catch (e: Exception){
//
//    }
//    return foodScoreValues
//}