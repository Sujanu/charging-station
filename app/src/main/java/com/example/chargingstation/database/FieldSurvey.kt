package np.com.softwel.suswa_nwash.database

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import np.com.softwel.suswa_nwash.model.FieldData

class FieldSurveyDbHelper(context: Context) : SQLiteOpenHelper(context, DATABASENAME, null, 1) {

    companion object {
        const val DATABASENAME = "fieldSurvey.db"
        const val FIELDSURVEYDATA = "fieldSurveyData"
    }

    override fun onCreate(db: SQLiteDatabase?) {

        db?.execSQL(
            "CREATE TABLE $FIELDSURVEYDATA(" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "lgName TEXT NOT NULL," +
                    "ward INT NOT NULL," +
                    "schemeCode TEXT NOT NULL," +
                    "survey_date TEXT NOT NULL," +
                    "benHH LONG NOT NULL," +
                    "benPop LONG NOT NULL," +
                    "structure_name TEXT NOT NULL," +
                    "issues TEXT NOT NULL," +
                    "solutions TEXT NOT NULL," +
                    "functional_status TEXT NOT NULL," +
                    "wusc_Registration_Renewal TEXT NOT NULL," +
                    "wusc_Completeness TEXT NOT NULL," +
                    "wusc_meeting_regularity TEXT NOT NULL," +
                    "vmw_appointment_and_mobilization TEXT NOT NULL," +
                    "wsp_plan_preparation_and_implementation TEXT NOT NULL," +
                    "om_procedure_implementation TEXT NOT NULL," +
                    "agm TEXT NOT NULL," +
                    "spare_part_management TEXT NOT NULL," +
                    "om_and_WSP_plan_annual_review TEXT NOT NULL," +
                    "account_management TEXT NOT NULL," +
                    "affiliation_with_a_cooperative TEXT NOT NULL," +
                    "active_OM_fund_and_regular_tariff_collection TEXT NOT NULL," +
                    "om_fund_status TEXT NOT NULL," +
                    "is_water_quantity_adequate TEXT NOT NULL," +
                    "is_water_accessible_within_15_min TEXT NOT NULL," +
                    "is_water_available_all_year_round TEXT NOT NULL," +
                    "does_water_quality_as_per_NDWQS TEXT NOT NULL," +
                    "yearlyIncomeAndExpenditureRatio TEXT NOT NULL ," +
                    "regular_SSC_reporting TEXT NOT NULL)"
        )
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {}


    fun getFiledSurveyById(id: Int): FieldData? {
        val db = readableDatabase
        val cursor =
            db.rawQuery("SELECT * FROM $FIELDSURVEYDATA WHERE id = ?", arrayOf(id.toString()))

        return if (cursor.moveToFirst()) {

            val field = FieldData(

                id = cursor.getInt(cursor.getColumnIndexOrThrow("id")),
                lgName = cursor.getString(cursor.getColumnIndexOrThrow("lgName")),
                ward = cursor.getInt(cursor.getColumnIndexOrThrow("ward")),
                scheme = cursor.getString(cursor.getColumnIndexOrThrow("schemeCode")),
                surveyDate = cursor.getString(cursor.getColumnIndexOrThrow("survey_date")),
                BenHH = cursor.getLong(cursor.getColumnIndexOrThrow("BenHH")),
                BenPop = cursor.getLong(cursor.getColumnIndexOrThrow("BenPop")),
                report = cursor.getString(cursor.getColumnIndexOrThrow("report")),
                structureName = cursor.getString(cursor.getColumnIndexOrThrow("structureName")),
                issues = cursor.getString(cursor.getColumnIndexOrThrow("issues")),
                solution = cursor.getString(cursor.getColumnIndexOrThrow("solution")),
                functionalStatus = cursor.getString(cursor.getColumnIndexOrThrow("functionalStatus")),
                registrationRenewalWUSC = cursor.getString(cursor.getColumnIndexOrThrow("registrationRenewalWUSC")),
                completenessWUSC = cursor.getString(cursor.getColumnIndexOrThrow("completenessWUSC")),
                regularityMeetingWUSC = cursor.getString(cursor.getColumnIndexOrThrow("regularityMeetingWUSC")),
                appointmentMobilization = cursor.getString(cursor.getColumnIndexOrThrow("appointmentMobilization")),
                planPreparationImplementation = cursor.getString(cursor.getColumnIndexOrThrow("planPreparationImplementation")),
                OMprodureImplementation = cursor.getString(cursor.getColumnIndexOrThrow("OMprodureImplementation")),
                AGM = cursor.getString(cursor.getColumnIndexOrThrow("AGM")),
                sparePartManagement = cursor.getString(cursor.getColumnIndexOrThrow("sparePartManagement")),
                annualReview = cursor.getString(cursor.getColumnIndexOrThrow("annualReview")),
                accountManagement = cursor.getString(cursor.getColumnIndexOrThrow("accountManagement")),
                affiliation = cursor.getString(cursor.getColumnIndexOrThrow("affiliation")),
                OMfundTraffic = cursor.getString(cursor.getColumnIndexOrThrow("OMfundTraffic")),
                OmfundStatus = cursor.getString(cursor.getColumnIndexOrThrow("OmfundStatus")),
                waterQuantity = cursor.getString(cursor.getColumnIndexOrThrow("waterQuantity")),
                waterAccessibility = cursor.getString(cursor.getColumnIndexOrThrow("waterAccessibility")),
                waterAvailability = cursor.getString(cursor.getColumnIndexOrThrow("waterAvailability")),
                waterQuality = cursor.getString(cursor.getColumnIndexOrThrow("waterQuality")),
                incomeExpenditureRatio = cursor.getString(cursor.getColumnIndexOrThrow("incomeExpenditureRatio")),
                SSCreporting = cursor.getString(cursor.getColumnIndexOrThrow("SSCreporting"))
            )

            cursor.close()
            field
        } else {
            cursor.close()
            null
        }
    }

    fun insertDataInTable(cv: ContentValues?, table_name: String): Boolean {
        val db = this.writableDatabase
        val value = db.insert(table_name, null, cv)
        db.close()
        return value > 0
    }

}