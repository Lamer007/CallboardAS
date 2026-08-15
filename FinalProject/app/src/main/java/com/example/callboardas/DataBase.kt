package com.example.callboardas

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DataBase(context: Context): SQLiteOpenHelper(context, DATABASE_FILE_NAME, null, 1) {

    companion object{
        const val DATABASE_FILE_NAME = "callboard_database"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        var query = """CREATE TABLE IF NOT EXISTS ${UserModel.TABLE_NAME}(
        ${UserModel.COLUMN_USER_ID} INTEGER PRIMARY KEY AUTOINCREMENT,    
        ${UserModel.COLUMN_USER_NAME} TEXT,    
        ${UserModel.COLUMN_USER_EMAIL} TEXT,    
        ${UserModel.COLUMN_USER_PHONE} INTEGER,    
        ${UserModel.COLUMN_USER_PASSWORD} TEXT,    
        ${UserModel.COLUMN_USER_CURRENCY} TEXT)""".trimIndent()
        db?.execSQL(query)

        query = """CREATE TABLE IF NOT EXISTS ${CallModel.TABLE_NAME}(
        ${CallModel.COLUMN_CALL_ID} INTEGER PRIMARY KEY AUTOINCREMENT,    
        ${CallModel.COLUMN_CALL_NAME} TEXT,    
        ${CallModel.COLUMN_CALL_DESCRIPTION} TEXT,    
        ${CallModel.COLUMN_CALL_AUTHOR} TEXT,    
        ${CallModel.COLUMN_CALL_AUTHOR_ID} INTEGER,    
        ${CallModel.COLUMN_CALL_PHONE} INTEGER,    
        ${CallModel.COLUMN_CALL_PRICE} INTEGER,
        ${CallModel.COLUMN_CALL_CURRENCY} TEXT)""".trimIndent()
        db?.execSQL(query)

        query = """CREATE TABLE IF NOT EXISTS ${ServiceModel.TABLE_NAME}(
        ${ServiceModel.COLUMN_SERVICE_ID} INTEGER PRIMARY KEY AUTOINCREMENT,    
        ${ServiceModel.COLUMN_SERVICE_NAME} TEXT,    
        ${ServiceModel.COLUMN_SERVICE_DESCRIPTION} TEXT,    
        ${ServiceModel.COLUMN_SERVICE_AUTHOR} TEXT,    
        ${ServiceModel.COLUMN_SERVICE_AUTHOR_ID} INTEGER,    
        ${ServiceModel.COLUMN_SERVICE_PHONE} INTEGER,    
        ${ServiceModel.COLUMN_SERVICE_PRICE} INTEGER,
        ${ServiceModel.COLUMN_SERVICE_CURRENCY} TEXT);""".trimIndent()
        db?.execSQL(query)
    }

    override fun onUpgrade(
        db: SQLiteDatabase?,
        oldVersion: Int,
        newVersion: Int
    ) {
        TODO("Not yet implemented")
    }

}