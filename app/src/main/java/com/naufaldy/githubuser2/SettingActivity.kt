package com.naufaldy.githubuser2

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.naufaldy.githubuser2.databinding.ActivitySettingBinding
import com.naufaldy.githubuser2.notif.AlarmReceiver
import kotlinx.android.synthetic.main.activity_setting.*

class SettingActivity : AppCompatActivity(){

    companion object{
        const val PREF_NAME = "Notif_Pref"
        const val NOTIFICATION = "notif"
    }

    private lateinit var binding: ActivitySettingBinding
    private lateinit var alarmReceiver: AlarmReceiver
    private lateinit var mUserpreference : SharedPreferences

    //create shared preference here
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        alarmReceiver = AlarmReceiver()
        mUserpreference = getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        userPreference()

    }
    private fun userPreference(){
        binding.notifSwitch.isChecked =mUserpreference.getBoolean(NOTIFICATION, false)
        //create notif setting here
        binding.notifSwitch.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked){
                alarmReceiver.setRepeatingAlarm(this,AlarmReceiver.TYPE_REPEATING,getString(R.string.daily_reminder))
            }
            else{
                alarmReceiver.cancelAlarm(this,AlarmReceiver.TYPE_REPEATING)
            }
            saveSetting(isChecked)
        }
    }
    private fun saveSetting(value: Boolean){
        val save = mUserpreference.edit()
        save.putBoolean(NOTIFICATION,value)
        save.apply()
    }

    }