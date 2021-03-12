package com.example.shaadi.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity
data class User(

    @PrimaryKey(autoGenerate = true)
    var id: Long = 0,
    var gender: String = "",
    var title: String = "",
    var first: String = "",
    var last: String = "",
    var city: String = "",
    var state: String = "",
    var country: String = "",
    var large: String = "",
    var medium: String = "",
    var thumbnail: String = "",
    var date: Date,
    var age: Int = 0,
    var status: Int = 0
)
