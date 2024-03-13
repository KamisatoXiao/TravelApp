package com.example.mainactivity.database

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class Want2GoDBHelper(val context:Context,name:String,version:Int):
    SQLiteOpenHelper(context,name,null,version){
    private val createLandscape="create table want2goList( id integer primary key autoincrement,name text)"

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(createLandscape)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int){}
}