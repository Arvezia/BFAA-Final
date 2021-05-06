package com.naufaldy.githubuser2

import android.content.ContentProvider
import android.content.ContentValues
import android.content.Context
import android.content.UriMatcher
import android.database.Cursor
import android.net.Uri
import com.naufaldy.githubuser2.database.DatabaseContract.AUTHORITY
import com.naufaldy.githubuser2.database.DatabaseContract.NoteCollumns.Companion.CONTENT_URI
import com.naufaldy.githubuser2.database.DatabaseContract.NoteCollumns.Companion.TABLE_NAME
import com.naufaldy.githubuser2.database.FavoriteUserHelper

class FavUserProvider : ContentProvider() {

    companion object{
        private const val FAV = 1
        private const val FAV_ID = 2
        private val sUriMatcher = UriMatcher(UriMatcher.NO_MATCH)
        private lateinit var favHelper:FavoriteUserHelper

        init {
            sUriMatcher.addURI(AUTHORITY,TABLE_NAME, FAV)
            sUriMatcher.addURI(AUTHORITY, "$TABLE_NAME/#", FAV_ID)
        }
    }

    override fun onCreate(): Boolean {
        favHelper = FavoriteUserHelper.getInstance(context as Context)
        favHelper.open()
        return true
    }

    override fun query(uri: Uri, projection: Array<String>?, selection: String?,
                       selectionArgs: Array<String>?, sortOrder: String?): Cursor? {
        return when (sUriMatcher.match(uri)){
            FAV -> favHelper.queryAll()
            FAV_ID -> favHelper.queryById(uri.lastPathSegment.toString())
            else -> null
        }

    }

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<String>?): Int {
        val deleted: Int = when (FAV_ID) {
            sUriMatcher.match(uri) -> favHelper.deleteById(uri.lastPathSegment.toString())
            else -> 0
        }
        context?.contentResolver?.notifyChange(CONTENT_URI, null)
        return deleted
    }

    override fun getType(uri: Uri): String? {
        return null
    }

    override fun insert(uri: Uri, values: ContentValues?): Uri? {
        val added: Long = when (FAV) {
            sUriMatcher.match(uri) -> favHelper.insert(values)
            else -> 0
        }
        context?.contentResolver?.notifyChange(CONTENT_URI, null)
        return Uri.parse("$CONTENT_URI/$added")
    }

    override fun update(uri: Uri, values: ContentValues?, selection: String?,
                        selectionArgs: Array<String>?): Int {
        val updated: Int = when (FAV_ID) {
            sUriMatcher.match(uri) -> favHelper.update(uri.lastPathSegment.toString(),values)
            else -> 0
        }
        context?.contentResolver?.notifyChange(CONTENT_URI, null)
        return updated
    }
}