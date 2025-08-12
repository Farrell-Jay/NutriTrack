//AI ASSISTED
package com.example.assignment1.ui

import android.app.Application
import androidx.compose.runtime.collectAsState
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.assignment1.data.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class PatientViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: PatientRepository
    val allPatients: Flow<List<Patient>>

    init {
        val database = AppDatabase.getDatabase(application)
        repository = PatientRepository(database.patientDao(), database.foodIntakeDao(), database.nutriCoachTipsDao())
        allPatients = repository.allPatients
    }

    // Food Intake related functions
    fun getAllFoodIntakes(): Flow<List<FoodIntake>> {
        return repository.getAllFoodIntakes()
    }

    fun getAllNutriCoachTips(): Flow<List<NutriCoachTips>> {
        return repository.getAllNutriCoachTips()
    }

    fun registerFoodIntake(userId: String) {
        viewModelScope.launch {
            try {
                var foodIntake = repository.getFoodIntakeByID(userId)
                if (foodIntake != null) {
                    return@launch
                }

                // Create new patient with default values
                val newFoodIntake = FoodIntake(
                    patientId = userId,
                    fruits = false,
                    vegetables = false,
                    grains = false,
                    redMeat = false,
                    seafood = false,
                    poultry = false,
                    fish = false,
                    eggs = false,
                    nutsSeeds = false,
                    dropdownSelection = "",
                    sleepTime = "00:00",
                    wakeUpTime = "00:00",
                    biggestMealTime = "00:00"
                )

                repository.insertFoodIntake(newFoodIntake)

                // Create new NutriCoachTips entry
                val newNutriCoachTips = NutriCoachTips(patientId = userId)
                repository.insertNutriCoachTips(newNutriCoachTips)
            } catch (e: Exception) {
                android.util.Log.e("PatientViewModel", "Error registering food intake: ${e.message}", e)
            }
        }
    }

    fun updateFoodIntake(foodIntake: FoodIntake) {
        viewModelScope.launch{
            repository.updateFoodIntake(foodIntake)
        }
    }

    fun deleteFoodIntake(foodIntake: FoodIntake) {
        viewModelScope.launch{
            repository.deleteFoodIntake(foodIntake)
        }
    }

    fun addNutriCoachTip(patientId: String, tip: String) {
        viewModelScope.launch {
            try {
                var nutriCoachTips = repository.getNutriCoachTipsByID(patientId)
                if (nutriCoachTips == null) {
                    nutriCoachTips = NutriCoachTips(patientId = patientId)
                    nutriCoachTips = nutriCoachTips.addTip(tip)
                    repository.insertNutriCoachTips(nutriCoachTips)
                } else {
                    nutriCoachTips = nutriCoachTips.addTip(tip)
                    repository.updateNutriCoachTips(nutriCoachTips)
                }
            } catch (e: Exception) {
                android.util.Log.e("PatientViewModel", "Error adding NutriCoach tip: ${e.message}", e)
            }
        }
    }

    fun authenticatePatient(userId: String, password: String, onResult: (Patient?) -> Unit) {
        viewModelScope.launch {
            val patient = repository.authenticatePatient(userId, password)
            onResult(patient)
        }
    }

    fun registerPatient(name: String, userId: String, phoneNumber: String, password: String, onResult: (Boolean) -> Unit) {
        viewModelScope.launch {
            // Check if user ID already exists
            val existingPatient = repository.getPatientById(userId)
            if (existingPatient != null) {
                onResult(false) // User ID already exists
                return@launch
            }
            
            // Create new patient with default values
            val newPatient = Patient(
                name = "", // Will be updated later
                password = password,
                phoneNumber = phoneNumber,
                userId = userId,
                sex = "", // Will be updated later
                heifaTotalScoreMale = null,
                heifaTotalScoreFemale = null,
                discretionaryHeifaScoreMale = null,
                discretionaryHeifaScoreFemale = null,
                discretionaryServeSize = null,
                vegetablesHeifaScoreMale = null,
                vegetablesHeifaScoreFemale = null,
                vegetablesWithLegumesAllocatedServeSize = null,
                legumesAllocatedVegetables = null,
                vegetablesVariationsScore = null,
                vegetablesCruciferous = null,
                vegetablesTuberAndBulb = null,
                vegetablesOther = null,
                legumes = null,
                vegetablesGreen = null,
                vegetablesRedAndOrange = null,
                fruitHeifaScoreMale = null,
                fruitHeifaScoreFemale = null,
                fruitServeSize = null,
                fruitVariationsScore = null,
                fruitPome = null,
                fruitTropicalAndSubtropical = null,
                fruitBerry = null,
                fruitStone = null,
                fruitCitrus = null,
                fruitOther = null,
                grainsAndCerealsHeifaScoreMale = null,
                grainsAndCerealsHeifaScoreFemale = null,
                grainsAndCerealsServeSize = null,
                grainsAndCerealsNonWholeGrains = null,
                wholeGrainsHeifaScoreMale = null,
                wholeGrainsHeifaScoreFemale = null,
                wholeGrainsServeSize = null,
                meatAndAlternativesHeifaScoreMale = null,
                meatAndAlternativesHeifaScoreFemale = null,
                meatAndAlternativesWithLegumesAllocatedServeSize = null,
                legumesAllocatedMeatAndAlternatives = null,
                dairyAndAlternativesHeifaScoreMale = null,
                dairyAndAlternativesHeifaScoreFemale = null,
                dairyAndAlternativesServeSize = null,
                sodiumHeifaScoreMale = null,
                sodiumHeifaScoreFemale = null,
                sodiumMgMilligrams = null,
                alcoholHeifaScoreMale = null,
                alcoholHeifaScoreFemale = null,
                alcoholStandardDrinks = null,
                waterHeifaScoreMale = null,
                waterHeifaScoreFemale = null,
                water = null,
                waterTotalMl = null,
                beverageTotalMl = null,
                sugarHeifaScoreMale = null,
                sugarHeifaScoreFemale = null,
                sugar = null,
                saturatedFatHeifaScoreMale = null,
                saturatedFatHeifaScoreFemale = null,
                saturatedFat = null,
                unsaturatedFatHeifaScoreMale = null,
                unsaturatedFatHeifaScoreFemale = null,
                unsaturatedFatServeSize = null
            )
            
            repository.insertPatient(newPatient)
            
            // Create a new FoodIntake entry for the patient
            val newFoodIntake = FoodIntake(
                patientId = userId,
                fruits = false,
                vegetables = false,
                grains = false,
                redMeat = false,
                seafood = false,
                poultry = false,
                fish = false,
                eggs = false,
                nutsSeeds = false,
                dropdownSelection = "",
                sleepTime = "00:00",
                wakeUpTime = "00:00",
                biggestMealTime = "00:00"
            )
            
            repository.insertFoodIntake(newFoodIntake)

            // Create a new NutriCoachTips entry for the patient
            val newNutriCoachTips = NutriCoachTips(patientId = userId)
            repository.insertNutriCoachTips(newNutriCoachTips)
            
            onResult(true)
        }
    }

    fun updatePatientProfile(userId: String, name: String, password: String, onResult: (Boolean) -> Unit) {
        viewModelScope.launch {
            val patient = repository.getPatientById(userId)
            if (patient != null) {
                val updatedPatient = patient.copy(name = name, password = password)
                repository.updatePatient(updatedPatient)
                onResult(true)
            } else {
                onResult(false)
            }
        }
    }

    fun updatePatientScores(
        userId: String,
        heifaTotalScoreMale: Float? = null,
        heifaTotalScoreFemale: Float? = null,
        discretionaryHeifaScoreMale: Float? = null,
        discretionaryHeifaScoreFemale: Float? = null,
        discretionaryServeSize: Float? = null,
        onResult: (Boolean) -> Unit
    ) {
        viewModelScope.launch {
            val patient = repository.getPatientById(userId)
            if (patient != null) {
                val updatedPatient = patient.copy(
                    heifaTotalScoreMale = heifaTotalScoreMale ?: patient.heifaTotalScoreMale,
                    heifaTotalScoreFemale = heifaTotalScoreFemale ?: patient.heifaTotalScoreFemale,
                    discretionaryHeifaScoreMale = discretionaryHeifaScoreMale ?: patient.discretionaryHeifaScoreMale,
                    discretionaryHeifaScoreFemale = discretionaryHeifaScoreFemale ?: patient.discretionaryHeifaScoreFemale,
                    discretionaryServeSize = discretionaryServeSize ?: patient.discretionaryServeSize
                )
                repository.updatePatient(updatedPatient)
                onResult(true)
            } else {
                onResult(false)
            }
        }
    }

    fun loadInitialData(patients: List<Patient>) {
        viewModelScope.launch {
            repository.insertAllPatients(patients)
            
            // Create FoodIntake and NutriCoachTips entries for each patient
            patients.forEach { patient ->
                val newFoodIntake = FoodIntake(
                    patientId = patient.userId,
                    fruits = false,
                    vegetables = false,
                    grains = false,
                    redMeat = false,
                    seafood = false,
                    poultry = false,
                    fish = false,
                    eggs = false,
                    nutsSeeds = false,
                    dropdownSelection = "",
                    sleepTime = "00:00",
                    wakeUpTime = "00:00",
                    biggestMealTime = "00:00"
                )
                repository.insertFoodIntake(newFoodIntake)

                val newNutriCoachTips = NutriCoachTips(patientId = patient.userId)
                repository.insertNutriCoachTips(newNutriCoachTips)
            }
        }
    }

    fun isFirstLaunch(onResult: (Boolean) -> Unit) {
        viewModelScope.launch {
            val count = repository.getPatientCount()
            onResult(count == 0f)
        }
    }

    fun printAllPatients() {
        viewModelScope.launch {
            allPatients.collect { patients ->
                patients.forEach { patient ->
                    android.util.Log.d("PatientDatabase", """
                        Patient:
                        Name: ${patient.name}
                        UserID: ${patient.userId}
                        Phone: ${patient.phoneNumber}
                        Sex: ${patient.sex}
                        HEIFA Total Score (Male): ${patient.heifaTotalScoreMale}
                        HEIFA Total Score (Female): ${patient.heifaTotalScoreFemale}
                        Discretionary HEIFA Score (Male): ${patient.discretionaryHeifaScoreMale}
                        Discretionary HEIFA Score (Female): ${patient.discretionaryHeifaScoreFemale}
                        Discretionary Serve Size: ${patient.discretionaryServeSize}
                    """.trimIndent())
                }
            }
        }
    }

    // Returns a map of all score-related fields for a given patient
    fun getAllScoresForPatient(patient: Patient): Map<String, Float?> {
        return mapOf(
            "heifaTotalScoreMale" to patient.heifaTotalScoreMale,
            "heifaTotalScoreFemale" to patient.heifaTotalScoreFemale,
            "discretionaryHeifaScoreMale" to patient.discretionaryHeifaScoreMale,
            "discretionaryHeifaScoreFemale" to patient.discretionaryHeifaScoreFemale,
            "discretionaryServeSize" to patient.discretionaryServeSize,
            "vegetablesHeifaScoreMale" to patient.vegetablesHeifaScoreMale,
            "vegetablesHeifaScoreFemale" to patient.vegetablesHeifaScoreFemale,
            "vegetablesWithLegumesAllocatedServeSize" to patient.vegetablesWithLegumesAllocatedServeSize,
            "legumesAllocatedVegetables" to patient.legumesAllocatedVegetables,
            "vegetablesVariationsScore" to patient.vegetablesVariationsScore,
            "vegetablesCruciferous" to patient.vegetablesCruciferous,
            "vegetablesTuberAndBulb" to patient.vegetablesTuberAndBulb,
            "vegetablesOther" to patient.vegetablesOther,
            "legumes" to patient.legumes,
            "vegetablesGreen" to patient.vegetablesGreen,
            "vegetablesRedAndOrange" to patient.vegetablesRedAndOrange,
            "fruitHeifaScoreMale" to patient.fruitHeifaScoreMale,
            "fruitHeifaScoreFemale" to patient.fruitHeifaScoreFemale,
            "fruitServeSize" to patient.fruitServeSize,
            "fruitVariationsScore" to patient.fruitVariationsScore,
            "fruitPome" to patient.fruitPome,
            "fruitTropicalAndSubtropical" to patient.fruitTropicalAndSubtropical,
            "fruitBerry" to patient.fruitBerry,
            "fruitStone" to patient.fruitStone,
            "fruitCitrus" to patient.fruitCitrus,
            "fruitOther" to patient.fruitOther,
            "grainsAndCerealsHeifaScoreMale" to patient.grainsAndCerealsHeifaScoreMale,
            "grainsAndCerealsHeifaScoreFemale" to patient.grainsAndCerealsHeifaScoreFemale,
            "grainsAndCerealsServeSize" to patient.grainsAndCerealsServeSize,
            "grainsAndCerealsNonWholeGrains" to patient.grainsAndCerealsNonWholeGrains,
            "wholeGrainsHeifaScoreMale" to patient.wholeGrainsHeifaScoreMale,
            "wholeGrainsHeifaScoreFemale" to patient.wholeGrainsHeifaScoreFemale,
            "wholeGrainsServeSize" to patient.wholeGrainsServeSize,
            "meatAndAlternativesHeifaScoreMale" to patient.meatAndAlternativesHeifaScoreMale,
            "meatAndAlternativesHeifaScoreFemale" to patient.meatAndAlternativesHeifaScoreFemale,
            "meatAndAlternativesWithLegumesAllocatedServeSize" to patient.meatAndAlternativesWithLegumesAllocatedServeSize,
            "legumesAllocatedMeatAndAlternatives" to patient.legumesAllocatedMeatAndAlternatives,
            "dairyAndAlternativesHeifaScoreMale" to patient.dairyAndAlternativesHeifaScoreMale,
            "dairyAndAlternativesHeifaScoreFemale" to patient.dairyAndAlternativesHeifaScoreFemale,
            "dairyAndAlternativesServeSize" to patient.dairyAndAlternativesServeSize,
            "sodiumHeifaScoreMale" to patient.sodiumHeifaScoreMale,
            "sodiumHeifaScoreFemale" to patient.sodiumHeifaScoreFemale,
            "sodiumMgMilligrams" to patient.sodiumMgMilligrams,
            "alcoholHeifaScoreMale" to patient.alcoholHeifaScoreMale,
            "alcoholHeifaScoreFemale" to patient.alcoholHeifaScoreFemale,
            "alcoholStandardDrinks" to patient.alcoholStandardDrinks,
            "waterHeifaScoreMale" to patient.waterHeifaScoreMale,
            "waterHeifaScoreFemale" to patient.waterHeifaScoreFemale,
            "water" to patient.water,
            "waterTotalMl" to patient.waterTotalMl,
            "beverageTotalMl" to patient.beverageTotalMl,
            "sugarHeifaScoreMale" to patient.sugarHeifaScoreMale,
            "sugarHeifaScoreFemale" to patient.sugarHeifaScoreFemale,
            "sugar" to patient.sugar,
            "saturatedFatHeifaScoreMale" to patient.saturatedFatHeifaScoreMale,
            "saturatedFatHeifaScoreFemale" to patient.saturatedFatHeifaScoreFemale,
            "saturatedFat" to patient.saturatedFat,
            "unsaturatedFatHeifaScoreMale" to patient.unsaturatedFatHeifaScoreMale,
            "unsaturatedFatHeifaScoreFemale" to patient.unsaturatedFatHeifaScoreFemale,
            "unsaturatedFatServeSize" to patient.unsaturatedFatServeSize
        )
    }

    fun verifyExistingUser(userId: String, phoneNumber: String, onResult: (Boolean) -> Unit) {
        viewModelScope.launch {
            val patient = repository.getPatientById(userId)
            if (patient != null && patient.phoneNumber == phoneNumber) {
                onResult(true) // User exists and phone number matches
            } else {
                onResult(false) // User doesn't exist or phone number doesn't match
            }
        }
    }

    fun getPatientById(userId: String, onResult: (Patient?) -> Unit) {
        viewModelScope.launch {
            val patient = repository.getPatientById(userId)
            onResult(patient)
        }
    }
} 