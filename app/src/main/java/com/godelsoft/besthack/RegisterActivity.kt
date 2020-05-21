package com.godelsoft.besthack

import android.os.Bundle
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_register.*
import org.jetbrains.anko.clearTask
import org.jetbrains.anko.intentFor
import org.jetbrains.anko.newTask

class RegisterActivity : AppCompatActivity()   {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        textErrorEMail.visibility = INVISIBLE
        textErrorPassword.visibility = INVISIBLE

        buttonRegister.setOnClickListener {
            textErrorEMail.visibility = INVISIBLE
            textErrorPassword.visibility = INVISIBLE

            val loginExsist = false  // email лежит в editTextTextEmailAddress.text
            if(loginExsist) {
                textErrorEMail.visibility = VISIBLE
            } else if(editTextTextPassword.text.toString() != editTextTextPassword2.text.toString()) {
                textErrorPassword.visibility = VISIBLE
            } else {
                // create new account
                startActivity(intentFor<MainActivity>().newTask().clearTask())
            }
        }

        buttonLogIn.setOnClickListener {
            startActivity(intentFor<LoginActivity>().newTask().clearTask())
        }
    }
}