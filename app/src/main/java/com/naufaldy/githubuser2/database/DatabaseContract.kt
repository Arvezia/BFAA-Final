package com.naufaldy.githubuser2.database

import android.provider.BaseColumns

class DatabaseContract {
    internal class NoteCollumns: BaseColumns{
        companion object{
            const val TABLE_NAME = "fav"
            const val USERNAME = "username"
            const val AVATAR = "avatar_url"
            const val FOLLOWING = "following"
            const val FOLLOWERS = "followers"
            const val FAVORITE = "fav status"
        }
    }
}