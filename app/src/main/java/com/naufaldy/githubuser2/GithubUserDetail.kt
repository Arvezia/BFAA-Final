package com.naufaldy.githubuser2

import android.database.Cursor
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.naufaldy.githubuser2.database.FavoriteUserHelper
import kotlinx.android.synthetic.main.activity_github_user_detail.*
import kotlinx.android.synthetic.main.github_list_menu.*
import kotlinx.android.synthetic.main.github_list_menu.view.*

class GithubUserDetail : AppCompatActivity(), View.OnClickListener {
    companion object{
    const val EXTRA_USER ="extra user"
    const val EXTRA_FAV = "extra fav"
    const val EXTRA_POSITION = "extra position"
    const val EXTRA_FAV_DATA = "extra fav data"

        @StringRes
        private val TAB_TITLES = intArrayOf(
                R.string.followers,
                R.string.following
        )
    }
    private lateinit var favHelper : FavoriteUserHelper
    private var favStatus = false
    private var favorite: FavoriteData? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_github_user_detail)

        favHelper = FavoriteUserHelper.getInstance(applicationContext)
        favHelper.open()

        FavoriteData = intent.getParcelableExtra(EXTRA_FAV_DATA)
        if (favorite != null){
            setFavData()
            favStatus = true
            val favUsers : Int = R.drawable.ic_baseline_favorite_24
            btn_fav.setImageResource(favUsers)
        }else{
            val user: UserData? =intent.getParcelableExtra(EXTRA_USER)

            if (user != null) {


                Glide.with(this)
                        .load(user.avatar)
                        .into(profile_picture)
                dt_name.text = user.name
                dt_username.text = user.username
                dt_followers.text ="Followers  ${user.followers}"
                dt_following.text = "Following  ${user.following}"

                tabViewPage(user.username!!)
            }
            btn_fav.setOnClickListener(this)
        }



    }
    private fun tabViewPage(username:String){
        val detailPagerAdapter = DetailPagerAdapter(this,username)
        val viewPager: ViewPager2 = findViewById(R.id.viewpager)
        viewPager.adapter = detailPagerAdapter
        val tabs: TabLayout = findViewById(R.id.tabs)
        TabLayoutMediator(tabs,viewPager){
            tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()
    }


/*            val user: UserData? =intent.getParcelableExtra(EXTRA_USER)

        if (user != null) {


            Glide.with(this)
                    .load(user.avatar)
                    .into(profile_picture)
            dt_name.text = user.name
            dt_username.text = user.username
            dt_followers.text ="Followers  ${user.followers}"
            dt_following.text = "Following  ${user.following}"

            tabViewPage(user.username!!)
        } */

    private fun setFavData(favStatus: Boolean){
        if(favStatus){
            btn_fav.setImageResource(R.drawable.ic_baseline_favorite_24)
        }
        else{
            btn_fav.setImageResource(R.drawable.ic_baseline_unfavorite_24)
        }

    }

    override fun onClick(view: View?) {
        val favData =intent.getParcelableExtra(EXTRA_FAV_DATA) as UserData
        when (view?.id){
            R.id.btn_fav ->{
                if (favStatus){
                    val githubUser = favData.username.toString()
                    favHelper.deleteById(githubUser)
                    Toast.makeText(this, "Data dihapus dari list favorite",Toast.LENGTH_SHORT).show()
                    setFavData(false)
                    favStatus = true
                }
                else{

                }
            }
        }
    }

}