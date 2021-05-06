package com.naufaldy.githubuser2.database

import android.net.Uri
import android.provider.BaseColumns

object DatabaseContract {
    const val AUTHORITY = "com.naufaldy.githubuser2"
    const val SCHEME = "content"

    internal class NoteCollumns: BaseColumns{
        companion object{
            const val TABLE_NAME = "fav"
            const val USERNAME = "username"
            const val NAME = "name"
            const val AVATAR = "avatar_url"
            const val FOLLOWING = "following"
            const val FOLLOWERS = "followers"
            const val FAVORITE = "fav_status"

            val CONTENT_URI: Uri = Uri.Builder().scheme(SCHEME)
                    .authority(AUTHORITY)
                    .appendPath(TABLE_NAME)
                    .build()
        }
    }
}