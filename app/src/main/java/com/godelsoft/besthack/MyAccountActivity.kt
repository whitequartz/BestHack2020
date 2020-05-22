package com.godelsoft.besthack

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.godelsoft.besthack.recycleViewAdapters.IssueAdapter
import kotlinx.android.synthetic.main.activity_my_account.*

class MyAccountActivity : AppCompatActivity() {
    private lateinit var recycleAdapter: IssueAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_account)

        back.setOnClickListener {
            onBackPressed()
        }

        button_logout.setOnClickListener {
            // TODO logout button
        }
    }
}