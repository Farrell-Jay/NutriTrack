// AI ASSISTED: Some parts of this code have been developed with the assistance of AI
package com.example.assignment1.data

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "food_intakes",
    foreignKeys = [
        ForeignKey(
            entity = Patient::class,
            parentColumns = ["userId"],
            childColumns = ["patientId"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class FoodIntake(
    @PrimaryKey
    val patientId: String,
    
    // Checkbox states
    val fruits: Boolean,
    val vegetables: Boolean,
    val grains: Boolean,
    val redMeat: Boolean,
    val seafood: Boolean,
    val poultry: Boolean,
    val fish: Boolean,
    val eggs: Boolean,
    val nutsSeeds: Boolean,
    
    // Dropdown selection
    val dropdownSelection: String,
    
    // Time-related fields
    val sleepTime: String,
    val wakeUpTime: String,
    val biggestMealTime: String
) 