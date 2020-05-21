package com.godelsoft.besthack

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import androidx.core.content.ContextCompat.getColor
import com.godelsoft.besthack.recycleViewAdapters.MessageAdapter
import kotlinx.android.synthetic.main.card_message.view.*

class Message(
    val sender: User,
    val text: String,
    val time: String,
    val clickF: ((user: User) -> Unit)? = null
) {
    companion object {
        fun selectMessages(adapter: MessageAdapter, vararg messages: Message): List<Message> {
            val list = mutableListOf<Message>()
            for (m in messages) {
                list.add(Message(m.sender, m.text, m.time) {
                    adapter.update(adapter.messageList.filter { message -> !list.contains(message) }.plus(
                        Message(m.sender, m.text, m.time)
                    ))
                    m.clickF?.apply {
                        this(it)
                    }
                    adapter.scrollDown()
                })
            }
            return list
        }
    }
}