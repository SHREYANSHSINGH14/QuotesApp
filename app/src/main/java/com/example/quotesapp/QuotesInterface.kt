package com.example.quotesapp

import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import retrofit2.http.GET

const val BASE_URL = "https://type.fit/"
interface QuotesInterface{
    @GET("api/quotes")
    fun getQuotes():Call<List<Quotes>>
}

object QuotesService{
    val quotesInstance:QuotesInterface
    init {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        quotesInstance = retrofit.create(QuotesInterface::class.java)
    }
}