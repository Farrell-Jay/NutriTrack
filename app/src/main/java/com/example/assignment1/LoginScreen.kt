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
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
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
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import com.example.assignment1.data.Patient
import com.example.assignment1.ui.theme.Assignment1Theme
import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.File
import kotlin.math.exp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.assignment1.ui.PatientViewModel
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.*
import androidx.compose.foundation.shape.RoundedCornerShape


class LoginScreen : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Assignment1Theme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Login(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@Composable
fun Greeting2(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview2() {
    Assignment1Theme {
        Greeting2("Android")
    }
}
@Composable
fun Login(modifier: Modifier = Modifier) {
    var userId by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    val context = LocalContext.current
    val viewModel: PatientViewModel = viewModel()

    LaunchedEffect(Unit) {
        viewModel.isFirstLaunch { isFirst: Boolean ->
            if (isFirst) {
                val patients = readPatientsFromCSV(context)
                viewModel.loadInitialData(patients)
            }
        }
    }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color(0xffFFDBBB)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Welcome Back!",
                style = TextStyle(
                    fontSize = 32.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = Color(0xffFF8000)
                ),
                modifier = Modifier.padding(bottom = 32.dp)
            )

            OutlinedTextField(
                value = userId,
                onValueChange = { userId = it },
                label = { Text("User ID") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                shape = RoundedCornerShape(12.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color(0xffFF8000),
                    unfocusedBorderColor = Color(0xffFF8000).copy(alpha = 0.5f),
                    focusedLabelColor = Color(0xffFF8000),
                    unfocusedLabelColor = Color(0xffFF8000).copy(alpha = 0.5f)
                )
            )

            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("Password") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                shape = RoundedCornerShape(12.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color(0xffFF8000),
                    unfocusedBorderColor = Color(0xffFF8000).copy(alpha = 0.5f),
                    focusedLabelColor = Color(0xffFF8000),
                    unfocusedLabelColor = Color(0xffFF8000).copy(alpha = 0.5f)
                )
            )

            Button(
                onClick = {
                    viewModel.authenticatePatient(userId, password) { patient ->
                        if (patient != null && patient.password != "") {
                            val loginPref = context.getSharedPreferences("currentUser", Context.MODE_PRIVATE).edit()
                            loginPref.putString("currentUserID", userId)
                            loginPref.apply()
                            Toast.makeText(context, "Login Successful", Toast.LENGTH_LONG).show()
                            context.startActivity(Intent(context, FoodIntakeQuestionnaire::class.java))
                        } else if (patient?.password == "")  {
                            Toast.makeText(context, "User not registered yet", Toast.LENGTH_LONG).show()
                        } else {
                            Toast.makeText(context, "Invalid credentials", Toast.LENGTH_LONG).show()
                        }
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp)
                    .height(56.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xffFF8000)
                ),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text(
                    text = "Login",
                    style = TextStyle(
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                )
            }

            TextButton(
                onClick = {
                    context.startActivity(Intent(context, RegistrationScreen::class.java))
                    (context as? LoginScreen)?.finish()
                },
                modifier = Modifier.padding(8.dp)
            ) {
                Text(
                    "New user? Register here",
                    style = TextStyle(
                        fontSize = 16.sp,
                        color = Color(0xffFF8000)
                    )
                )
            }
        }
    }
}


//fun readFromCSV(context: Context, fileName: String): List<String> {
//    val secondValues = mutableListOf<String>()
//    var assets = context.assets
//    try {
//        val inputStream = assets.open(fileName)
//        val reader = BufferedReader(InputStreamReader(inputStream))
//        reader.useLines { lines ->
//            secondValues.clear() // Clear the list before adding new data
//            lines.drop(1).forEach { line -> //skip the first line
//                val values = line.split(",") // Split each line
//                if (values.size > 1) {
//                    val userId = values[1].trim() // userID
//                    secondValues.add(userId) //add the id to a list
//                }
//            }
//        }
//
//    } catch (e: Exception){
//
//    }
//    return secondValues
//}


//fun areValuesPresentInCSV(context: Context, fileName: String, dropDownOption: String, phoneNumber: String): Boolean {
//    val assets = context.assets
//    try {
//        val inputStream = assets.open(fileName)
//        val reader = BufferedReader(InputStreamReader(inputStream))
//        reader.useLines { lines ->
//            lines.drop(1).forEach { line -> // Skip the first line, assuming it's headers
//                val values = line.split(",").map { it.trim() } // Split and trim each value
//                if (dropDownOption in values && phoneNumber in values) {
//                    return true // Both values are found in the same line
//                }
//            }
//        }
//    } catch (e: Exception) {
//        // Handle exception if necessary
//        e.printStackTrace()
//    }
//    return false // Return false if neither value is found in any line
//}

