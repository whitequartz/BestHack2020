package com.godelsoft.besthack

import android.content.Context
import android.content.pm.ActivityInfo
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.godelsoft.besthack.recycleViewAdapters.IssueAdapter
import kotlinx.android.synthetic.main.activity_my_account.*
import org.jetbrains.anko.clearTask
import org.jetbrains.anko.intentFor
import org.jetbrains.anko.newTask

class MyAccountActivity : AppCompatActivity() {
//    private lateinit var recycleAdapter: IssueAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT;
        setContentView(R.layout.activity_my_account)

        back.setOnClickListener {
            onBackPressed()
        }

        button_logout.setOnClickListener {
            val mSettings =
                getSharedPreferences(GlobalDataLoader.APP_PREFERENCES, Context.MODE_PRIVATE)
            val editor = mSettings.edit()
            editor.putString(GlobalDataLoader.APP_TOKEN, "")
            editor.apply()
            startActivity(intentFor<LoginActivity>().newTask().clearTask())
        }
    }
}