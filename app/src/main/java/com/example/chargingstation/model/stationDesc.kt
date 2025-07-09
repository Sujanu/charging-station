package com.example.chargingstation.model

data class stationDesc(
    val id : Int,
    val owner_name:String,
    val station_name: String,
    val contact: Int ,
    val location : String,

    val longitude : Double,
    val latitude : Double,
    val elevation: Double,

    val charger_no_3:Int,
    val chager_capacity_3 : String,
    val charger_type_3: String,
    val charger_make_3:String,
    val approximate_cost_3: Int,

    val charger_no_2:Int,
    val chager_capacity_2 : String,
    val charger_type_2: String,
    val charger_make_2:String,
    val approximate_cost_2: Int,

    val chager_capacity_1 : String,
    val approximate_cost_1: Int,
    val charger_make_1:String,
    val charger_type_1: String,
    val charger_no_1:Int

)
