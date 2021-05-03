package com.naufaldy.githubuser2

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.github_list_menu.view.*

class FollowingAdapter: RecyclerView.Adapter<FollowingAdapter.ListDataHolder>() {
    private val listFollowing = ArrayList<FollowingData>()

    fun setData(items: ArrayList<FollowingData>) {
        listFollowing.clear()
        listFollowing.addAll(items)
        notifyDataSetChanged()
    }

    inner class ListDataHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(followingData: FollowingData) {
            with(itemView) {
                Glide.with(itemView)
                        .load(followingData.avatar)
                        //.apply(RequestOptions().override(100, 100))
                        .into(itemView.img_user_photo)
                itemView.tv_github_username.text = followingData.username
            }

        }


    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ListDataHolder {
        val mview = LayoutInflater.from(viewGroup.context)
                .inflate(R.layout.github_list_menu, viewGroup, false)
        return ListDataHolder(mview)
    }

    override fun onBindViewHolder(listDataHolder: ListDataHolder, position: Int) {
        listDataHolder.bind(listFollowing[position])


    }

    override fun getItemCount(): Int {
        return listFollowing.size
    }
}