// AI ASSISTED: Some parts of this code have been developed with the assistance of AI
package com.example.assignment1.data

import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Query

data class GeminiPrompt(
    val contents: List<GeminiContent>
)

data class GeminiContent(
    val role: String,
    val parts: List<GeminiPart>
)

data class GeminiPart(
    val text: String
)

data class GeminiResponse(
    val candidates: List<Candidate>
)

data class Candidate(
    val content: Content
)

data class GeminiGeneratedContent(
    val parts: List<GeminiPart>
)

data class Content(
    val parts: List<Part>
)

data class Part(val text: String)

// Retrofit API interface
interface GeminiApi {
    @Headers("Content-Type: application/json")
    @POST("models/gemini-1.5-flash:generateContent")
    suspend fun generateMessage(
        @Query("key") apiKey: String,
        @Body prompt: GeminiPrompt
    ): GeminiResponse
}
