package com.example.chargingstation.model

data class ChargerData(
    var id : Int,
    val charger : Int,
    val chargerMake : String,
    val chargerType : String,
    val chargerCapacity : String,
    val chargerCost : Long,
)
