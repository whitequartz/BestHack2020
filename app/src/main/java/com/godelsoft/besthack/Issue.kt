package com.godelsoft.besthack

import android.widget.Toast
import org.json.JSONObject

data class Issue(
    val ID:             Long,
    val header:         String,
    val time:           String,
    val description:    String,
    val chat:           Chat
){
    companion object {
        fun startCreation() {
            Toast.makeText(MainActivity.mainContext, "startCreation()", Toast.LENGTH_SHORT).show()
        }
    }
}

class IssueJSON(json: String) : JSONObject(json) {
    val id: Long            = this.optLong("ID")
    val title: String       = this.optString("Title")
    val time: Long          = this.optLong("Time")
    val description: String = this.optString("Descr")
    val isOpen: Boolean     = this.optBoolean("IsOpen")
    val userId: Long        = this.optLong("UserID")
    val tpId: Long          = this.optLong("TpID")
//    val chatId: Long          = 0
}
