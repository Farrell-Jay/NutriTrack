// AI ASSISTED: Some parts of this code have been developed with the assistance of AI
package com.example.assignment1

import android.content.Context
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.assignment1.ui.PatientViewModel
import com.example.assignment1.ui.theme.Assignment1Theme

class RegistrationScreen : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Assignment1Theme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Registration(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@Composable
fun Registration(modifier: Modifier = Modifier) {
    var userName by remember { mutableStateOf("") }
    var userId by remember { mutableStateOf("") }
    var phoneNumber by remember { mutableStateOf("") }
    var newPassword by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    val context = LocalContext.current
    val viewModel: PatientViewModel = viewModel()

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
                text = "Create Account",
                style = TextStyle(
                    fontSize = 32.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = Color(0xffFF8000)
                ),
                modifier = Modifier.padding(bottom = 32.dp)
            )

            OutlinedTextField(
                value = userName,
                onValueChange = { userName = it },
                label = { Text("Full Name") },
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
                value = phoneNumber,
                onValueChange = { phoneNumber = it },
                label = { Text("Phone Number") },
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
                value = newPassword,
                onValueChange = { newPassword = it },
                label = { Text("New Password") },
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
                value = confirmPassword,
                onValueChange = { confirmPassword = it },
                label = { Text("Confirm Password") },
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
                    if (newPassword == confirmPassword && newPassword != "") {
                        // First verify if the user exists
                        viewModel.verifyExistingUser(userId, phoneNumber) { exists ->
                            if (exists) {
                                // Check if user already has a password (is already registered)
                                viewModel.getPatientById(userId) { patient ->
                                    if (patient?.password?.isNotEmpty() == true) {
                                        Toast.makeText(context, "User already registered", Toast.LENGTH_LONG).show()
                                    } else {
                                        // User exists but not registered, update their profile
                                        viewModel.updatePatientProfile(userId, userName, newPassword) { success ->
                                            if (success) {
                                                Toast.makeText(context, "Profile created successfully", Toast.LENGTH_LONG).show()
                                                context.startActivity(android.content.Intent(context, LoginScreen::class.java))
                                                (context as? RegistrationScreen)?.finish()
                                            } else {
                                                Toast.makeText(context, "Error updating profile", Toast.LENGTH_LONG).show()
                                            }
                                        }
                                    }
                                }
                            } else {
                                // User doesn't exist, show error
                                Toast.makeText(context, "Invalid User ID/Phone Number", Toast.LENGTH_LONG).show()
                            }
                        }
                    } else if (newPassword == "") {
                        Toast.makeText(context, "Password can't be blank", Toast.LENGTH_LONG).show()
                    } else {
                        Toast.makeText(context, "Passwords must match", Toast.LENGTH_LONG).show()
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
                    text = "Register",
                    style = TextStyle(
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                )
            }

            TextButton(
                onClick = {
                    context.startActivity(android.content.Intent(context, LoginScreen::class.java))
                    (context as? RegistrationScreen)?.finish()
                },
                modifier = Modifier.padding(8.dp)
            ) {
                Text(
                    "Back to Login",
                    style = TextStyle(
                        fontSize = 16.sp,
                        color = Color(0xffFF8000)
                    )
                )
            }
        }
    }
} 