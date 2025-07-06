package com.example.chargingstation.model

import androidx.compose.material3.SelectableChipElevation


data class Station (
    val id: Int,
    val owner_name:String,
    val station_name: String,
    val contact: Int ,
    val location : String,

    val longitude : Double,
    val latitude : Double,
    val elevation: Double
)