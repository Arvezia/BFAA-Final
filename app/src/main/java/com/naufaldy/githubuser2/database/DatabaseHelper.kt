package com.naufaldy.githubuser2.database

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.naufaldy.githubuser2.database.DatabaseContract.NoteCollumns.Companion.AVATAR
import com.naufaldy.githubuser2.database.DatabaseContract.NoteCollumns.Companion.FOLLOWERS
import com.naufaldy.githubuser2.database.DatabaseContract.NoteCollumns.Companion.FOLLOWING
import com.naufaldy.githubuser2.database.DatabaseContract.NoteCollumns.Companion.TABLE_NAME
import com.naufaldy.githubuser2.database.DatabaseContract.NoteCollumns.Companion.USERNAME

class DatabaseHelper (context: Context): SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION){
    companion object{
        private const val DATABASE_NAME = "favorite_user"
        private const val DATABASE_VERSION = 1
        private const val SQL_CREATE_TABLE = "CREATE TABLE $TABLE_NAME"+
                "${USERNAME} TEXT NOT NULL"+
                "${AVATAR} TEXT NOT NULL"+
                "${FOLLOWING} TEXT NOT NULL"+
                "${FOLLOWERS} TEXT NOT NULL"
    }

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(SQL_CREATE_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }
}