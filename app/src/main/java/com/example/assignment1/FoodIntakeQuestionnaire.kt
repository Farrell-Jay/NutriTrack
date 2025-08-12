// AI ASSISTED: Some parts of this code have been developed with the assistance of AI
package com.example.assignment1


import android.app.TimePickerDialog
import android.content.Context
import android.os.Bundle
import android.content.Intent
import android.icu.util.Calendar
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
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.AlertDialogDefaults
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.unit.dp
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.remember
import androidx.compose.ui.draw.clip
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.assignment1.data.FoodIntake
import com.example.assignment1.ui.PatientViewModel
import com.example.assignment1.ui.theme.Assignment1Theme
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch

class FoodIntakeQuestionnaire : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Questionnaire()
        }
    }
}

@Composable
fun Greeting3(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview3() {
    Assignment1Theme {
        Greeting3("Android")
    }
}

@Composable
fun Questionnaire() {
    val context = LocalContext.current
    val viewModel: PatientViewModel = viewModel()
    val loginPref = context.getSharedPreferences("currentUser", Context.MODE_PRIVATE)
    val userID = loginPref.getString("currentUserID", "ID Not Registered") ?: "ID Not Registered"

    // Collect food intake data
    val allFoodIntake by viewModel.getAllFoodIntakes().collectAsState(initial = emptyList())
    var patientFoodIntake = allFoodIntake.find { it.patientId == userID }

    // Dialog states
    var showDialog by remember { mutableStateOf(false) }
    var showDialog2 by remember { mutableStateOf(false) }
    var showDialog3 by remember { mutableStateOf(false) }
    var showDialog4 by remember { mutableStateOf(false) }
    var showDialog5 by remember { mutableStateOf(false) }
    var showDialog6 by remember { mutableStateOf(false) }

    // Checkbox states
    val mCheckBoxStateFruits = remember { mutableStateOf(false) }
    val mCheckBoxStateVegetables = remember { mutableStateOf(false) }
    val mCheckBoxStateGrains = remember { mutableStateOf(false) }
    val mCheckBoxStateRed_Meat = remember { mutableStateOf(false) }
    val mCheckBoxStateSeafood = remember { mutableStateOf(false) }
    val mCheckBoxStatePoultry = remember { mutableStateOf(false) }
    val mCheckBoxStateFish = remember { mutableStateOf(false) }
    val mCheckBoxStateEggs = remember { mutableStateOf(false) }
    val mCheckBoxStateNutsSeeds = remember { mutableStateOf(false) }

    // Dropdown state
    var expanded by remember { mutableStateOf(false) }
    var optionPickedDropdown by remember { mutableStateOf("") }

    // Time picker states
    val sleepTime = remember { mutableStateOf("00:00") }
    val mTimePickerDialog1 = TimePickerFun(sleepTime)

    val wakeUpTime = remember { mutableStateOf("00:00") }
    val mTimePickerDialog2 = TimePickerFun(wakeUpTime)

    val biggestMealTime = remember { mutableStateOf("00:00") }
    val mTimePickerDialog3 = TimePickerFun(biggestMealTime)

    LaunchedEffect(patientFoodIntake) {
        patientFoodIntake?.let {
            mCheckBoxStateFruits.value = it.fruits
            mCheckBoxStateVegetables.value = it.vegetables
            mCheckBoxStateGrains.value = it.grains
            mCheckBoxStateRed_Meat.value = it.redMeat
            mCheckBoxStateSeafood.value = it.seafood
            mCheckBoxStatePoultry.value = it.poultry
            mCheckBoxStateFish.value = it.fish
            mCheckBoxStateEggs.value = it.eggs
            mCheckBoxStateNutsSeeds.value = it.nutsSeeds

            optionPickedDropdown = it.dropdownSelection

            sleepTime.value = it.sleepTime
            wakeUpTime.value = it.wakeUpTime
            biggestMealTime.value = it.biggestMealTime
        }
    }

    // Function to save data to database
    fun saveToDatabase() {
        try {
            val newFoodIntake = FoodIntake(
                patientId = userID,
                fruits = mCheckBoxStateFruits.value,
                vegetables = mCheckBoxStateVegetables.value,
                grains = mCheckBoxStateGrains.value,
                redMeat = mCheckBoxStateRed_Meat.value,
                seafood = mCheckBoxStateSeafood.value,
                poultry = mCheckBoxStatePoultry.value,
                fish = mCheckBoxStateFish.value,
                eggs = mCheckBoxStateEggs.value,
                nutsSeeds = mCheckBoxStateNutsSeeds.value,
                dropdownSelection = optionPickedDropdown,
                sleepTime = sleepTime.value,
                wakeUpTime = wakeUpTime.value,
                biggestMealTime = biggestMealTime.value
            )

            viewModel.updateFoodIntake(newFoodIntake)
            Toast.makeText(context, "Preferences saved successfully!", Toast.LENGTH_SHORT).show()
        } catch (e: Exception) {
            Toast.makeText(context, "Error saving data: ${e.message}", Toast.LENGTH_SHORT).show()
        }
    }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color(0xffFFDBBB) // Light orange background
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Header
            Text(
                text = "Food Intake Questionnaire",
                style = MaterialTheme.typography.headlineMedium.copy(
                    fontWeight = FontWeight.Bold,
                    color = Color(0xffFF8000)
                ),
                modifier = Modifier.padding(bottom = 8.dp)
            )

            // Food Categories Section
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Text(
                        text = "Food Categories",
                        style = MaterialTheme.typography.titleLarge.copy(
                            color = Color(0xffFF8000),
                            fontWeight = FontWeight.Bold
                        )
                    )

                    Text(
                        text = "Select all food categories within your diet",
                        style = MaterialTheme.typography.bodyMedium.copy(
                            color = Color.Gray
                        )
                    )

                    // Food category checkboxes in a grid
                    Column(
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            FoodCategoryCheckbox(
                                text = "Fruits",
                                checked = mCheckBoxStateFruits.value,
                                onCheckedChange = { mCheckBoxStateFruits.value = it }
                            )
                            FoodCategoryCheckbox(
                                text = "Veget-\nables",
                                checked = mCheckBoxStateVegetables.value,
                                onCheckedChange = { mCheckBoxStateVegetables.value = it }
                            )
                            FoodCategoryCheckbox(
                                text = "Grains",
                                checked = mCheckBoxStateGrains.value,
                                onCheckedChange = { mCheckBoxStateGrains.value = it }
                            )
                        }

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            FoodCategoryCheckbox(
                                text = "Red \nMeat",
                                checked = mCheckBoxStateRed_Meat.value,
                                onCheckedChange = { mCheckBoxStateRed_Meat.value = it }
                            )
                            FoodCategoryCheckbox(
                                text = "Sea-\nfood",
                                checked = mCheckBoxStateSeafood.value,
                                onCheckedChange = { mCheckBoxStateSeafood.value = it }
                            )
                            FoodCategoryCheckbox(
                                text = "Poultry",
                                checked = mCheckBoxStatePoultry.value,
                                onCheckedChange = { mCheckBoxStatePoultry.value = it }
                            )
                        }

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            FoodCategoryCheckbox(
                                text = "Fish",
                                checked = mCheckBoxStateFish.value,
                                onCheckedChange = { mCheckBoxStateFish.value = it }
                            )
                            FoodCategoryCheckbox(
                                text = "Eggs",
                                checked = mCheckBoxStateEggs.value,
                                onCheckedChange = { mCheckBoxStateEggs.value = it }
                            )
                            FoodCategoryCheckbox(
                                text = "Nuts/\nSeeds",
                                checked = mCheckBoxStateNutsSeeds.value,
                                onCheckedChange = { mCheckBoxStateNutsSeeds.value = it }
                            )
                        }
                    }
                }
            }

            // Persona Section
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Text(
                        text = "Your Persona",
                        style = MaterialTheme.typography.titleLarge.copy(
                            color = Color(0xffFF8000),
                            fontWeight = FontWeight.Bold
                        )
                    )

                    Text(
                        text = "People can be broadly classified into 6 different types based on their eating preferences. Select the type that best fits you!",
                        style = MaterialTheme.typography.bodyMedium.copy(
                            color = Color.Gray
                        )
                    )

                    // Persona buttons in a grid
                    Column(
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            PersonaButton(
                                text = "Health Devotee",
                                onClick = { showDialog = true },
                                modifier = Modifier.weight(1f)
                            )
                            PersonaButton(
                                text = "Mindful Eater",
                                onClick = { showDialog2 = true },
                                modifier = Modifier.weight(1f)
                            )
                        }

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            PersonaButton(
                                text = "Wellness Striver",
                                onClick = { showDialog3 = true },
                                modifier = Modifier.weight(1f)
                            )
                            PersonaButton(
                                text = "Balance Seeker",
                                onClick = { showDialog4 = true },
                                modifier = Modifier.weight(1f)
                            )
                        }

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            PersonaButton(
                                text = "Health Procrastinator",
                                onClick = { showDialog5 = true },
                                modifier = Modifier.weight(1f)
                            )
                            PersonaButton(
                                text = "Food Carefree",
                                onClick = { showDialog6 = true },
                                modifier = Modifier.weight(1f)
                            )
                        }
                    }

                    // Persona selection dropdown
                    OutlinedTextField(
                        value = optionPickedDropdown,
                        onValueChange = { optionPickedDropdown = it },
                        label = { Text("Selected Persona") },
                        enabled = false,
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { expanded = true },
                        trailingIcon = {
                            IconButton(onClick = { expanded = true }) {
                                Icon(Icons.Default.ArrowDropDown, contentDescription = "Dropdown")
                            }
                        },
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = Color(0xffFF8000),
                            unfocusedBorderColor = Color.Gray
                        )
                    )

                    DropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false },
                        modifier = Modifier.fillMaxWidth(0.9f)
                    ) {
                        listOf(
                            "Health Devotee",
                            "Mindful Eater",
                            "Wellness Striver",
                            "Balance Seeker",
                            "Health Procrastinator",
                            "Food Carefree"
                        ).forEach { persona ->
                            DropdownMenuItem(
                                text = { Text(persona) },
                                onClick = {
                                    expanded = false
                                    optionPickedDropdown = persona
                                }
                            )
                        }
                    }
                }
            }

            // Timings Section
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Text(
                        text = "Timings",
                        style = MaterialTheme.typography.titleLarge.copy(
                            color = Color(0xffFF8000),
                            fontWeight = FontWeight.Bold
                        )
                    )

                    // Time pickers with fixed width labels
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Sleep Time",
                            style = MaterialTheme.typography.bodyMedium.copy(
                                fontWeight = FontWeight.Medium
                            ),
                            modifier = Modifier.width(120.dp)
                        )
                        OutlinedTextField(
                            value = sleepTime.value,
                            onValueChange = { },
                            enabled = false,
                            modifier = Modifier
                                .width(200.dp)
                                .clickable { mTimePickerDialog1.show() },
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = Color(0xffFF8000),
                                unfocusedBorderColor = Color.Gray
                            )
                        )
                    }

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Wake Up Time",
                            style = MaterialTheme.typography.bodyMedium.copy(
                                fontWeight = FontWeight.Medium
                            ),
                            modifier = Modifier.width(120.dp)
                        )
                        OutlinedTextField(
                            value = wakeUpTime.value,
                            onValueChange = { },
                            enabled = false,
                            modifier = Modifier
                                .width(200.dp)
                                .clickable { mTimePickerDialog2.show() },
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = Color(0xffFF8000),
                                unfocusedBorderColor = Color.Gray
                            )
                        )
                    }

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Largest Meal Time",
                            style = MaterialTheme.typography.bodyMedium.copy(
                                fontWeight = FontWeight.Medium
                            ),
                            modifier = Modifier.width(120.dp)
                        )
                        OutlinedTextField(
                            value = biggestMealTime.value,
                            onValueChange = { },
                            enabled = false,
                            modifier = Modifier
                                .width(200.dp)
                                .clickable { mTimePickerDialog3.show() },
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = Color(0xffFF8000),
                                unfocusedBorderColor = Color.Gray
                            )
                        )
                    }
                }
            }

            // Save Button
            Button(
                onClick = {
                    saveToDatabase()
                    context.startActivity(Intent(context, HomeScreen::class.java))
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xffFF8000)
                ),
                shape = RoundedCornerShape(8.dp)
            ) {
                Text(
                    text = "Save Preferences",
                    style = MaterialTheme.typography.titleMedium.copy(
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    )
                )
            }
        }
    }

    // Persona dialogs
    if (showDialog) {
        PersonaDialog(
            title = "Health Devotee",
            imageRes = R.drawable.persona_1,
            description = "A health devotee is deeply committed to maintaining a balanced lifestyle, incorporating regular exercise, a nutritious diet, and mindful practices. They stay informed about wellness trends, prioritize mental health, and actively seek ways to improve their physical well-being, often serving as a wellness advocate in their community.",
            onDismiss = { showDialog = false }
        )
    }
    if (showDialog2) {
        PersonaDialog(
            title = "Mindful Eater",
            imageRes = R.drawable.persona_2,
            description = "A mindful eater practices conscious eating, focusing on savoring each bite " +
                    "and understanding the nutritional value of their meals. They prioritize " +
                    "eating whole, unprocessed foods and listen to their body's cues to manage portion sizes. " +
                    "This approach not only enhances their physical health but also their overall well-being.",
            onDismiss = { showDialog2 = false }
        )
    }
    if (showDialog3) {
        PersonaDialog(
            title = "Wellness Striver",
            imageRes = R.drawable.persona_3,
            description = "A wellness striver actively seeks to improve their overall health and " +
                    "well-being by adopting healthier habits. They balance exercise, " +
                    "nutrition, and mental health practices, always aiming to better " +
                    "their physical and emotional states. This persona is committed to " +
                    "continuous personal growth and often experiments with new wellness trends and routines.",
            onDismiss = { showDialog3 = false }
        )
    }
    if (showDialog4) {
        PersonaDialog(
            title = "Balance Seeker",
            imageRes = R.drawable.persona_4,
            description = "A Balance Seeker is someone who strives for moderation and variety in both nutrition and " +
                    "lifestyle. Rather than following strict diets or extreme routines, Balance Seekers aim to " +
                    "maintain a healthy equilibrium — enjoying nutritious foods while also allowing occasional " +
                    "indulgences. They value flexibility, mindful eating, and sustainable habits over quick fixes or rigid plans.",
            onDismiss = { showDialog4 = false }
        )
    }
    if (showDialog5) {
        PersonaDialog(
            title = "Health Procrastinator",
            imageRes = R.drawable.persona_5,
            description = "A health procrastinator recognizes the importance of wellness but struggles to consistently prioritize it. " +
                    "They often plan to start new health routines or diets \"tomorrow\" or \"next week,\" frequently " +
                    "pushing back fitness goals. Despite their good intentions, they find it challenging to turn their " +
                    "wellness plans into action.",
            onDismiss = { showDialog5 = false }
        )
    }
    if (showDialog6) {
        PersonaDialog(
            title = "Food Carefree",
            imageRes = R.drawable.persona_6,
            description = "A Food Carefree persona is relaxed about their dietary choices, " +
                    "often prioritizing convenience and taste over nutritional content. " +
                    "They enjoy indulging in their favorite foods without much concern for " +
                    "calories or health impacts, valuing immediate pleasure and ease over long-term dietary goals.",
            onDismiss = { showDialog6 = false }
        )
    }



}

