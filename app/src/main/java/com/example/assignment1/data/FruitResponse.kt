// AI ASSISTED: Some parts of this code have been developed with the assistance of AI
package com.example.assignment1.data

data class FruitResponse(
    val name: String,
    val family: String,
    val genus: String,
    val order: String,
    val nutritions: Nutrition
)

data class Nutrition(
    val carbohydrates: Double,
    val protein: Double,
    val fat: Double,
    val calories: Int,
    val sugar: Double
)
