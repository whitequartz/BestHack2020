package com.godelsoft.besthack

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_secret.*
import kotlinx.android.synthetic.main.activity_sign_in.*
import org.jetbrains.anko.*
import java.lang.Exception

class SignInActivity : AppCompatActivity()  {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)

        buttonLogin.setOnClickListener {
            try {
                val data = "AUTH ${textEmailAddress.text} ${textPassword.text} "
                val test = TcpRequest(data) { res ->
                    if (res?.succ == true) {
                        runOnUiThread {
                            startActivity(intentFor<MainActivity>().newTask().clearTask())
                        }
                    }
                }
                Thread(test).start()
            } catch (e: Exception) {
                // TODO
            }
        }

        buttonCreateNew.setOnClickListener {
            startActivity(intentFor<RegisterActivity>().newTask().clearTask())
        }
    }
}