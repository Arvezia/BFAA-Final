package com.naufaldy.githubuser2

import android.content.ContentUris
import android.content.ContentValues
import android.content.Intent
import android.database.Cursor
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.naufaldy.githubuser2.database.DatabaseContract
import com.naufaldy.githubuser2.database.FavoriteUserHelper
import kotlinx.android.synthetic.main.activity_github_user_detail.*
import kotlinx.android.synthetic.main.github_list_menu.*
import kotlinx.android.synthetic.main.github_list_menu.view.*

class GithubUserDetail : AppCompatActivity(), View.OnClickListener {
    companion object{
        const val EXTRA_USER ="extra user"

        // disini kamu putExtra dua data yang sama dengan key yang berbeda.
        // dapat dilihat kamu menyimpan userDataIntent yang sama
        // dengan key yang berbeda ( GithubUserDetail.EXTRA_USER, GithubUserDetail.EXTRA_FAV)
        // satu saja cukup kan?
//        const val EXTRA_FAV = "extra fav"

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
    private lateinit var imageProfile: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_github_user_detail)

        favHelper = FavoriteUserHelper.getInstance(applicationContext)
        favHelper.open()
        val user: UserData? =intent.getParcelableExtra(EXTRA_USER)

        if (user != null) {

            favorite = intent.getParcelableExtra(EXTRA_FAV_DATA)
            if (favorite != null){
                setFavData()
                favStatus = true
                val favUsers: Int = R.drawable.ic_baseline_favorite_24
                btn_fav.setImageResource(favUsers)
            }else{
                setData()
            }

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
    private fun setFavData() {
        val favoriteUser = intent.getParcelableExtra(EXTRA_FAV_DATA) as FavoriteData?


        if (favoriteUser != null) {


            Glide.with(this)
                    .load(favoriteUser.avatar)
                    .into(profile_picture)
            dt_name.text = favoriteUser.name
            dt_username.text = favoriteUser.username
            dt_followers.text ="Followers  ${favoriteUser.followers}"
            dt_following.text = "Following  ${favoriteUser.following}"
            imageProfile = favoriteUser.avatar.toString()
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

    private fun setData(){
        val user=intent.getParcelableExtra(EXTRA_USER) as UserData?

        if (user != null) {


            Glide.with(this)
                    .load(user.avatar)
                    .into(profile_picture)
            dt_name.text = user.name
            dt_username.text = user.username
            dt_followers.text ="Followers  ${user.followers}"
            dt_following.text = "Following  ${user.following}"
            imageProfile = user.avatar.toString()
        }

    }



    override fun onClick(view: View) {
        val favUser: Int = R.drawable.ic_baseline_favorite_24
        val regUser: Int = R.drawable.ic_baseline_unfavorite_24
        if (view.id == R.id.btn_fav){
            if (favStatus){
                favHelper.deleteById(favorite?.username.toString())
                Toast.makeText(this, "User telah dihapus dari daftar favorit", Toast.LENGTH_SHORT).show()
                btn_fav.setImageResource(regUser)
                favStatus = true
            }
            else{
                val favUsername = dt_username.text.toString()
                val favName = dt_name.text.toString()
                val favAvatar = imageProfile
                val favFollowers = dt_followers.text.toString()
                val favFollowing = dt_following.text.toString()
                val dataFavorite = "1"

                val values = ContentValues()
                values.put(DatabaseContract.NoteCollumns.USERNAME, favUsername)
                values.put(DatabaseContract.NoteCollumns.NAME, favName)
                values.put(DatabaseContract.NoteCollumns.AVATAR, favAvatar)
                values.put(DatabaseContract.NoteCollumns.FOLLOWERS, favFollowers)
                values.put(DatabaseContract.NoteCollumns.FOLLOWING, favFollowing)

                favStatus = true
                Toast.makeText(this,"User telah ditambahkan ke daftar favorit",Toast.LENGTH_SHORT).show()
                btn_fav.setImageResource(favUser)
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.favourite_menu -> {
                val intent = Intent(this, FavouriteActivity::class.java)
                startActivity(intent)
            }
            R.id.setting_menu -> {
                val intent = Intent(this, SettingActivity::class.java)
                startActivity(intent)
            }
        }
        return super.onOptionsItemSelected(item)
    }
}

