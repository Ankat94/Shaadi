package com.example.shaadi.network

import com.example.shaadi.models.Result
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

interface MyApi {

    @GET("api/?results=10")
    fun getData() : Call<Result>

    companion object{
        operator fun invoke(): MyApi {
            return Retrofit.Builder()
                .baseUrl("https://randomuser.me/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(MyApi::class.java)
        }
    }
}