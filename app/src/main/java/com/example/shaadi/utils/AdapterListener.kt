package com.example.shaadi.utils

import com.example.shaadi.models.User


interface AdapterListener {

    fun onStatusChanged(user: User, postion: Int)
}