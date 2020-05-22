package com.godelsoft.besthack

import android.content.Context
import android.content.SharedPreferences
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_secret.*
import kotlinx.android.synthetic.main.activity_sign_in.*
import org.jetbrains.anko.*
import org.json.JSONObject
import java.lang.Exception

class LoginActivity : AppCompatActivity()  {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT;
        setContentView(R.layout.activity_sign_in)

        val mSettings =
            getSharedPreferences(GlobalDataLoader.APP_PREFERENCES, Context.MODE_PRIVATE)


        if (mSettings.contains(GlobalDataLoader.APP_TOKEN)) {
            Log.d("AUTH", "Found token")
            val token = mSettings.getString(GlobalDataLoader.APP_TOKEN, "") ?: ""
            Log.d("AUTH", token)
            if (token != "") {
                val req = TcpRequest("CHECK_TOKEN $token") { res ->
                    if (res?.succ == true) {
                        val userId = (res.data ?: "0").toLong()
                        val t = TcpRequest("IS_TP $userId") {
                            if (it?.succ == true) {
                                val type = UserType.WORKER
                                if (it.data == "1")
                                    User.current.type = UserType.SUPPORT
                                User.current = User(userId, "NAME", type).apply {
                                    devices.addAll(User.userTest.devices)
                                }
                                Log.d("STATUS:", User.current.type.toString())
                                runOnUiThread {
                                    startActivity(intentFor<MainActivity>().newTask().clearTask())
                                }
                            }
                        }
                        Thread(t).run()
                    }
                }
                Thread(req).start()
            }
        }

        buttonLogin.setOnClickListener {
            try {
                val req = TcpRequest("AUTH ${textEmailAddress.text} ${textPassword.text} ") { res ->
                    if (res?.succ == true) {
                        val authData = JSONObject(res.data ?: "")
                        val userId = authData.optLong("ID")
                        val editor = mSettings.edit()
                        editor.putString(GlobalDataLoader.APP_TOKEN, authData.optString("Token"))
                        editor.apply()
                        val t = TcpRequest("IS_TP $userId") {
                            if (it?.succ == true) {
                                val type = UserType.WORKER
                                if (it.data == "1")
                                    User.current.type = UserType.SUPPORT
                                User.current = User(userId, "NAME", type).apply {
                                    devices.addAll(User.userTest.devices)
                                }
                                Log.d("STATUS:", User.current.type.toString())
                                runOnUiThread {
                                    startActivity(intentFor<MainActivity>().newTask().clearTask())
                                }
                            }
                        }
                        Thread(t).run()
                    }
                }
                Thread(req).start()
            } catch (e: Exception) { }
        }

        buttonCreateNew.setOnClickListener {
            startActivity(intentFor<RegisterActivity>().newTask().clearTask())
        }
    }
}