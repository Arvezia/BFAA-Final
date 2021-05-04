package com.naufaldy.githubuser2

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.github_list_menu.view.*

class FavoriteAdapter(private val activity: Activity): RecyclerView.Adapter<FavoriteAdapter.FavoriteViewHolder>() {
    var listFav = ArrayList<FavoriteData>()
        set(listFav){
            if(listFav.size > 0){
                this.listFav.clear()
            }
            this.listFav.addAll(listFav)

            notifyDataSetChanged()
        }

    inner class FavoriteViewHolder(itemView: View):RecyclerView.ViewHolder(itemView) {
        fun bind(favoriteData: FavoriteData){
            with(itemView){
                Glide.with(itemView)
                        .load(favoriteData.avatar)
                        .into(itemView.img_user_photo)
                itemView.tv_github_username.text = favoriteData.username
                itemView.setOnClickListener(
                        CustomOnItemClickListener(adapterPosition,object :CustomOnItemClickListener.OnItemClickCallback{

                            override fun onItemClicked(view: View, position: Int) {
                                val intent = Intent(activity,GithubUserDetail::class.java)
                                intent.putExtra(GithubUserDetail.EXTRA_POSITION, position)
                                intent.putExtra(GithubUserDetail.EXTRA_FAV_DATA, favoriteData)
                                activity.startActivity(intent)
                            }

                        })
                )
            }

        }

    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): FavoriteViewHolder {
        val mview = LayoutInflater.from(viewGroup.context)
                .inflate(R.layout.github_list_menu, viewGroup, false)
        return FavoriteViewHolder(mview)
    }

    override fun onBindViewHolder(favoriteViewHolder: FavoriteViewHolder, position: Int) {
        favoriteViewHolder.bind(listFav[position])
    }

    override fun getItemCount(): Int {
        return listFav.size
    }
}