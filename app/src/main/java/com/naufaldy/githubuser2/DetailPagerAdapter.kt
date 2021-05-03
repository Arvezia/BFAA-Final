package com.naufaldy.githubuser2

import android.icu.text.IDNA
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import java.lang.IndexOutOfBoundsException

const val EXTRA_FOLLOWERS = 0
const val EXTRA_FOLLOWING = 1

class DetailPagerAdapter(activity: GithubUserDetail, username: String) : FragmentStateAdapter(activity) {

    private val tabFragment: Map<Int, () -> Fragment> = mapOf(
            EXTRA_FOLLOWERS to {FollowersFragment.newInstance(username)},
            EXTRA_FOLLOWING to {FollowingFragment.newInstance(username)}
    )

    override fun getItemCount(): Int  = tabFragment.size

    override fun createFragment(position: Int): Fragment {
        return tabFragment[position]?.invoke() ?: throw IndexOutOfBoundsException()
    }

}