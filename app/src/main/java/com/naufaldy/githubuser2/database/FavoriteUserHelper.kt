package com.naufaldy.githubuser2.database

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import com.naufaldy.githubuser2.database.DatabaseContract.NoteCollumns.Companion.TABLE_NAME
import com.naufaldy.githubuser2.database.DatabaseContract.NoteCollumns.Companion.USERNAME
import java.sql.SQLException
import kotlin.jvm.Throws

class FavoriteUserHelper(context: Context) {
    private var databaseHelper: DatabaseHelper = DatabaseHelper(context)
    private var database: SQLiteDatabase = databaseHelper.writableDatabase

    companion object {
        private const val DATABASE_TABLE = TABLE_NAME

        private var INSTANCE: FavoriteUserHelper? = null
        fun getInstance(context: Context): FavoriteUserHelper =
                INSTANCE?: synchronized(this){
                    INSTANCE?: FavoriteUserHelper(context)
                }
    }
    @Throws(SQLException::class)
    fun open(){
        database = databaseHelper.writableDatabase
    }
    fun close(){
        databaseHelper.close()

        if (database.isOpen)
            database.close()
    }
    fun queryAll(): Cursor {
        return database.query(
                DATABASE_TABLE,
                null,
                null,
                null,
                null,
                null,
                "$USERNAME ASC"
        )
    }
    fun queryById(id:String): Cursor{
        return database.query(
                DATABASE_TABLE,
                null,
                "$USERNAME = ?",
                arrayOf(id),
                null,
                null,
                null,
                null
        )
    }
    fun insert (values: ContentValues?): Long{
        return database.insert(DATABASE_TABLE, null, values)
    }
    fun update(id: String, values: ContentValues?): Int{
        return database.update(DATABASE_TABLE, values, "$USERNAME = ?", arrayOf(id))
    }
    fun deleteById(id: String):Int{
        return database.delete(DATABASE_TABLE, "$USERNAME = '$id'", null)
    }
}