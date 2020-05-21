package com.godelsoft.besthack

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_sign_in.*
import org.jetbrains.anko.*
import java.lang.Exception

class LoginActivity : AppCompatActivity()  {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)

        buttonLogin.setOnClickListener {
            var signedIn = false
            try {
                signedIn = true  // Запрос лог: textEmailAddress.text пароль: textPassword.text
            } catch (e: Exception) {  }

            if (signedIn) {
                startActivity(intentFor<MainActivity>().newTask().clearTask())
            } else {

            }
        }

        buttonCreateNew.setOnClickListener {
            startActivity(intentFor<RegisterActivity>().newTask().clearTask())
        }
    }
}