fun readPatientsFromCSV(context: Context): List<Patient> {
    val patients = mutableListOf<Patient>()
    try {
        val inputStream = context.assets.open("data.csv")
        val reader = BufferedReader(InputStreamReader(inputStream))
        reader.useLines { lines ->
            lines.drop(1).forEach { line ->
                val values = line.split(",").map { it.trim() }
                if (values.size >= 5) {
                    val phoneNumber = values[0]
                    val userId = values[1]
                    val sex = values[2]
                    val name = ""
                    val password = ""
                    patients.add(
                        Patient(
                            name = name,
                            password = password,
                            phoneNumber = phoneNumber,
                            userId = userId,
                            sex = sex,
                            heifaTotalScoreMale = values.getOrNull(3)?.toFloatOrNull(),
                            heifaTotalScoreFemale = values.getOrNull(4)?.toFloatOrNull(),
                            discretionaryHeifaScoreMale = values.getOrNull(5)?.toFloatOrNull(),
                            discretionaryHeifaScoreFemale = values.getOrNull(6)?.toFloatOrNull(),
                            discretionaryServeSize = values.getOrNull(7)?.toFloatOrNull(),
                            vegetablesHeifaScoreMale = values.getOrNull(8)?.toFloatOrNull(),
                            vegetablesHeifaScoreFemale = values.getOrNull(9)?.toFloatOrNull(),
                            vegetablesWithLegumesAllocatedServeSize = values.getOrNull(10)?.toFloatOrNull(),
                            legumesAllocatedVegetables = values.getOrNull(11)?.toFloatOrNull(),
                            vegetablesVariationsScore = values.getOrNull(12)?.toFloatOrNull(),
                            vegetablesCruciferous = values.getOrNull(13)?.toFloatOrNull(),
                            vegetablesTuberAndBulb = values.getOrNull(14)?.toFloatOrNull(),
                            vegetablesOther = values.getOrNull(15)?.toFloatOrNull(),
                            legumes = values.getOrNull(16)?.toFloatOrNull(),
                            vegetablesGreen = values.getOrNull(17)?.toFloatOrNull(),
                            vegetablesRedAndOrange = values.getOrNull(18)?.toFloatOrNull(),
                            fruitHeifaScoreMale = values.getOrNull(19)?.toFloatOrNull(),
                            fruitHeifaScoreFemale = values.getOrNull(20)?.toFloatOrNull(),
                            fruitServeSize = values.getOrNull(21)?.toFloatOrNull(),
                            fruitVariationsScore = values.getOrNull(22)?.toFloatOrNull(),
                            fruitPome = values.getOrNull(23)?.toFloatOrNull(),
                            fruitTropicalAndSubtropical = values.getOrNull(24)?.toFloatOrNull(),
                            fruitBerry = values.getOrNull(25)?.toFloatOrNull(),
                            fruitStone = values.getOrNull(26)?.toFloatOrNull(),
                            fruitCitrus = values.getOrNull(27)?.toFloatOrNull(),
                            fruitOther = values.getOrNull(28)?.toFloatOrNull(),
                            grainsAndCerealsHeifaScoreMale = values.getOrNull(29)?.toFloatOrNull(),
                            grainsAndCerealsHeifaScoreFemale = values.getOrNull(30)?.toFloatOrNull(),
                            grainsAndCerealsServeSize = values.getOrNull(31)?.toFloatOrNull(),
                            grainsAndCerealsNonWholeGrains = values.getOrNull(32)?.toFloatOrNull(),
                            wholeGrainsHeifaScoreMale = values.getOrNull(33)?.toFloatOrNull(),
                            wholeGrainsHeifaScoreFemale = values.getOrNull(34)?.toFloatOrNull(),
                            wholeGrainsServeSize = values.getOrNull(35)?.toFloatOrNull(),
                            meatAndAlternativesHeifaScoreMale = values.getOrNull(36)?.toFloatOrNull(),
                            meatAndAlternativesHeifaScoreFemale = values.getOrNull(37)?.toFloatOrNull(),
                            meatAndAlternativesWithLegumesAllocatedServeSize = values.getOrNull(38)?.toFloatOrNull(),
                            legumesAllocatedMeatAndAlternatives = values.getOrNull(39)?.toFloatOrNull(),
                            dairyAndAlternativesHeifaScoreMale = values.getOrNull(40)?.toFloatOrNull(),
                            dairyAndAlternativesHeifaScoreFemale = values.getOrNull(41)?.toFloatOrNull(),
                            dairyAndAlternativesServeSize = values.getOrNull(42)?.toFloatOrNull(),
                            sodiumHeifaScoreMale = values.getOrNull(43)?.toFloatOrNull(),
                            sodiumHeifaScoreFemale = values.getOrNull(44)?.toFloatOrNull(),
                            sodiumMgMilligrams = values.getOrNull(45)?.toFloatOrNull(),
                            alcoholHeifaScoreMale = values.getOrNull(46)?.toFloatOrNull(),
                            alcoholHeifaScoreFemale = values.getOrNull(47)?.toFloatOrNull(),
                            alcoholStandardDrinks = values.getOrNull(48)?.toFloatOrNull(),
                            waterHeifaScoreMale = values.getOrNull(49)?.toFloatOrNull(),
                            waterHeifaScoreFemale = values.getOrNull(50)?.toFloatOrNull(),
                            water = values.getOrNull(51)?.toFloatOrNull(),
                            waterTotalMl = values.getOrNull(52)?.toFloatOrNull(),
                            beverageTotalMl = values.getOrNull(53)?.toFloatOrNull(),
                            sugarHeifaScoreMale = values.getOrNull(54)?.toFloatOrNull(),
                            sugarHeifaScoreFemale = values.getOrNull(55)?.toFloatOrNull(),
                            sugar = values.getOrNull(56)?.toFloatOrNull(),
                            saturatedFatHeifaScoreMale = values.getOrNull(57)?.toFloatOrNull(),
                            saturatedFatHeifaScoreFemale = values.getOrNull(58)?.toFloatOrNull(),
                            saturatedFat = values.getOrNull(59)?.toFloatOrNull(),
                            unsaturatedFatHeifaScoreMale = values.getOrNull(60)?.toFloatOrNull(),
                            unsaturatedFatHeifaScoreFemale = values.getOrNull(61)?.toFloatOrNull(),
                            unsaturatedFatServeSize = values.getOrNull(62)?.toFloatOrNull()
                        )
                    )
                }
            }
        }
    } catch (e: Exception) {
        e.printStackTrace()
    }
    return patients
}
