package com.naufaldy.githubuser2

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize


@Parcelize
data class FollowingData(
        var username: String? = "",
        var name: String? = "",
        var following: String? = "",
        var followers: String? = "",
        var avatar: String? = ""
):Parcelable
