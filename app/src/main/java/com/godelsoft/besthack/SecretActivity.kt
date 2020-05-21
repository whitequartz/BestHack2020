package com.godelsoft.besthack

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_secret.*

class SecretActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_secret)

        button_test_1.setOnClickListener {
            response_testbox.setText("TESTTESTTESTTEST")
        }

        button_test_2.setOnClickListener {
            response_testbox.setText("2222222 2222 2222222 22222222")
        }
    }
}