package com.example.chargingstation

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import com.example.chargingstation.model.ChargingStationData

class ChargingStation(context: Context) : SQLiteOpenHelper(context, DATABSENAME, null, 2) {

    companion object {
        const val DATABSENAME = "chargingStation.db"
        const val CHARGING1 = "chargerStation1"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(
            "CREATE TABLE $CHARGING1(" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "uuid TEXT NOT NULL,"+
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
                    "chargerCapacity2 TEXT NOT NULL," +
                    "charger2 TEXT NOT NULL," +
                    "chargerType2 TEXT NOT NULL," +
                    "chargerMake2 TEXT NOT NULL," +
                    "chargerCost2 LONG NOT NULL," +
                    "chargerCapacity3 TEXT NOT NULL," +
                    "charger3 TEXT NOT NULL," +
                    "chargerType3 TEXT NOT NULL," +
                    "chargerMake3 TEXT NOT NULL," +
                    "chargerCost3 LONG NOT NULL," +
                    "cost_of_electricty_per_month INTEGER NOT NULL," +
                    "average_no_of_micro_bus_per_day INTEGER NOT NULL," +
                    "average_no_of_car_bus_per_day INTEGER NOT NULL," +
                    "any_challenges_or_issues_during_implementaion TEXT NOT NULL)"
        )
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {

    }

    fun insertCharger1(
        uuid : String,
        owner : String,

        stationName : String,
        contact : Long,
        location : String,
        longitude : Double,
        latitude : Double,
        elevation : Double,
        dateTime : String,

        chargerCapacity1 : String,
        charger1 : Long,
        chargerMake1 : String,
        chargerType1 : String,
        chargerCost1 : Long,

        chargerCapacity2 : String,
        charger2 : Long,
        chargerMake2 : String,
        chargerType2 : String,
        chargerCost2 : Long,

        chargerCapacity3 : String,
        charger3 : Long,
        chargerMake3 : String,
        chargerType3 : String,
        chargerCost3 : Long,

        costOfElectrictyEerMonth : Int,
        averageNoOfMicroBusPerDay : Int,
        averageNoOfCarBusPerDay : Int,
        anyChallengesOrIssuesDuringImplementaion : String
    ) {
        val db = this.writableDatabase
        val values = ContentValues().apply {
            put("uuid", uuid)
            put("owner", owner)
            put("station_name", stationName)
            put("contact", contact)
            put("location", location)
            put("longitude", longitude)
            put("latitude", latitude)
            put("elevation", elevation)
            put("dateTime", dateTime)
            put("chargerCapacity1", chargerCapacity1)
            put("chargerMake1", chargerMake1)
            put("charger1", charger1.toString()) // stores as Text
            put("chargerType1", chargerType1)
            put("chargerCost1", chargerCost1)
            put("chargerCapacity2", chargerCapacity2)
            put("chargerMake2", chargerMake2)
            put("charger2", charger2.toString()) // stores as Text
            put("chargerType2", chargerType2)
            put("chargerCost2", chargerCost2)
            put("chargerCapacity3", chargerCapacity3)
            put("chargerMake3", chargerMake3)
            put("charger3", charger3.toString()) // stores as Text
            put("chargerType3", chargerType3)
            put("chargerCost3", chargerCost3)

            put("cost_of_electricty_per_month", costOfElectrictyEerMonth )
            put("average_no_of_micro_bus_per_day", averageNoOfMicroBusPerDay)
            put("average_no_of_car_bus_per_day", averageNoOfCarBusPerDay)
            put("any_challenges_or_issues_during_implementaion", anyChallengesOrIssuesDuringImplementaion)
        }
        val result = db.insert(CHARGING1, null, values)

        if (result == -1L) {

            Log.d("DB", "Error inserting data into the database")
        } else {

            Log.d("DB", "Data inserted successfully")
        }
    }

    fun getAllChargingStations(): List<ChargingStationData> {
        val stationList = mutableListOf<ChargingStationData>()
        val db = this.readableDatabase
        val cursor = db.rawQuery("SELECT * FROM $CHARGING1", null)

        if (cursor.moveToFirst()) {
            do {
                val station = ChargingStationData(
                    id = cursor.getInt(cursor.getColumnIndexOrThrow("id")),
                    uuid = cursor.getString(cursor.getColumnIndexOrThrow("uuid")),
                    owner = cursor.getString(cursor.getColumnIndexOrThrow("owner")),
                    stationName = cursor.getString(cursor.getColumnIndexOrThrow("station_name")),
                    contact = cursor.getLong(cursor.getColumnIndexOrThrow("contact")),
                    location = cursor.getString(cursor.getColumnIndexOrThrow("location")),
                    longitude = cursor.getDouble(cursor.getColumnIndexOrThrow("longitude")),
                    latitude = cursor.getDouble(cursor.getColumnIndexOrThrow("latitude")),
                    elevation = cursor.getDouble(cursor.getColumnIndexOrThrow("elevation")),
                    dateTime = cursor.getString(cursor.getColumnIndexOrThrow("dateTime")),

                    charger1 = cursor.getString(cursor.getColumnIndexOrThrow("charger1")),
                    chargerType1 = cursor.getString(cursor.getColumnIndexOrThrow("chargerType1")),
                    chargerMake1 = cursor.getString(cursor.getColumnIndexOrThrow("chargerMake1")),
                    chargerCost1 = cursor.getLong(cursor.getColumnIndexOrThrow("chargerCost1")),
                    chargerCapacity1 = cursor.getString(cursor.getColumnIndexOrThrow("chargerCapacity1")),

                    charger2 = cursor.getString(cursor.getColumnIndexOrThrow("charger2")),
                    chargerType2 = cursor.getString(cursor.getColumnIndexOrThrow("chargerType2")),
                    chargerMake2 = cursor.getString(cursor.getColumnIndexOrThrow("chargerMake2")),
                    chargerCost2 = cursor.getLong(cursor.getColumnIndexOrThrow("chargerCost2")),
                    chargerCapacity2 = cursor.getString(cursor.getColumnIndexOrThrow("chargerCapacity2")),

                    charger3 = cursor.getString(cursor.getColumnIndexOrThrow("charger3")),
                    chargerType3 = cursor.getString(cursor.getColumnIndexOrThrow("chargerType3")),
                    chargerMake3 = cursor.getString(cursor.getColumnIndexOrThrow("chargerMake3")),
                    chargerCost3 = cursor.getLong(cursor.getColumnIndexOrThrow("chargerCost3")),
                    chargerCapacity3 = cursor.getString(cursor.getColumnIndexOrThrow("chargerCapacity3")),

                    electricityCostPerMonth = cursor.getInt(cursor.getColumnIndexOrThrow("cost_of_electricty_per_month")),
                    microBusPerDay = cursor.getInt(cursor.getColumnIndexOrThrow("average_no_of_micro_bus_per_day")),
                    carBusPerDay = cursor.getInt(cursor.getColumnIndexOrThrow("average_no_of_car_bus_per_day")),
                    challenges = cursor.getString(cursor.getColumnIndexOrThrow("any_challenges_or_issues_during_implementaion"))
                )
                stationList.add(station)
            } while (cursor.moveToNext())
        }

        cursor.close()
        db.close()
        return stationList
    }

    fun deleteTaskById(id: Int): Int {
        val db = this.writableDatabase
        val deleted = db.delete(CHARGING1, "id=?", arrayOf(id.toString()))
        db.close()
        return deleted
    }

    fun getStationById(id: Int): ChargingStationData? {
        val db = readableDatabase
        val cursor = db.rawQuery("SELECT * FROM $CHARGING1 WHERE id = ?", arrayOf(id.toString()))
        return if (cursor.moveToFirst()) {
            val station = ChargingStationData(

                id = cursor.getInt(cursor.getColumnIndexOrThrow("id")),
                uuid = cursor.getString(cursor.getColumnIndexOrThrow("uuid")),
                owner = cursor.getString(cursor.getColumnIndexOrThrow("owner")),
                stationName = cursor.getString(cursor.getColumnIndexOrThrow("station_name")),
                contact = cursor.getLong(cursor.getColumnIndexOrThrow("contact")),
                location = cursor.getString(cursor.getColumnIndexOrThrow("location")),
                longitude = cursor.getDouble(cursor.getColumnIndexOrThrow("longitude")),
                latitude = cursor.getDouble(cursor.getColumnIndexOrThrow("latitude")),
                elevation = cursor.getDouble(cursor.getColumnIndexOrThrow("elevation")),
                dateTime = cursor.getString(cursor.getColumnIndexOrThrow("dateTime")),

                charger1 = cursor.getString(cursor.getColumnIndexOrThrow("charger1")),
                chargerType1 = cursor.getString(cursor.getColumnIndexOrThrow("chargerType1")),
                chargerMake1 = cursor.getString(cursor.getColumnIndexOrThrow("chargerMake1")),
                chargerCost1 = cursor.getLong(cursor.getColumnIndexOrThrow("chargerCost1")),
                chargerCapacity1 = cursor.getString(cursor.getColumnIndexOrThrow("chargerCapacity1")),

                charger2 = cursor.getString(cursor.getColumnIndexOrThrow("charger2")),
                chargerType2 = cursor.getString(cursor.getColumnIndexOrThrow("chargerType2")),
                chargerMake2 = cursor.getString(cursor.getColumnIndexOrThrow("chargerMake2")),
                chargerCost2 = cursor.getLong(cursor.getColumnIndexOrThrow("chargerCost2")),
                chargerCapacity2 = cursor.getString(cursor.getColumnIndexOrThrow("chargerCapacity2")),

                charger3 = cursor.getString(cursor.getColumnIndexOrThrow("charger3")),
                chargerType3 = cursor.getString(cursor.getColumnIndexOrThrow("chargerType3")),
                chargerMake3 = cursor.getString(cursor.getColumnIndexOrThrow("chargerMake3")),
                chargerCost3 = cursor.getLong(cursor.getColumnIndexOrThrow("chargerCost3")),
                chargerCapacity3 = cursor.getString(cursor.getColumnIndexOrThrow("chargerCapacity3")),

                electricityCostPerMonth = cursor.getInt(cursor.getColumnIndexOrThrow("cost_of_electricty_per_month")),
                microBusPerDay = cursor.getInt(cursor.getColumnIndexOrThrow("average_no_of_micro_bus_per_day")),
                carBusPerDay = cursor.getInt(cursor.getColumnIndexOrThrow("average_no_of_car_bus_per_day")),
                challenges = cursor.getString(cursor.getColumnIndexOrThrow("any_challenges_or_issues_during_implementaion"))
            )
            cursor.close()
            station
        } else {
            cursor.close()
            null
        }
    }

    fun updateChargingStation(station: ChargingStationData): Boolean {
        val db = this.writableDatabase
        val contentValues = ContentValues().apply {
            put("owner", station.owner)
            put("uuid", station.uuid)
            put("station_name", station.stationName)
            put("contact", station.contact)
            put("location", station.location)
            put("longitude", station.longitude)
            put("latitude", station.latitude)
            put("elevation", station.elevation)
            put("dateTime", station.dateTime)

            put("chargerCapacity1", station.chargerCapacity1)
            put("chargerMake1", station.chargerMake1)
            put("chargerType1", station.chargerType1)
            put("charger1", station.charger1)
            put("chargerCost1", station.chargerCost1)

            put("chargerCapacity2", station.chargerCapacity2)
            put("chargerMake2", station.chargerMake2)
            put("chargerType2", station.chargerType2)
            put("charger2", station.charger2)
            put("chargerCost2", station.chargerCost2)

            put("chargerCapacity3", station.chargerCapacity3)
            put("chargerMake3", station.chargerMake3)
            put("chargerType3", station.chargerType3)
            put("charger3", station.charger3)
            put("chargerCost3", station.chargerCost3)

            put("cost_of_electricty_per_month", station.electricityCostPerMonth)
            put("average_no_of_micro_bus_per_day", station.microBusPerDay)
            put("average_no_of_car_bus_per_day", station.carBusPerDay)
            put("any_challenges_or_issues_during_implementaion", station.challenges)
        }

        val updated = db.update(CHARGING1, contentValues, "id = ?", arrayOf(station.id.toString()))
        db.close()
        return updated > 0
    }
}