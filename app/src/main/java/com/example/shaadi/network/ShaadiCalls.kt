package com.example.shaadi.network

import com.example.shaadi.models.Result
import retrofit2.Call
import retrofit2.http.GET

interface ShaadiCalls {

    @GET("api/?results=10")
    fun getData() : Call<Result>
}