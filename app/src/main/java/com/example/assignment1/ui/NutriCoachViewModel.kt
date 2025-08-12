package com.example.assignment1.ui

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.assignment1.data.ApiClient
import com.example.assignment1.data.FruitResponse
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import com.example.assignment1.data.GeminiClient
import com.example.assignment1.data.GeminiContent
import com.example.assignment1.data.GeminiPart
import com.example.assignment1.data.GeminiPrompt
import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.assignment1.data.AppDatabase
import com.example.assignment1.data.PatientRepository

class NutriCoachViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: PatientRepository

    init {
        val database = AppDatabase.getDatabase(application)
        repository = PatientRepository(database.patientDao(), database.foodIntakeDao(), database.nutriCoachTipsDao())
    }

    private val _fruitState = MutableStateFlow<FruitResponse?>(null)
    val fruitState: StateFlow<FruitResponse?> = _fruitState

    fun fetchFruit(fruitName: String) {
        viewModelScope.launch {
            try {
                val result = ApiClient.api.getFruitDetails(fruitName)
                _fruitState.value = result
            } catch (e: Exception) {
                Log.e("NutriCoach", "Error fetching fruit: ${e.message}")
                _fruitState.value = null
            }
        }
    }

    // ✅ New: Gemini AI motivational message
    private val _motivationalMessage = MutableStateFlow<String?>(null)
    val motivationalMessage: StateFlow<String?> = _motivationalMessage

    fun fetchMotivationalMessage(apiKey: String, dataset: String, patientId: String) {
        viewModelScope.launch {
            try {
                val prompt = GeminiPrompt(
                    contents = listOf(
                        GeminiContent(
                            role = "user",
                            parts = listOf(GeminiPart("I will send you some quick info of a patient we have in our" +
                                    "food questionnaire application, based on the data collected from our questionnaire, generate a short " +
                                    "encouraging message to help someone improve their fruit intake or increase their health in general. Here is the" +
                                    "results of their questionnaire: \n" +
                                    dataset
                            ))
                        )
                    )
                )
                val response = GeminiClient.api.generateMessage(apiKey, prompt)
                val tip = response.candidates.firstOrNull()?.content?.parts?.firstOrNull()?.text
                    ?: "No response."
                _motivationalMessage.value = tip

                // Store the tip in the database
                var nutriCoachTips = repository.getNutriCoachTipsByID(patientId)
                if (nutriCoachTips == null) {
                    nutriCoachTips = com.example.assignment1.data.NutriCoachTips(patientId = patientId)
                    nutriCoachTips = nutriCoachTips.addTip(tip)
                    repository.insertNutriCoachTips(nutriCoachTips)
                } else {
                    nutriCoachTips = nutriCoachTips.addTip(tip)
                    repository.updateNutriCoachTips(nutriCoachTips)
                }
            } catch (e: Exception) {
                _motivationalMessage.value = "Failed to load message."
                Log.e("NutriCoach", "Error fetching motivational message: ${e.message}")
            }
        }
    }

    // ✅ New: Gemini AI motivational message
    private val _patternMessage = MutableStateFlow<String?>(null)
    private val _patternMessage2 = MutableStateFlow<String?>(null)
    private val _patternMessage3 = MutableStateFlow<String?>(null)
    val patternMessage: StateFlow<String?> = _patternMessage
    val patternMessage2: StateFlow<String?> = _patternMessage2
    val patternMessage3: StateFlow<String?> = _patternMessage3

    fun fetchPatternMessage(apiKey: String, dataset: String) {
        viewModelScope.launch {
            try {
                val prompt = GeminiPrompt(
                    contents = listOf(
                        GeminiContent(
                            role = "user",
                            parts = listOf(GeminiPart("Generate me the 1st interesting pattern you have recieved from this " +
                                    "dataset and write it in the way of a fun fact: \n" +
                                    dataset
                            ))
                        )
                    )
                )
                val prompt2 = GeminiPrompt(
                    contents = listOf(
                        GeminiContent(
                            role = "user",
                            parts = listOf(GeminiPart("Generate me another interesting pattern you have recieved from this " +
                                    "dataset and write it in the way of a fun fact: \n" +
                                    dataset
                            ))
                        )
                    )
                )
                val prompt3 = GeminiPrompt(
                    contents = listOf(
                        GeminiContent(
                            role = "user",
                            parts = listOf(GeminiPart("Generate me an pattern never heard before from this dataset " +
                                    "and write it in the way of a fun fact: \n" +
                                    dataset
                            ))
                        )
                    )
                )
                val response = GeminiClient.api.generateMessage(apiKey, prompt)
                val response2 = GeminiClient.api.generateMessage(apiKey, prompt2)
                val response3 = GeminiClient.api.generateMessage(apiKey, prompt3)
                _patternMessage.value = response.candidates.firstOrNull()?.content?.parts?.firstOrNull()?.text
                _patternMessage2.value = response2.candidates.firstOrNull()?.content?.parts?.firstOrNull()?.text
                _patternMessage3.value = response3.candidates.firstOrNull()?.content?.parts?.firstOrNull()?.text
                    ?: "No response."
            } catch (e: Exception) {
                _patternMessage.value = "Failed to load message."
            }
        }
    }
}
