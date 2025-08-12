// AI ASSISTED: Some parts of this code have been developed with the assistance of AI
package com.example.assignment1.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface FoodIntakeDao {
    @Query("SELECT * FROM food_intakes")
    fun getAllFoodIntakes(): Flow<List<FoodIntake>>

    @Query("SELECT * FROM food_intakes WHERE patientId = :userId")
    fun getFoodIntakeByID(userId: String): FoodIntake?

    @Insert
    suspend fun insertFoodIntake(foodIntake: FoodIntake)

    @Update
    suspend fun updateFoodIntake(foodIntake: FoodIntake)

    @Delete
    suspend fun deleteFoodIntake(foodIntake: FoodIntake)
} 