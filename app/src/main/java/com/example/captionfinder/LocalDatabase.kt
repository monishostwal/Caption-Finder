package com.example.captionfinder

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class LocalDatabase(context:Context) : SQLiteOpenHelper(context,"Local_Database",null,1) {
    var COL_1:String="id"
    var COL_2: String="caption"

    var TABLE_NAME="Local_Database"
    override fun onCreate(db: SQLiteDatabase?) {
        val table = "CREATE TABLE Local_Database(id INTEGER PRIMARY KEY AUTOINCREMENT,caption TEXT)"
        db!!.execSQL(table)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db!!.execSQL("DROP TABLE IF EXISTS"+TABLE_NAME)
        onCreate(db)
         }
    fun adddata(caption: String):Unit{

        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(COL_2, caption)
        db.insert(TABLE_NAME, null, contentValues)
    }
    fun retrievedata(){
        //TODO retrival of data in favorites.
    }
}