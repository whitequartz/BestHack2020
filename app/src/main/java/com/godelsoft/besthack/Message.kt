package com.godelsoft.besthack

import com.godelsoft.besthack.recycleViewAdapters.MessageAdapter

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