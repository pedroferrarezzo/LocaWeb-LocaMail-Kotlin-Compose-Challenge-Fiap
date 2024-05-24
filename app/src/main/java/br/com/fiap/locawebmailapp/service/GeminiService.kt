package br.com.fiap.locawebmailapp.service

import br.com.fiap.locawebmailapp.model.ai.GeminiRequest
import br.com.fiap.locawebmailapp.model.ai.GeminiResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Query

interface GeminiService {

    @POST("v1beta/models/gemini-pro:generateContent?")
    fun getResponseFromGemini(@Query("key") key: String, @Body request: GeminiRequest): Call<GeminiResponse>

}