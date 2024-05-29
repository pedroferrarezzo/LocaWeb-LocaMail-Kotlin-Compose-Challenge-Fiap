package br.com.fiap.locawebmailapp.service

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class GeminiFactory {
    private val URL = "https://generativelanguage.googleapis.com/"
// APENAS PARA DESENVOLVIMENTO (LOG INTERCEPTOR OKHTTP)
//    val intercepter = HttpLoggingInterceptor().apply {
//        this.level = HttpLoggingInterceptor.Level.BODY
//    }
//    val client = OkHttpClient.Builder().apply {
//        this.addInterceptor(intercepter)
//    }.build()


    private val geminiFactory = Retrofit.Builder()
        .baseUrl(URL)
//        .client(client)
        .addConverterFactory(
            GsonConverterFactory.create()
        )
        .build()

    fun getGeminiFactory(): GeminiService {
        return geminiFactory.create(GeminiService::class.java)
    }
}