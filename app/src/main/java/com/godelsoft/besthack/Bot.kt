package com.godelsoft.besthack

import com.godelsoft.besthack.recycleViewAdapters.MessageAdapter
import java.util.*
import kotlin.collections.ArrayList

data class Bot(val botSender: User, val sender: User, val recycleAdapter: MessageAdapter) {
    private val dialogStatus =  hashMapOf<String, Message>()
    private val currentStatus: String = "/"

    fun addMessage(name: String, answer: String, array: Array<String>) {
        dialogStatus[name] = Message(sender, name, "${CalFormatter.datef(
            Calendar.getInstance())} ${CalFormatter.timef(Calendar.getInstance())}") {
            recycleAdapter.add(mutableListOf(Message(botSender, answer, "${CalFormatter.datef(
                Calendar.getInstance())} ${CalFormatter.timef(Calendar.getInstance())}")).apply {
                val messages = ArrayList<Message>()
                for (elem in array) {
                    dialogStatus[elem]?.let { messages.add(getMessage(it.text)) }
                }
                addAll(Message.selectMessages(recycleAdapter, messages
                ))
            })
        }
    }

    fun getMessage(name: String): Message {

        dialogStatus[name] ?.let { return Message(it.sender, it.text, "${CalFormatter.datef(
            Calendar.getInstance())} ${CalFormatter.timef(Calendar.getInstance())}", it.clickF) }
        return Message(sender, "Empty", "${CalFormatter.datef(
            Calendar.getInstance())} ${CalFormatter.timef(Calendar.getInstance())}")
    }

    init {
        addMessage("back", "Возвращаемся с начальному экрану", arrayOf("FAQ", "request", "support"))
        addMessage("q1", "Ans1", arrayOf("back"))
        addMessage("q2", "Ans2", arrayOf("back"))
        addMessage("FAQ", "Выберете категорию", arrayOf("q1", "q2"))
        addMessage("request", "Выберете категорию", arrayOf("q1", "q2"))
        addMessage("support", "Выберете категорию", arrayOf("q1", "q2"))
    }
}