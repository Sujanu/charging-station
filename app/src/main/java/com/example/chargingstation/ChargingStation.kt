package com.example.chargingstation

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import com.example.chargingstation.model.ChargerData
import com.example.chargingstation.model.ChargingStationData
import com.example.chargingstation.model.UserData

class ChargingStation(context: Context) : SQLiteOpenHelper(context, DATABASENAME, null, 2) {

    companion object {
        const val DATABASENAME = "chargingStation.db"
        const val CHARGING1 = "chargerStation1"
        const val USERTABLE = "userInfo"
        const val CHARGER = "charger"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(
            "CREATE TABLE $CHARGING1(" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "uuid TEXT NOT NULL," +
                    "owner TEXT NOT NULL," +
                    "station_name TEXT NOT NULL," +
                    "contact LONG not null," +
                    "location TEXT NOT NULL," +
                    "longitude REAL," +
                    "latitude REAL ," +
                    "elevation REAL," +
                    "dateTime TEXT NOT NULL," +

                    "cost_of_electricty_per_month INTEGER NOT NULL," +
                    "average_no_of_micro_bus_per_day INTEGER NOT NULL," +
                    "average_no_of_car_bus_per_day INTEGER NOT NULL," +
                    "any_challenges_or_issues_during_implementaion TEXT NOT NULL," +
                    "photo1 BLOB NOT NULL," +
                    "photo2 BLOB NOT NULL)"
        )

        db?.execSQL(
            "CREATE TABLE $USERTABLE(" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "username TEXT NOT NULL," +
                    "email TEXT NOT NULL," +
                    "phone LONG NOT NULL," +
                    "password TEXT NOT NULL)"
        )

        db?.execSQL(
            "CREATE TABLE $CHARGER(" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "uuid TEXT NOT NULL," +
                    "chargerCapacity TEXT NOT NULL," +
                    "charger TEXT NOT NULL," +
                    "chargerType TEXT NOT NULL," +
                    "chargerMake TEXT NOT NULL," +
                    "chargerCost LONG NOT NULL)"
        )
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {}

    fun insertUser(
        username: String,
        password: String,
        email: String,
        phone: Long

    ) {
        val db = this.writableDatabase
        val values = ContentValues().apply {
            put("username", username)
            put("password", password)
            put("phone", phone)
            put("email", email)
        }
        val result = db.insert(USERTABLE, null, values)

        if (result == -1L) {

            Log.d("DB", "Error inserting data into the database")
        } else {

            Log.d("DB", "Data inserted successfully")
        }
    }

    fun addCharger(chargerData: ChargerData): Long {
        val db = writableDatabase
        val contentValues = ContentValues().apply {

            put("charger", chargerData.charger)
            put("uuid", chargerData.uuid )
            put("chargerCapacity", chargerData.chargerCapacity)
            put("chargerMake", chargerData.chargerMake)
            put("chargerType", chargerData.chargerType)
            put("chargerCost", chargerData.chargerCost)
        }
        return db.insert(CHARGER, null, contentValues)
    }

    fun deleteChargerById(id: Int): Int {
        val db = writableDatabase
        return db.delete("charger_table", "id=?", arrayOf(id.toString()))
    }

    fun insertCharger1(
        uuid: String,
        owner: String,

        stationName: String,
        contact: Long,
        location: String,
        longitude: Double,
        latitude: Double,
        elevation: Double,
        dateTime: String,

        costOfElectrictyEerMonth: Int,
        averageNoOfMicroBusPerDay: Int,
        averageNoOfCarBusPerDay: Int,
        anyChallengesOrIssuesDuringImplementaion: String,
        photo1: ByteArray,
        photo2: ByteArray
    )
     {
        val db = this.writableDatabase
//        val imageByteArray: ByteArray = bitmapToByteArray()
        val values = ContentValues().apply {
            put("uuid", uuid)
            put("owner",owner)
            put("station_name",stationName)
            put("contact", contact)
            put("location", location)
            put("longitude", longitude)
            put("latitude", latitude)
            put("elevation", elevation)
            put("dateTime", dateTime)

            put("cost_of_electricty_per_month", costOfElectrictyEerMonth)
            put("average_no_of_micro_bus_per_day", averageNoOfMicroBusPerDay)
            put("average_no_of_car_bus_per_day", averageNoOfCarBusPerDay)
            put("any_challenges_or_issues_during_implementaion", anyChallengesOrIssuesDuringImplementaion)

            put("photo1", photo1)
            put("photo2", photo2)
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

                    electricityCostPerMonth = cursor.getInt(cursor.getColumnIndexOrThrow("cost_of_electricty_per_month")),
                    microBusPerDay = cursor.getInt(cursor.getColumnIndexOrThrow("average_no_of_micro_bus_per_day")),
                    carBusPerDay = cursor.getInt(cursor.getColumnIndexOrThrow("average_no_of_car_bus_per_day")),
                    challenges = cursor.getString(cursor.getColumnIndexOrThrow("any_challenges_or_issues_during_implementaion")),

                    photo1 = cursor.getBlob(cursor.getColumnIndexOrThrow("photo1")),
                    photo2 = cursor.getBlob(cursor.getColumnIndexOrThrow("photo2"))
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

    fun getChargerById(id: Int): ChargerData? {

        val db = readableDatabase
        val cursor = db.rawQuery("SELECT * FROM $CHARGER WHERE id = ?", arrayOf(id.toString()))

        return if (cursor.moveToFirst()) {
            val station = ChargerData(
                id = cursor.getInt(cursor.getColumnIndexOrThrow("id")),
                uuid = cursor.getString(cursor.getColumnIndexOrThrow("uuid")),
                charger = cursor.getInt(cursor.getColumnIndexOrThrow("charger")),
                chargerType = cursor.getString(cursor.getColumnIndexOrThrow("chargerType")),
                chargerMake = cursor.getString(cursor.getColumnIndexOrThrow("chargerMake")),
                chargerCost = cursor.getLong(cursor.getColumnIndexOrThrow("chargerCost")),
                chargerCapacity = cursor.getString(cursor.getColumnIndexOrThrow("chargerCapacity")),
            )
            cursor.close()
            station
        } else {
            cursor.close()
            null
        }
    }

    fun getChargersByUUID(uuid: String): List<ChargerData> {
        val chargerList = mutableListOf<ChargerData>()
        val db = readableDatabase
        val cursor = db.rawQuery("SELECT * FROM $CHARGER WHERE uuid = ?", arrayOf(uuid))

        if (cursor.moveToFirst()) {
            do {
                val charger = ChargerData(
                    id = cursor.getInt(cursor.getColumnIndexOrThrow("id")),
                    charger = cursor.getInt(cursor.getColumnIndexOrThrow("charger")),
                    chargerType = cursor.getString(cursor.getColumnIndexOrThrow("chargerType")),
                    chargerMake = cursor.getString(cursor.getColumnIndexOrThrow("chargerMake")),
                    chargerCost = cursor.getLong(cursor.getColumnIndexOrThrow("chargerCost")),
                    chargerCapacity = cursor.getString(cursor.getColumnIndexOrThrow("chargerCapacity")),
                    uuid = cursor.getString(cursor.getColumnIndexOrThrow("uuid"))
                )
                chargerList.add(charger)
            } while (cursor.moveToNext())
        }

        cursor.close()
        return chargerList
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

                electricityCostPerMonth = cursor.getInt(cursor.getColumnIndexOrThrow("cost_of_electricty_per_month")),
                microBusPerDay = cursor.getInt(cursor.getColumnIndexOrThrow("average_no_of_micro_bus_per_day")),
                carBusPerDay = cursor.getInt(cursor.getColumnIndexOrThrow("average_no_of_car_bus_per_day")),
                challenges = cursor.getString(cursor.getColumnIndexOrThrow("any_challenges_or_issues_during_implementaion")),

                photo1 = cursor.getBlob(cursor.getColumnIndexOrThrow("photo1")),
                photo2 = cursor.getBlob(cursor.getColumnIndexOrThrow("photo2"))
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

            put("cost_of_electricty_per_month", station.electricityCostPerMonth)
            put("average_no_of_micro_bus_per_day", station.microBusPerDay)
            put("average_no_of_car_bus_per_day", station.carBusPerDay)
            put("any_challenges_or_issues_during_implementaion", station.challenges)

            put("photo1", station.photo1)
            put("photo2", station.photo2)

        }

        val updated = db.update(CHARGING1, contentValues, "id = ?", arrayOf(station.id.toString()))
        db.close()
        return updated > 0
    }

    fun validateUser(username: String, password: String): UserData? {
        val db = this.readableDatabase
        val cursor = db.rawQuery(
            "SELECT * FROM $USERTABLE WHERE username = ? AND password = ?",
            arrayOf(username, password)
        )
        var userData: UserData? = null
        if (cursor.moveToFirst()) {
            userData = UserData(
                id = cursor.getInt(cursor.getColumnIndexOrThrow("id")),
                username = cursor.getString(cursor.getColumnIndexOrThrow("username")),
                password = cursor.getString(cursor.getColumnIndexOrThrow("password"))
            )
        }
        cursor.close()
        return userData
    }

    fun updateCharger(station: ChargerData): Boolean {
        val db = this.writableDatabase
        val contentValues = ContentValues().apply {

            put("chargerCapacity", station.chargerCapacity)
            put("chargerMake", station.chargerMake)
            put("chargerType", station.chargerType)
            put("charger", station.charger)
            put("chargerCost", station.chargerCost)
        }
        val updated = db.update(CHARGER, contentValues, "id = ?", arrayOf(station.id.toString()))
        db.close()
        return updated > 0
    }
}