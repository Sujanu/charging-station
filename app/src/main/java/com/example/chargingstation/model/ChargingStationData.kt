package com.example.chargingstation.model

data class ChargingStationData(

    val id : Int,
    val uuid : String,
    val owner : String,
    val stationName : String,
    val contact : Long,
    val location : String,

    val longitude : Double,
    val latitude : Double,
    val elevation : Double,
    val dateTime : String,

    val electricityCostPerMonth : Int,
    val carBusPerDay : Int,
    val microBusPerDay :Int,
    val challenges : String,

    val photo1 : ByteArray,
    val photo2 : ByteArray
)
