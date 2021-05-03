package com.naufaldy.githubuser2

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.AsyncHttpResponseHandler
import com.naufaldy.githubuser2.databinding.FragmentFollowingBinding
import cz.msebera.android.httpclient.Header
import kotlinx.android.synthetic.main.fragment_following.*
import org.json.JSONArray
import org.json.JSONObject

class FollowingFragment : Fragment() {
    companion object {
        val TAG = MainActivity::class.java.simpleName
        const val EXTRA_FOLLOWING = "extra following"
        const val EXTRA_USERNAME = "0"

        @JvmStatic
        fun newInstance(username: String):FollowingFragment{
            return FollowingFragment().apply {
                arguments = Bundle().apply {
                    putString(EXTRA_USERNAME, username)
                }
            }
        }
    }
    private val listUser = ArrayList<FollowingData>()
    private lateinit var adapter: FollowingAdapter
    private lateinit var binding: FragmentFollowingBinding


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = FragmentFollowingBinding.inflate(inflater, container, false)
        adapter = FollowingAdapter()
        adapter.notifyDataSetChanged()

        binding.rvFollowing.adapter = adapter
        binding.rvFollowing.layoutManager = LinearLayoutManager(activity)
        binding.rvFollowing.setHasFixedSize(true)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getFollowingData(arguments?.getString(FollowingFragment.EXTRA_USERNAME)!!)
    }
    private fun getFollowingData(username: String){

        val client = AsyncHttpClient()
        client.addHeader("Authorization", "token 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")
        client.addHeader("User-Agent", "request")
        val url = "https://api.github.com/users/${username}/following"

        client.get(url, object : AsyncHttpResponseHandler() {
            override fun onSuccess(statusCode: Int, headers: Array<out Header>, responseBody: ByteArray) {


                val result = String(responseBody)
                Log.d(FollowingFragment.TAG, result)
                try {
                    //val responseObject = JSONObject(result)
                    val jsonArray = JSONArray(result)
                    for (i in 0 until jsonArray.length()) {
                        val jsonObject = jsonArray.getJSONObject(i)
                        val username: String = jsonObject.getString("login")
                        val followingData = FollowingData(username)
                        listUser.add(followingData)

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


    private fun getUserDetail(listUser: ArrayList<FollowingData>,) {



        val client = AsyncHttpClient()
        client.addHeader("Authorization", "token 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")
        client.addHeader("User-Agent", "request")

        for ((i,followingData ) in listUser.withIndex()) {
            var urluser = "https://api.github.com/users/${followingData.username}"

            client.get(urluser, object : AsyncHttpResponseHandler() {

                override fun onSuccess(statusCode: Int, headers: Array<out Header>, responseBody: ByteArray) {
                    val result = String(responseBody)
                    Log.d(FollowingFragment.TAG, result)
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
            FollowingLoading.visibility = View.VISIBLE
        } else{
            FollowingLoading.visibility = View.GONE
        }
    }

}
