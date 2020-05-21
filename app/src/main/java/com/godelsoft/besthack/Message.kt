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