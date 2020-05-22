package com.godelsoft.besthack

import com.godelsoft.besthack.recycleViewAdapters.MessageAdapter
import org.json.JSONObject

class Message(
    val sender: User,
    var text: String,
    val time: String,
    var clickF: ((user: User) -> Unit)? = null
) {
    companion object {
        fun selectMessages(adapter: MessageAdapter, messages:  ArrayList<Message>): List<Message> {
            val list = mutableListOf<Message>()
            for (m in messages) {
                list.add(Message(m.sender, m.text, m.time) {
                    adapter.remove(list)
                    adapter.add(listOf(Message(m.sender, m.text, m.time)))
                    adapter.recyclerView.postDelayed({
                        m.clickF?.apply {
                            this(it)
                        }
                    }, (400).toLong())
                })
            }
            return list
        }
    }
}

class MessageJSON(private val json: String) : JSONObject(json) {
    val id              = this.optLong("ID")
    val sender          = this.optLong("SenderID")
    val issue           = this.optLong("IssueID")
    val time            = this.optLong("Time")
    val text: String    = this.optString("Content")
    val mtype           = this.optInt("MType")
}