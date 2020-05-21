package com.godelsoft.besthack

import android.os.Bundle
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_register.*
import kotlinx.android.synthetic.main.activity_sign_in.*
import org.jetbrains.anko.clearTask
import org.jetbrains.anko.intentFor
import org.jetbrains.anko.newTask
import java.lang.Exception

class RegisterActivity : AppCompatActivity()   {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        textErrorEMail.visibility = INVISIBLE
        textErrorPassword.visibility = INVISIBLE

        buttonRegister.setOnClickListener {
            textErrorEMail.visibility = INVISIBLE
            textErrorPassword.visibility = INVISIBLE

            if (editTextTextPassword.text.toString() != editTextTextPassword2.text.toString()) {
                textErrorPassword.visibility = VISIBLE
            }
            else {
                try {
                    val data = "REGISTER ${editTextTextEmailAddress.text} ${editTextTextPassword.text} "
                    val test = TcpRequest(data) { res ->
                        if (res?.succ == true) {
                            runOnUiThread {
                                startActivity(intentFor<MainActivity>().newTask().clearTask())
                            }
                        } else {
                            runOnUiThread {
                                textErrorEMail.visibility = VISIBLE
                            }
                        }
                    }
                    Thread(test).start()
                } catch (e: Exception) {

                }
            }
        }

        buttonLogIn.setOnClickListener {
            startActivity(intentFor<SignInActivity>().newTask().clearTask())
        }
    }
}