package com.example.shaadi.models

data class Response(
    var gender: String = "",
    var name: Name,
    var location: Location,
    var picture: Picture,
    var dob: DOB
    )
