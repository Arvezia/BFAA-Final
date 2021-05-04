package com.naufaldy.githubuser2

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.AsyncHttpResponseHandler
import com.naufaldy.githubuser2.databinding.FragmentFollowersBinding
import cz.msebera.android.httpclient.Header
import kotlinx.android.synthetic.main.fragment_followers.*
import kotlinx.android.synthetic.main.fragment_following.*
import kotlinx.android.synthetic.main.fragment_following.FollowingLoading
import org.json.JSONArray
import org.json.JSONObject


class FollowersFragment : Fragment() {
    companion object {
        val TAG = MainActivity::class.java.simpleName
        const val EXTRA_FOLLOWERS = "extra followers"
        const val EXTRA_USERNAME = "0"

        @JvmStatic
        fun newInstance(username: String):FollowersFragment{
            return FollowersFragment().apply {
                arguments = Bundle().apply {
                    putString(EXTRA_USERNAME, username)
                }
            }
        }
    }

    private val listUser = ArrayList<FollowersData>()
    private lateinit var adapter: FollowersAdapter
    private lateinit var binding: FragmentFollowersBinding



    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentFollowersBinding.inflate(inflater, container, false)
        adapter = FollowersAdapter()
        adapter.notifyDataSetChanged()

        binding.rvFollowers.adapter = adapter
        binding.rvFollowers.layoutManager = LinearLayoutManager(activity)
        binding.rvFollowers.setHasFixedSize(true)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getFollowersData(arguments?.getString(EXTRA_USERNAME)!!)

    }


    private fun getFollowersData(username: String){

        val client = AsyncHttpClient()
        client.addHeader("Authorization", "token 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")
        client.addHeader("User-Agent", "request")
        val url = "https://api.github.com/users/${username}/followers"

        client.get(url, object : AsyncHttpResponseHandler() {
            override fun onSuccess(statusCode: Int, headers: Array<out Header>, responseBody: ByteArray) {


                val result = String(responseBody)
                Log.d(TAG, result)
                try {
                    //val responseObject = JSONObject(result)
                    val jsonArray = JSONArray(result)
                    for (i in 0 until jsonArray.length()) {
                        val jsonObject = jsonArray.getJSONObject(i)
                        val username: String = jsonObject.getString("login")
                        val followersData = FollowersData(username)
                        listUser.add(followersData)

                    }

                    getUserDetail(listUser)

                } catch (e: Exception) {
                    Log.d("Exception", e.message.toString())
                    /*Toast.makeText(this@MainActivity, e.message, Toast.LENGTH_SHORT)
                            .show()
                    e.printStackTrace()*/

                }

            }


            override fun onFailure(statusCode: Int, headers: Array<out Header>, responseBody: ByteArray, error: Throwable) {

                val errorMessage =when (statusCode){
                    401 -> "$statusCode : Bad Request"
                    403 -> "$statusCode : Forbidden"
                    404 -> "$statusCode : Not Found"
                    else -> "$statusCode : ${error.message}"
                }
                Log.d("onFailure", error.message.toString())
                /*Toast.makeText(this@MainActivity, errorMessage, Toast.LENGTH_LONG)
                        .show()*/
            }
        })
    }


    private fun getUserDetail(listUser: ArrayList<FollowersData>,) {



        val client = AsyncHttpClient()
        client.addHeader("Authorization", "token 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")
        client.addHeader("User-Agent", "request")

        for ((i,followersData ) in listUser.withIndex()) {
            var urluser = "https://api.github.com/users/${followersData.username}"

            client.get(urluser, object : AsyncHttpResponseHandler() {

                override fun onSuccess(statusCode: Int, headers: Array<out Header>, responseBody: ByteArray) {
                    val result = String(responseBody)
                    Log.d(TAG, result)
                    try {
                        val github = JSONObject(result)
                        val username: String? = github.getString("login").toString()
                        val name: String? = github.getString("name").toString()
                        val avatar: String? = github.getString("avatar_url").toString()
                        val followers: String? = github.getString("followers")
                        val following: String? = github.getString("following")
                        listUser[i].name = name
                        listUser[i].avatar = avatar
                        listUser[i].followers = followers
                        listUser[i].following = following

                    } catch (e: Exception) {
                        Log.d("Exception", e.message.toString())
                    }
                }


                override fun onFailure(statusCode: Int, headers: Array<out Header>, responseBody: ByteArray, error: Throwable) {
                    Log.d("onFailure", error.message.toString())
                }
            })
        }
        adapter.setData(listUser)
        showLoading(false)
    }


    private fun showLoading(state: Boolean){
        if (state){
            FollowersLoading.visibility = View.VISIBLE
        } else{
            FollowersLoading.visibility = View.GONE
        }
    }

    }
