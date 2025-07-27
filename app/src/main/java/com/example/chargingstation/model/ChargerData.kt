package com.example.chargingstation.model


data class ChargerData(
    var id : Int,
    var uuid: String,
    val charger : Int,
    val chargerMake : String,
    val chargerType : String,
    val chargerCapacity : String,
    val chargerCost : Long,
)
