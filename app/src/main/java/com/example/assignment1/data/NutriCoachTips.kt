// AI ASSISTED: Some parts of this code have been developed with the assistance of AI
package com.example.assignment1.data

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "nutri_coach_tips",
    foreignKeys = [
        ForeignKey(
            entity = Patient::class,
            parentColumns = ["userId"],
            childColumns = ["patientId"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class NutriCoachTips(
    @PrimaryKey
    val patientId: String,
    val tipsString: String = ""
) {
    companion object {
        private const val DELIMITER = "|||"
    }

    val tips: List<String>
        get() = if (tipsString.isEmpty()) emptyList() else tipsString.split(DELIMITER)

    fun addTip(tip: String): NutriCoachTips {
        val currentTips = tips.toMutableList()
        currentTips.add(tip)
        return copy(tipsString = currentTips.joinToString(DELIMITER))
    }

    fun updateTips(newTips: List<String>): NutriCoachTips {
        return copy(tipsString = newTips.joinToString(DELIMITER))
    }
} 