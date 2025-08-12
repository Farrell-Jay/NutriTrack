// AI ASSISTED: Some parts of this code have been developed with the assistance of AI
package com.example.assignment1.data

import kotlinx.coroutines.flow.Flow

class PatientRepository(
    private val patientDao: PatientDao, 
    private val foodIntakeDao: FoodIntakeDao,
    private val nutriCoachTipsDao: NutriCoachTipsDao
) {
    
    val allPatients: Flow<List<Patient>> = patientDao.getAllPatients()

    suspend fun getPatientById(userId: String): Patient? {
        return patientDao.getPatientById(userId)
    }

    suspend fun insertPatient(patient: Patient) {
        patientDao.insertPatient(patient)
    }

    suspend fun insertAllPatients(patients: List<Patient>) {
        patientDao.insertAllPatients(patients)
    }

    suspend fun updatePatient(patient: Patient) {
        patientDao.updatePatient(patient)
    }

    suspend fun authenticatePatient(userId: String, password: String): Patient? {
        return patientDao.authenticatePatient(userId, password)
    }

    suspend fun getPatientCount(): Float {
        return patientDao.getPatientCount()
    }

    fun getAllFoodIntakes(): Flow<List<FoodIntake>> {
        return foodIntakeDao.getAllFoodIntakes()
    }

    suspend fun getFoodIntakeByID(userId: String): FoodIntake? {
        return foodIntakeDao.getFoodIntakeByID(userId)
    }

    suspend fun insertFoodIntake(foodIntake: FoodIntake) {
        foodIntakeDao.insertFoodIntake(foodIntake)
    }

    suspend fun updateFoodIntake(foodIntake: FoodIntake) {
        foodIntakeDao.updateFoodIntake(foodIntake)
    }

    suspend fun deleteFoodIntake(foodIntake: FoodIntake) {
        foodIntakeDao.deleteFoodIntake(foodIntake)
    }

    // NutriCoachTips operations
    fun getAllNutriCoachTips(): Flow<List<NutriCoachTips>> {
        return nutriCoachTipsDao.getAllNutriCoachTips()
    }

    suspend fun getNutriCoachTipsByID(patientId: String): NutriCoachTips? {
        return nutriCoachTipsDao.getNutriCoachTipsByID(patientId)
    }

    suspend fun insertNutriCoachTips(nutriCoachTips: NutriCoachTips) {
        nutriCoachTipsDao.insertNutriCoachTips(nutriCoachTips)
    }

    suspend fun updateNutriCoachTips(nutriCoachTips: NutriCoachTips) {
        nutriCoachTipsDao.updateNutriCoachTips(nutriCoachTips)
    }

    suspend fun deleteNutriCoachTips(nutriCoachTips: NutriCoachTips) {
        nutriCoachTipsDao.deleteNutriCoachTips(nutriCoachTips)
    }
} 