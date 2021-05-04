package com.naufaldy.githubuser2.database

import android.database.Cursor
import com.naufaldy.githubuser2.FavoriteData

object MappingHelper {
    fun mapCursorToArrayList(favCursor: Cursor?): ArrayList<FavoriteData>{
        val favList = ArrayList<FavoriteData>()

        favCursor?.apply {
            while (moveToNext()){
                val username = getString(getColumnIndexOrThrow(DatabaseContract.NoteCollumns.USERNAME))
                val name = getString(getColumnIndexOrThrow(DatabaseContract.NoteCollumns.NAME))
                val avatar = getString(getColumnIndexOrThrow(DatabaseContract.NoteCollumns.AVATAR))
                val followers = getString(getColumnIndexOrThrow(DatabaseContract.NoteCollumns.FOLLOWERS))
                val following = getString(getColumnIndexOrThrow(DatabaseContract.NoteCollumns.FOLLOWING))
                val favorite = getString(getColumnIndexOrThrow(DatabaseContract.NoteCollumns.FAVORITE))
                favList.add(FavoriteData(username, avatar, following, followers, favorite))
            }
        }
        return favList
    }
}