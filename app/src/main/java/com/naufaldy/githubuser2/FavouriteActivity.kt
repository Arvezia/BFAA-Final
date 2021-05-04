package com.naufaldy.githubuser2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.naufaldy.githubuser2.database.FavoriteUserHelper
import com.naufaldy.githubuser2.database.MappingHelper
import kotlinx.android.synthetic.main.activity_favourite.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class FavouriteActivity : AppCompatActivity() {
    private lateinit var adapter: FavoriteAdapter

    companion object{
        private const val EXTRA_STATE = "extra_state"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favourite)

        showRecyclerList()
        if (savedInstanceState == null){
            loadFavAsync()
        }
        else{
            val list = savedInstanceState.getParcelableArrayList<FavoriteData>(EXTRA_STATE)
            if (list != null){
                adapter.listFav = list
            }
        }
    }

    private fun showRecyclerList() {
        adapter = FavoriteAdapter(this)
        adapter.notifyDataSetChanged()

        rv_fav.layoutManager = LinearLayoutManager(this)
        rv_fav.adapter = adapter
    }

    private fun loadFavAsync() {
        GlobalScope.launch(Dispatchers.Main) {
            favorite_loading.visibility = View.VISIBLE
            val favHelper = FavoriteUserHelper.getInstance(applicationContext)
            favHelper.open()
            val defferedFav = async(Dispatchers.IO) {
                val cursor = favHelper.queryAll()
                MappingHelper.mapCursorToArrayList(cursor)
            }
            favHelper.close()
            favorite_loading.visibility = View.INVISIBLE
            val favorite = defferedFav.await()
            if (favorite.size>0){
                adapter.listFav = favorite
            }
            else{
                adapter.listFav = ArrayList()
                showSnackBar()
            }
        }
    }
    private fun showSnackBar(){
        Toast.makeText(this,"Tidak ada user favorit", Toast.LENGTH_SHORT).show()
    }
}