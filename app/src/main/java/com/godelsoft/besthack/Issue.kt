package com.godelsoft.besthack

import android.widget.Toast

data class Issue(
    val header: String,
    val time: String,
    val description: String,
    val chat: Chat
){
    companion object {
        fun startCreation() {
            Toast.makeText(MainActivity.mainContext, "startCreation()", Toast.LENGTH_SHORT).show()
        }
    }
}