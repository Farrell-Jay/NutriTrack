// AI ASSISTED: Some parts of this code have been developed with the assistance of AI
package com.example.assignment1.data

import retrofit2.http.GET
import retrofit2.http.Path

interface FruityViceApi {
    @GET("api/fruit/{fruitName}")
    suspend fun getFruitDetails(@Path("fruitName") fruitName: String): FruitResponse
}
