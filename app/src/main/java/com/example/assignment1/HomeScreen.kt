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
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
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
import com.example.assignment1.ui.theme.Assignment1Theme
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.assignment1.ui.PatientViewModel
import androidx.compose.runtime.collectAsState
import com.example.assignment1.data.Patient
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.firstOrNull
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.layout.Box

class HomeScreen : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            HomeScreenPage()
        }
    }
}

@Composable
fun Greeting4(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview4() {
    Assignment1Theme {
        Greeting4("Android")
    }
}

@Composable
fun HomeScreenPage() {
    val context = LocalContext.current
    val viewModel: PatientViewModel = viewModel()
    val loginPref = context.getSharedPreferences("currentUser", Context.MODE_PRIVATE)
    val userID = loginPref.getString("currentUserID", "ID Not Registered") ?: "ID Not Registered"
    val allPatients by viewModel.allPatients.collectAsState(initial = emptyList())
    val patient = allPatients.find { it.userId == userID }
    val heifaScore = when (patient?.sex?.lowercase()) {
        "male" -> patient.heifaTotalScoreMale?.toString() ?: "N/A"
        "female" -> patient.heifaTotalScoreFemale?.toString() ?: "N/A"
        else -> "N/A"
    }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color(0xffFFDBBB)
    ) {
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
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Welcome Section
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 24.dp, vertical = 16.dp)
                ) {
                    Text(
                        text = "Hello ${patient?.name ?: "User"},",
                        style = TextStyle(
                            fontSize = 32.sp,
                            fontWeight = FontWeight.ExtraBold,
                            color = Color(0xffFF8000)
                        )
                    )
                }

                // Edit Questionnaire Card
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 24.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = Color.White
                    ),
                    elevation = CardDefaults.cardElevation(
                        defaultElevation = 4.dp
                    )
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Update your food intake questionnaire",
                            style = TextStyle(
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Medium,
                                color = Color(0xffFF8000)
                            ),
                            modifier = Modifier.weight(1f)
                        )
                        Button(
                            onClick = {
                                context.startActivity(Intent(context, FoodIntakeQuestionnaire::class.java))
                            },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color(0xffFF8000)
                            ),
                            shape = RoundedCornerShape(8.dp)
                        ) {
                            Text(
                                text = "Edit",
                                style = TextStyle(
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color.White
                                )
                            )
                        }
                    }
                }

                // Food Bowl Image
                androidx.compose.foundation.Image(
                    painter = painterResource(id = R.drawable.food_bowl),
                    contentDescription = "Food Bowl",
                    modifier = Modifier
                        .size(300.dp)
                        .padding(16.dp)
                )

                // HEIFA Score Card
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 24.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = Color.White
                    ),
                    elevation = CardDefaults.cardElevation(
                        defaultElevation = 4.dp
                    )
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                    ) {
                        Text(
                            text = "Your HEIFA Score: ",
                            style = TextStyle(
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xffFF8000)
                            )
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "$heifaScore/100",
                            style = TextStyle(
                                fontSize = 24.sp,
                                fontWeight = FontWeight.ExtraBold,
                                color = Color(0xffFF8000)
                            )
                        )
                    }
                }

                // HEIFA Score Explanation Card
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 24.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = Color.White
                    ),
                    elevation = CardDefaults.cardElevation(
                        defaultElevation = 4.dp
                    )
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                    ) {
                        Text(
                            text = "What is the HEIFA Score?",
                            style = TextStyle(
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xffFF8000)
                            )
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "The HEIFA score, or Health Eating Index for Australian Adults, " +
                                    "is a metric used to evaluate dietary quality based on national " +
                                    "dietary guidelines. It assesses consumption patterns across various " +
                                    "food groups, rewarding higher intakes of fruits, vegetables, and whole " +
                                    "grains while penalizing excessive consumption of sugar, salt, and saturated fats. " +
                                    "This comprehensive scoring system helps identify nutritional gaps and promotes " +
                                    "balanced eating by guiding individuals towards healthier food choices. It aims " +
                                    "to improve public health outcomes by encouraging diets rich in essential nutrients.",
                            style = TextStyle(
                                fontSize = 14.sp,
                                lineHeight = 20.sp,
                                color = Color.DarkGray
                            )
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}

@Composable
fun readFoodScoreCSV(context: Context, fileName: String): String {
    var secondValues by remember { mutableStateOf("") }
    var assets = context.assets
    val loginPref = context.getSharedPreferences("UserInfo", Context.MODE_PRIVATE)
    val userID = loginPref.getString("currentUserID", "ID Not Registered")
    try {
        val inputStream = assets.open(fileName)
        val reader = BufferedReader(InputStreamReader(inputStream))
        reader.useLines { lines ->
            lines.drop(1).forEach { line -> //skip the first line & reads each line
                val values = line.split(",") // Split each line
                if (values.size > 1 && values[2].trim() == "Male" && values[1].trim() == userID) {
                    val foodScore = values[3].trim() // HEIFA male score
                    secondValues = foodScore  //add the id to a list
                    return secondValues
                }
                else if (values.size > 1 && values[2].trim() == "Female" && values[1].trim() == userID){
                    val foodScore = values[4].trim() // HEIFA female score
                    secondValues = foodScore  //add the id to a list
                    return secondValues
                }
                else{
                    secondValues = "Your ID is not in the database."
                }
            }
        }

    } catch (e: Exception){

    }
    return secondValues
}