// AI ASSISTED: Some parts of this code have been developed with the assistance of AI
package com.example.assignment1.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface NutriCoachTipsDao {
    @Query("SELECT * FROM nutri_coach_tips")
    fun getAllNutriCoachTips(): Flow<List<NutriCoachTips>>

    @Query("SELECT * FROM nutri_coach_tips WHERE patientId = :patientId")
    suspend fun getNutriCoachTipsByID(patientId: String): NutriCoachTips?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNutriCoachTips(nutriCoachTips: NutriCoachTips)

    @Update
    suspend fun updateNutriCoachTips(nutriCoachTips: NutriCoachTips)

    @Delete
    suspend fun deleteNutriCoachTips(nutriCoachTips: NutriCoachTips)
} 