package com.godelsoft.besthack

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import org.jetbrains.anko.startActivity

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val sighedIn = false  // Запрос

        if (sighedIn)
            startActivity<MainActivity>()
        else
            startActivity<SignInActivity>()

        finish()
    }
}