@Composable
fun FoodCategoryCheckbox(
    text: String,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(4.dp)
    ) {
        Checkbox(
            checked = checked,
            onCheckedChange = onCheckedChange,
            colors = CheckboxDefaults.colors(
                checkedColor = Color(0xffFF8000),
                uncheckedColor = Color.Gray
            )
        )
        Text(
            text = text,
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.padding(start = 4.dp)
        )
    }
}

@Composable
fun PersonaButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Button(
        onClick = onClick,
        modifier = modifier.height(64.dp), // Increased height
        colors = ButtonDefaults.buttonColors(
            containerColor = Color(0xffFF8000)
        ),
        shape = RoundedCornerShape(8.dp)
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.bodyLarge.copy(
                color = Color.White,
                fontWeight = FontWeight.Medium
            ),
            maxLines = 2,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
fun TimePickerFun(mTime: MutableState<String>): TimePickerDialog{

    val mContext = LocalContext.current
    val mCalendar = Calendar.getInstance()

    val mHour = mCalendar.get(Calendar.HOUR_OF_DAY)
    val mMinute = mCalendar.get(Calendar.MINUTE)

    mCalendar.time = Calendar.getInstance().time

    return TimePickerDialog(
        mContext,
        {   _, mHour: Int, mMinute: Int ->
            mTime.value = "$mHour: $mMinute"
        }, mHour, mMinute, false
    )
}

@Composable
fun PersonaDialog(
    title: String,
    imageRes: Int,
    description: String,
    onDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(
                text = title,
                style = MaterialTheme.typography.headlineSmall.copy(
                    fontWeight = FontWeight.Bold,
                    color = Color(0xffFF8000)
                )
            )
        },
        text = {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Image(
                    painter = painterResource(id = imageRes),
                    contentDescription = title,
                    modifier = Modifier
                        .size(200.dp)
                        .clip(RoundedCornerShape(8.dp))
                )
                Text(
                    text = description,
                    style = MaterialTheme.typography.bodyMedium.copy(
                        color = Color.Gray
                    )
                )
            }
        },
        confirmButton = {
            TextButton(onClick = onDismiss) {
                Text(
                    "Dismiss",
                    style = MaterialTheme.typography.labelLarge.copy(
                        color = Color(0xffFF8000)
                    )
                )
            }
        }
    )
}