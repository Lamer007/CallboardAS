package com.example.callboardas

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
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
        ${CallModel.COLUMN_CALL_ADDRESS} TEXT,    
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
        ${ServiceModel.COLUMN_SERVICE_ADDRESS} TEXT,    
        ${ServiceModel.COLUMN_SERVICE_AUTHOR} TEXT,    
        ${ServiceModel.COLUMN_SERVICE_AUTHOR_ID} INTEGER,    
        ${ServiceModel.COLUMN_SERVICE_PHONE} INTEGER,    
        ${ServiceModel.COLUMN_SERVICE_PRICE} INTEGER,
        ${ServiceModel.COLUMN_SERVICE_CURRENCY} TEXT);""".trimIndent()
        db?.execSQL(query)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        val query = "DROP TABLE IF EXISTS ${UserModel.TABLE_NAME}"
        db?.execSQL(query)
        onCreate(db)
    }

    fun addUser(name: String, email: String, phone: Long, password: String, currency: String) {
        val db = writableDatabase
        val cv = ContentValues()
        cv.put(UserModel.COLUMN_USER_NAME, name)
        cv.put(UserModel.COLUMN_USER_EMAIL, email)
        cv.put(UserModel.COLUMN_USER_PHONE, phone)
        cv.put(UserModel.COLUMN_USER_PASSWORD, password)
        cv.put(UserModel.COLUMN_USER_CURRENCY, currency)
        db.insert(UserModel.TABLE_NAME, null,cv)
    }

    fun addCall(name: String, description: String, address: String, author: String, authorId: Int,
                phone: Long, price: Int, currency: String) {
        val db = writableDatabase
        val cv = ContentValues()
        cv.put(CallModel.COLUMN_CALL_NAME, name)
        cv.put(CallModel.COLUMN_CALL_DESCRIPTION, description)
        cv.put(CallModel.COLUMN_CALL_ADDRESS, address)
        cv.put(CallModel.COLUMN_CALL_AUTHOR, author)
        cv.put(CallModel.COLUMN_CALL_AUTHOR_ID, authorId)
        cv.put(CallModel.COLUMN_CALL_PHONE, phone)
        cv.put(CallModel.COLUMN_CALL_PRICE, price)
        cv.put(CallModel.COLUMN_CALL_CURRENCY, currency)
        db.insert(CallModel.TABLE_NAME, null,cv)
    }

    fun addService(name: String, description: String, address: String, author: String, authorId: Int,
                   phone: Long, price: Int, currency: String) {
        val db = writableDatabase
        val cv = ContentValues()
        cv.put(ServiceModel.COLUMN_SERVICE_NAME, name)
        cv.put(ServiceModel.COLUMN_SERVICE_DESCRIPTION, description)
        cv.put(ServiceModel.COLUMN_SERVICE_ADDRESS, address)
        cv.put(ServiceModel.COLUMN_SERVICE_AUTHOR, author)
        cv.put(ServiceModel.COLUMN_SERVICE_AUTHOR_ID, authorId)
        cv.put(ServiceModel.COLUMN_SERVICE_PHONE, phone)
        cv.put(ServiceModel.COLUMN_SERVICE_PRICE, price)
        cv.put(ServiceModel.COLUMN_SERVICE_CURRENCY, currency)
        db.insert(ServiceModel.TABLE_NAME, null,cv)
    }

    fun getCallById(callId: Int): CallModel?{
        val db = readableDatabase
        val query = """SELECT * FROM ${CallModel.TABLE_NAME} WHERE ${CallModel.COLUMN_CALL_ID} = ?""".trimIndent()
        val cursor: Cursor = db.rawQuery(query, arrayOf(callId.toString()))

        if(cursor.moveToFirst()) {
            val name = cursor.getString(cursor.getColumnIndexOrThrow(CallModel.COLUMN_CALL_NAME))
            val description = cursor.getString(cursor.getColumnIndexOrThrow(CallModel.COLUMN_CALL_DESCRIPTION))
            val address = cursor.getString(cursor.getColumnIndexOrThrow(CallModel.COLUMN_CALL_ADDRESS))
            val author = cursor.getString(cursor.getColumnIndexOrThrow(CallModel.COLUMN_CALL_AUTHOR))
            val authorId = cursor.getInt(cursor.getColumnIndexOrThrow(CallModel.COLUMN_CALL_AUTHOR_ID))
            val phone = cursor.getLong(cursor.getColumnIndexOrThrow(CallModel.COLUMN_CALL_PHONE))
            val price = cursor.getInt(cursor.getColumnIndexOrThrow(CallModel.COLUMN_CALL_PRICE))
            val currency = cursor.getString(cursor.getColumnIndexOrThrow(CallModel.COLUMN_CALL_CURRENCY))
            return CallModel(callId, name, description, address, author, authorId, phone, price, currency)
        } else return null
    }

    fun getServiceById(serviceId: Int): ServiceModel?{
        val db = readableDatabase
        val query = """SELECT * FROM ${ServiceModel.TABLE_NAME} WHERE ${ServiceModel.COLUMN_SERVICE_ID} = ?""".trimIndent()
        val cursor: Cursor = db.rawQuery(query, arrayOf(serviceId.toString()))

        if(cursor.moveToFirst()) {
            val name = cursor.getString(cursor.getColumnIndexOrThrow(ServiceModel.COLUMN_SERVICE_NAME))
            val description = cursor.getString(cursor.getColumnIndexOrThrow(ServiceModel.COLUMN_SERVICE_DESCRIPTION))
            val address = cursor.getString(cursor.getColumnIndexOrThrow(ServiceModel.COLUMN_SERVICE_ADDRESS))
            val author = cursor.getString(cursor.getColumnIndexOrThrow(ServiceModel.COLUMN_SERVICE_AUTHOR))
            val authorId = cursor.getInt(cursor.getColumnIndexOrThrow(ServiceModel.COLUMN_SERVICE_AUTHOR_ID))
            val phone = cursor.getLong(cursor.getColumnIndexOrThrow(ServiceModel.COLUMN_SERVICE_PHONE))
            val price = cursor.getInt(cursor.getColumnIndexOrThrow(ServiceModel.COLUMN_SERVICE_PRICE))
            val currency = cursor.getString(cursor.getColumnIndexOrThrow(ServiceModel.COLUMN_SERVICE_CURRENCY))
            return ServiceModel(serviceId, name, description, address, author, authorId, phone, price, currency)
        } else return null
    }

    fun getUserById(userId: Int): UserModel?{
        val db = readableDatabase
        val query = """SELECT * FROM ${UserModel.TABLE_NAME} WHERE ${UserModel.COLUMN_USER_ID} = ?""".trimIndent()
        val cursor: Cursor = db.rawQuery(query, arrayOf(userId.toString()))

        if(cursor.moveToFirst()) {
            val name = cursor.getString(cursor.getColumnIndexOrThrow(UserModel.COLUMN_USER_NAME))
            val email = cursor.getString(cursor.getColumnIndexOrThrow(UserModel.COLUMN_USER_EMAIL))
            val phone = cursor.getLong(cursor.getColumnIndexOrThrow(UserModel.COLUMN_USER_PHONE))
            val password = cursor.getString(cursor.getColumnIndexOrThrow(UserModel.COLUMN_USER_PASSWORD))
            val currency = cursor.getString(cursor.getColumnIndexOrThrow(UserModel.COLUMN_USER_CURRENCY))
            return UserModel(userId, name, email, phone, password, currency)
        } else return null
    }

    fun getUserIdByEmail(email: String): Int{
        val db = readableDatabase
        val query = """SELECT * FROM ${UserModel.TABLE_NAME} WHERE ${UserModel.COLUMN_USER_EMAIL} = ?""".trimIndent()
        val cursor: Cursor = db.rawQuery(query, arrayOf(email))
        return if(cursor.moveToFirst()) { cursor.getInt(cursor.getColumnIndexOrThrow(UserModel.COLUMN_USER_ID)) } else return -1
    }

    fun getAllCalls(): List<CallModel> {
        val db = readableDatabase
        val query = """SELECT * FROM ${CallModel.TABLE_NAME}""".trimIndent()
        val cursor: Cursor = db.rawQuery(query, null)
        val callList = mutableListOf<CallModel>()

        while (cursor.moveToNext()) {
            val callId = cursor.getInt(cursor.getColumnIndexOrThrow(CallModel.COLUMN_CALL_ID))
            val name = cursor.getString(cursor.getColumnIndexOrThrow(CallModel.COLUMN_CALL_NAME))
            val description = cursor.getString(cursor.getColumnIndexOrThrow(CallModel.COLUMN_CALL_DESCRIPTION))
            val address = cursor.getString(cursor.getColumnIndexOrThrow(CallModel.COLUMN_CALL_ADDRESS))
            val author = cursor.getString(cursor.getColumnIndexOrThrow(CallModel.COLUMN_CALL_AUTHOR))
            val authorId = cursor.getInt(cursor.getColumnIndexOrThrow(CallModel.COLUMN_CALL_AUTHOR_ID))
            val phone = cursor.getLong(cursor.getColumnIndexOrThrow(CallModel.COLUMN_CALL_PHONE))
            val price = cursor.getInt(cursor.getColumnIndexOrThrow(CallModel.COLUMN_CALL_PRICE))
            val currency = cursor.getString(cursor.getColumnIndexOrThrow(CallModel.COLUMN_CALL_CURRENCY))
            callList.add(CallModel(callId, name, description, address, author, authorId, phone, price, currency))
        }

        cursor.close()
        return callList
    }

    fun getAllServices(): List<ServiceModel> {
        val db = readableDatabase
        val query = """SELECT * FROM ${ServiceModel.TABLE_NAME}""".trimIndent()
        val cursor: Cursor = db.rawQuery(query, null)
        val callList = mutableListOf<ServiceModel>()

        while (cursor.moveToNext()) {
            val serviceId = cursor.getInt(cursor.getColumnIndexOrThrow(ServiceModel.COLUMN_SERVICE_ID))
            val name = cursor.getString(cursor.getColumnIndexOrThrow(ServiceModel.COLUMN_SERVICE_NAME))
            val description = cursor.getString(cursor.getColumnIndexOrThrow(ServiceModel.COLUMN_SERVICE_DESCRIPTION))
            val address = cursor.getString(cursor.getColumnIndexOrThrow(ServiceModel.COLUMN_SERVICE_ADDRESS))
            val author = cursor.getString(cursor.getColumnIndexOrThrow(ServiceModel.COLUMN_SERVICE_AUTHOR))
            val authorId = cursor.getInt(cursor.getColumnIndexOrThrow(ServiceModel.COLUMN_SERVICE_AUTHOR_ID))
            val phone = cursor.getLong(cursor.getColumnIndexOrThrow(ServiceModel.COLUMN_SERVICE_PHONE))
            val price = cursor.getInt(cursor.getColumnIndexOrThrow(ServiceModel.COLUMN_SERVICE_PRICE))
            val currency = cursor.getString(cursor.getColumnIndexOrThrow(ServiceModel.COLUMN_SERVICE_CURRENCY))
            callList.add(ServiceModel(serviceId, name, description, address, author, authorId, phone, price, currency))
        }

        cursor.close()
        return callList
    }

    fun getAllCallsByAuthor(authorId: Int): List<Int> {
        val db = readableDatabase
        val query = """SELECT * FROM ${CallModel.TABLE_NAME} WHERE ${CallModel.COLUMN_CALL_AUTHOR_ID} = ?""".trimIndent()
        val cursor: Cursor = db.rawQuery(query, arrayOf(authorId.toString()))
        val callIdList = mutableListOf<Int>()

        while (cursor.moveToNext()) {
            val callId = cursor.getInt(cursor.getColumnIndexOrThrow(CallModel.COLUMN_CALL_ID))
            callIdList.add(callId)
        }

        cursor.close()
        return callIdList
    }

    fun getAllServicesByAuthor(authorId: Int): List<Int> {
        val db = readableDatabase
        val query = """SELECT * FROM ${ServiceModel.TABLE_NAME} WHERE ${ServiceModel.COLUMN_SERVICE_AUTHOR_ID} = ?""".trimIndent()
        val cursor: Cursor = db.rawQuery(query, arrayOf(authorId.toString()))
        val serviceIdList = mutableListOf<Int>()

        while (cursor.moveToNext()) {
            val serviceId = cursor.getInt(cursor.getColumnIndexOrThrow(ServiceModel.COLUMN_SERVICE_ID))
            serviceIdList.add(serviceId)
        }

        cursor.close()
        return serviceIdList
    }

    fun editCall(callId: Int, name: String, description: String, address: String, author: String, authorId: Int,
                 phone: Long, price: Int, currency: String) {
        val db = writableDatabase
        val cv = ContentValues()
        cv.put(CallModel.COLUMN_CALL_NAME, name)
        cv.put(CallModel.COLUMN_CALL_DESCRIPTION, description)
        cv.put(CallModel.COLUMN_CALL_ADDRESS, address)
        cv.put(CallModel.COLUMN_CALL_AUTHOR, author)
        cv.put(CallModel.COLUMN_CALL_AUTHOR_ID, authorId)
        cv.put(CallModel.COLUMN_CALL_PHONE, phone)
        cv.put(CallModel.COLUMN_CALL_PRICE, price)
        cv.put(CallModel.COLUMN_CALL_CURRENCY, currency)
        db.update(CallModel.TABLE_NAME, cv, "${CallModel.COLUMN_CALL_ID} = ?", arrayOf(callId.toString()))
    }

    fun editService(serviceId: Int, name: String, description: String, address: String, author: String, authorId: Int,
                    phone: Long, price: Int, currency: String) {
        val db = writableDatabase
        val cv = ContentValues()
        cv.put(ServiceModel.COLUMN_SERVICE_NAME, name)
        cv.put(ServiceModel.COLUMN_SERVICE_DESCRIPTION, description)
        cv.put(ServiceModel.COLUMN_SERVICE_ADDRESS, address)
        cv.put(ServiceModel.COLUMN_SERVICE_AUTHOR, author)
        cv.put(ServiceModel.COLUMN_SERVICE_AUTHOR_ID, authorId)
        cv.put(ServiceModel.COLUMN_SERVICE_PHONE, phone)
        cv.put(ServiceModel.COLUMN_SERVICE_PRICE, price)
        cv.put(ServiceModel.COLUMN_SERVICE_CURRENCY, currency)
        db.update(ServiceModel.TABLE_NAME, cv, "${ServiceModel.COLUMN_SERVICE_ID} = ?", arrayOf(serviceId.toString()))
    }

    fun editUser(userId: Int, name: String, email: String, phone: Long, password: String, currency: String) {
        val db = writableDatabase
        val cv = ContentValues()
        cv.put(UserModel.COLUMN_USER_NAME, name)
        cv.put(UserModel.COLUMN_USER_EMAIL, email)
        cv.put(UserModel.COLUMN_USER_PHONE, phone)
        cv.put(UserModel.COLUMN_USER_PASSWORD, password)
        cv.put(UserModel.COLUMN_USER_CURRENCY, currency)

        val callIdList = getAllCallsByAuthor(userId)

        for (callId in callIdList) {
            val call: CallModel? = getCallById(callId)
            editCall(call!!.callId, call.name, call.description, call.address, name, call.authorId, phone, call.price, call.currency)
        }

        val serviceIdList = getAllServicesByAuthor(userId)

        for (serviceId in serviceIdList) {
            val service: ServiceModel? = getServiceById(serviceId)
            editService(service!!.serviceId, service.name, service.description, service.address, name, service.authorId, phone, service.price, service.currency)
        }

        db.update(UserModel.TABLE_NAME, cv, "${UserModel.COLUMN_USER_ID} = ?", arrayOf(userId.toString()))
    }
}