package com.example.tickp;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;




public class DataBaseHelper extends SQLiteOpenHelper {
    public static final String PATIENT_TABLE = "PATIENT_TABLE";
    public static final String COLUMN_PATIENT_NOM = "PATIENT_NOM";
    public static final String COLUMN_PATIENT_PRENOM = "PATIENT_Prenom";
    public static final String COLUMN_PATIENT_MATRICULE = "PATIENT_Matricule";
    public static final String COLUMN_PATIENT_SERVICE = "PATIENT_Service";
    public static final String COLUMN_PATIENT_TYPE_ANALYSE = "PATIENT_Type_Analyse"
            ;
    public DataBaseHelper(@Nullable Context context) {
        super(context, "patient.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String createTableStatment= "CREATE TABLE " + PATIENT_TABLE + " (" + COLUMN_PATIENT_MATRICULE + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_PATIENT_NOM + " TEXT, " + COLUMN_PATIENT_PRENOM + " TEXT, " + COLUMN_PATIENT_SERVICE + " TEXT, " + COLUMN_PATIENT_TYPE_ANALYSE + " TEXT )";
        db.execSQL(createTableStatment);



    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public boolean addOne(PatientModel patientmodel){

        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues cv=new ContentValues();
        ////cv.put(COLUMN_PATIENT_MATRICULE, PatientModel.getMatricule());
        cv.put(COLUMN_PATIENT_NOM, PatientModel.getNom());
        cv.put(COLUMN_PATIENT_PRENOM, PatientModel.getPrenom());
        cv.put(COLUMN_PATIENT_SERVICE, PatientModel.getService());
        cv.put(COLUMN_PATIENT_TYPE_ANALYSE, PatientModel.getType_Analyse());

        long insert = db.insert(PATIENT_TABLE, null, cv);
        if (insert==-1){
            return false;

        }
        else {
            return true;
        }

    }
}
