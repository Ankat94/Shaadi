package com.example.shaadi.network

import android.content.Context
import android.net.ConnectivityManager

interface CheckInternet {

    companion object {
        fun Connection(context: Context): Boolean{
            val connectivityManager= context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val networkInfo=connectivityManager.activeNetworkInfo
            return  networkInfo!=null && networkInfo.isConnected
        }
    }
}