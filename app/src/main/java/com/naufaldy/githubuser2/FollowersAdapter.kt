package com.naufaldy.githubuser2

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.github_list_menu.view.*

class FollowersAdapter: RecyclerView.Adapter<FollowersAdapter.ListDataHolder>() {

    private val listFollowers = ArrayList<FollowersData>()

    fun setData(items: ArrayList<FollowersData>) {
        listFollowers.clear()
        listFollowers.addAll(items)
        notifyDataSetChanged()
    }

    inner class ListDataHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(followersData: FollowersData) {
                with(itemView) {
                    Glide.with(itemView)
                        .load(followersData.avatar)
                        //.apply(RequestOptions().override(100, 100))
                        .into(itemView.img_user_photo)
                    itemView.tv_github_username.text = followersData.username
                }

        }


    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ListDataHolder {
        val mview = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.github_list_menu, viewGroup, false)
        return ListDataHolder(mview)
    }

    override fun onBindViewHolder(listDataHolder: ListDataHolder, position: Int) {
        listDataHolder.bind(listFollowers[position])


    }

    override fun getItemCount(): Int {
        return listFollowers.size
    }
}