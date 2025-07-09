package com.example.chargingstation

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import androidx.compose.runtime.currentRecomposeScope
import java.sql.Blob
import java.time.chrono.ChronoLocalDateTime


class ChargingStation(context: Context) : SQLiteOpenHelper(context, DATABSENAME, null, 2) {

    companion object {
        const val DATABSENAME = "chargingStation.db"
        const val STATION_INFORMATION = "stationInformation"
        const val CHARGING_1 = "chargerStation1"
        const val CHARGING_2 = "chargerStation2"
        const val CHARGING_3 = "chargerStation3"
        const val STATION_DESCRIPTION = "stationDescription"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(
            "CREATE TABLE $STATION_INFORMATION(" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "owner TEXT NOT NULL," +
                    "station_name TEXT NOT NULL," +
                    "contact LONG not null," +
                    "location TEXT NOT NULL," +
                    "longitude REAL," +
                    "latitude REAL ," +
                    "elevation REAL," +
                    "dateTime TEXT NOT NULL," +
                    "chargerCapacity1 TEXT NOT NULL," +
                    "charger1 TEXT NOT NULL," +
                    "chargerType1 TEXT NOT NULL," +
                    "chargerMake1 TEXT NOT NULL," +
                    "chargerCost1 LONG NOT NULL," +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "chargerCapacity2 TEXT NOT NULL," +
                    "charger2 TEXT NOT NULL," +
                    "chargerType2 TEXT NOT NULL," +
                    "chargerMake2 TEXT NOT NULL," +
                    "chargerCost2 LONG NOT NULL,"+
                    "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "chargerCapacity3 TEXT NOT NULL," +
                    "charger3 TEXT NOT NULL," +
                    "chargerType3 TEXT NOT NULL," +
                    "chargerMake3 TEXT NOT NULL," +
                    "chargerCost3 LONG NOT NULL," +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "cost_of_electricty_per_month INTEGER NOT NULL," +
                    "average_no_of_micro_bus_per_day INTEGER NOT NULL," +
                    "average_no_of_car_bus_per_day INTEGER NOT NULL," +
                    "any_challenges_or_issues_during_implementaion TEXT NOT NULL," +
                    "photo_1 BLOB NOT NULL," +
                    "photo_2 BLOB NOT NULL)"
        )
    }
    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {

    }


    /////////// ****** Station Info ****** ///////////

    fun insertChargingStation(
        owner: String,
        stationName: String,
        contact: Long,
        location: String,
        longitude: Double,
        latitude: Double,
        elevation: Double,
        dateTime: String
    ) {
        val db = this.writableDatabase
        val values = ContentValues().apply {
            put("owner", owner)
            put("station_name", stationName)
            put("contact", contact)
            put("location", location)
            put("longitude", longitude)
            put("latitude", latitude)
            put("elevation", elevation)
            put("dateTime", dateTime)
        }

        val result = db.insert(STATION_INFORMATION, null, values)

        if (result == -1L) {

            Log.d("DB","Error inserting data into the database")
        } else {

            Log.d("DB","Data inserted successfully")
        }
        db.close()
    }

     // Charging Staion 1 //

    fun insertCharger1(
        chargerCapacity1: String,
        charger1: Long,
        chargerMake1: String,
        chargerType1: String,
        chargerCost1: Long
    ) {
        val db = this.writableDatabase
        val values = ContentValues().apply {
            put("chargerCapacity1", chargerCapacity1)
            put("chargerMake1", chargerMake1)
            put("charger1", charger1.toString()) // stores as Text
            put("chargerType1", chargerType1)
            put("chargerCost1", chargerCost1)
        }
        val result = db.insert(CHARGING_1, null, values)

        if (result == -1L) {

            Log.d("DB","Error inserting data into the database")
        } else {

            Log.d("DB","Data inserted successfully")
        }
    }




    // Charging Staion 2 //

    fun insertCharger2(
        chargerCapacity2: String,
        charger2: Long,
        chargerMake2: String,
        chargerType2: String,
        chargerCost2: Long
    ) {
        val db = this.writableDatabase
        val values = ContentValues().apply {
            put("chargerCapacity2", chargerCapacity2)
            put("chargerMake2", chargerMake2)
            put("charger2", charger2.toString()) // stores as Text
            put("chargerType2", chargerType2)
            put("chargerCost2", chargerCost2)
        }
        val result = db.insert(CHARGING_2,null,values)

        if (result == -1L) {

            Log.d("DB","Error inserting data into the database")
        } else {

            Log.d("DB","Data inserted successfully")
        }
    }

    // Charging Staion 3 //

    fun insertCharger3(
        chargerCapacity3: String,
        charger3: Long,
        chargerMake3: String,
        chargerType3: String,
        chargerCost3: Long
    ) {
        val db = this.writableDatabase
        val values = ContentValues().apply {
            put("chargerCapacity3", chargerCapacity3)
            put("chargerMake3", chargerMake3)
            put("charger3", charger3.toString()) // stores as Text
            put("chargerType3", chargerType3)
            put("chargerCost3", chargerCost3)
        }
        val result = db.insert(CHARGING_3, null, values)

        if (result == -1L) {

            Log.d("DB","Error inserting data into the database")
        } else {

            Log.d("DB","Data inserted successfully")
        }
    }

    fun insertDesc
    (
        cost_of_electricty_per_month: Int,
        average_no_of_micro_bus_per_day : Int,
        average_no_of_car_bus_per_day : Int,
        any_challenges_or_issues_during_implementaion : String
//        photo_1 : Blob,
//        photo_2 : Blob
         ){
        val db = this.writableDatabase
        val values = ContentValues().apply {
            put("cost_of_electricty_per_month" ,cost_of_electricty_per_month)
            put("average_no_of_micro_bus_per_day", average_no_of_micro_bus_per_day)
            put("average_no_of_car_bus_per_day",average_no_of_car_bus_per_day)
            put("any_challenges_or_issues_during_implementaion",any_challenges_or_issues_during_implementaion)
            put("photo_1", ByteArray(0))  // or real image data
            put("photo_2", ByteArray(0))


        }

        val result = db.insert(STATION_DESCRIPTION, null, values)

        if (result == -1L) {

            Log.d("DB","Error inserting data into the database")
        } else {

            Log.d("DB","Data inserted successfully")
        }
    }

//    fun getAll(): List <chargerStation2>{
//
//        val datalist  = mutableListOf<chargerStation2>()
//        val db = this.readableDatabase
//        val cursor  = db.rawQuery("SELECT * FROM $CHARGING_2", null)
//        if(cursor.moveToFirst())
//        {
//            do{
//                val id = cursor.getInt(cursor.getColumnIndexOrThrow(COL_ID))
//            }
//        }
//
//    }

}