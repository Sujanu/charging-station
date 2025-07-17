package com.example.chargingstation.model

import android.net.Uri
import java.sql.Blob

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

    val charger1 : String,
    val chargerMake1 : String,
    val chargerType1 : String,
    val chargerCapacity1 : String,
    val chargerCost1 : Long,

    val charger2 : String,
    val chargerMake2 : String,
    val chargerType2 : String,
    val chargerCapacity2 : String,
    val chargerCost2 : Long,

    val charger3 : String,
    val chargerMake3 : String,
    val chargerType3 : String,
    val chargerCapacity3 : String,
    val chargerCost3 : Long,

    val electricityCostPerMonth : Int,
    val carBusPerDay : Int,
    val microBusPerDay :Int,
    val challenges : String,

    val photo1 : ByteArray,
    val photo2 : ByteArray,

)