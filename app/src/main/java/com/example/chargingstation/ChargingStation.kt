package com.example.chargingstation

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.health.connect.datatypes.ExerciseRoute.Location
import androidx.compose.material3.SelectableChipElevation
import com.example.chargingstation.activites.charging
import com.example.chargingstation.activites.station1
import org.w3c.dom.Text

class ChargingStation(context: Context) : SQLiteOpenHelper(context, DATABSENAME, null, 1) {

    companion object {
        const val DATABSENAME = "chargingStation.db"
        const val TABLE_NAME = "station"
        const val Charging_1 = "chargerStation1"
        const val Charging_2 = "chargerStation2"
        const val Charging_3 = "chargerStation3"
        const val Electric_Cost = "station_desc"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(
            "CREATE TABLE $TABLE_NAME(" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "owner TEXT NOT NULL," +
                    "station_name TEXT NOT NULL," +
                    "contact INTEGER not null," +
                    "location TEXT NOT NULL," +
                    "longitude DOUBLE NOT NULL," +
                    "latitude DOUBLE NOT NULL," +
                    "elevation DOUBLE NOT NULL)"
        );
        db?.execSQL(
            "CREATE TABLE $Charging_1(" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "charger_capacity_1 TEXT NOT NULL," +
                    "charger_no_1 TEXT NOT NULL," +
                    "charger_type_1 TEXT NOT NULL," +
                    "charger_make_1 TEXT NOT NULL," +
                    "approximate_cost_1 INTEGER NOT NULL)"
        );

        db?.execSQL(
            "CREATE TABLE $Charging_2(" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "charger_capacity_1 TEXT NOT NULL," +
                    "charger_no_2 TEXT NOT NULL," +
                    "charger_type_2 TEXT NOT NULL," +
                    "charger_make_2 TEXT NOT NULL," +
                    "approximate_cost_2 INEGER NOT NULL)"
        );

        db?.execSQL(
            "CREATE TABLE $Charging_3(" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "charger_capacity_3 TEXT NOT NULL," +
                    "charger_no_3 TEXT NOT NULL," +
                    "charger_type_3 TEXT NOT NULL," +
                    "charger_make_3 TEXT NOT NULL," +
                    "approximate_cost_3 INTEGER NOT NULL)"
        );

        db?.execSQL(
            "CREATE TABLE $Electric_Cost(" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "cost_of_electricty_per_month INTEGER NOT NULL," +
                    "average_no_of_micro_bus_per_day INTEGER NOT NULL," +
                    "average_no_of_car_bus_per_day INTEGER NOT NULL," +
                    "any_challenges_or_issues_during_implementaion TEXT NOT NULL," +
                    "photo_1 BLOB NOT NULL," +
                    "photo_2 BLOB NOT NULL)"
        );
    }


    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        db?.execSQL("DROP TABLE IF EXISTS $Charging_1")
        db?.execSQL("DROP TABLE IF EXISTS $Charging_2")
        db?.execSQL("DROP TABLE IF EXISTS $Charging_3")
        db?.execSQL("DROP TABLE IF EXISTS $Electric_Cost")

        onCreate(db)
    }

    fun insertChargingStation(
        owner: String,
        station_name: String,
        contact: Int,
        location: String,
        longitude: Double,
        latitude: Double,
        elevation: Double
    ) {
        val db = this.writableDatabase
        val values = ContentValues().apply{
            put("owner", owner)
            put("station_name",station_name)
            put("contact",contact)
            put("location",location)
            put("longitude",longitude)
            put("latitude", latitude)
            put("elevation",elevation)

        }

        val result= db.insert(TABLE_NAME,null,values)

        if (result == -1L) {

            println("Error inserting data into the database")
        } else {

            println("Data inserted successfully")
        }
        db.close()
    }

    fun insertCharger1(
        charger_capacity_1: String,
        charger_no_1: Int,
        charger_make_1: String,
        charger_type_1: String,
        charger_cost_1: Int
    ){
        val db = this.writableDatabase
        val values = ContentValues().apply {
            put("charger_capacity_1",charger_capacity_1)
            put("charger_make_1",charger_make_1)
            put("charger_no_1",charger_no_1.toString()) // stores as Text
            put("charger_type_1",charger_type_1)
            put("charger_cost_1",charger_cost_1)
        }
        val result = db.insert(Charging_1,null,values)

        if (result == -1L) {

            println("Error inserting data into the database")
        } else {

            println("Data inserted successfully")
        }
        db.close()

        }

    fun getStationName(): List<charging> {
        val stationList = mutableListOf<>()
        val db = this.readableDatabase
        val cursor = db.rawQuery("SELECT * FROM  $TABLE_NAME",null)

        if (cursor.moveToFirst()){
            do {
                val id = cursor.getInt(cursor.getColumnIndexOrThrow(station_name))
            }
        }
    }

}