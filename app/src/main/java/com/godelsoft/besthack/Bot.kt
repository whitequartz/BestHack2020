package com.godelsoft.besthack

import com.godelsoft.besthack.recycleViewAdapters.MessageAdapter
import java.util.*
import kotlin.collections.ArrayList

data class Bot(val botSender: User, val sender: User, val recycleAdapter: MessageAdapter) {
    private val dialogStatus =  hashMapOf<String, Message>()
    private val currentStatus: String = "/"

    fun addMessage(hashname: String, text: String, answer: String, array: Array<String>) {
        dialogStatus[hashname] = Message(sender, text, "${CalFormatter.datef(
            Calendar.getInstance())} ${CalFormatter.timef(Calendar.getInstance())}") {
            if (answer == "") {
                val messages = ArrayList<Message>()
                for (elem in array) {
                    dialogStatus[elem]?.let { messages.add(getMessage(elem)) }
                }
                recycleAdapter.add(messages)
            } else {
                recycleAdapter.add(mutableListOf(Message(botSender, answer, "${CalFormatter.datef(
                    Calendar.getInstance())} ${CalFormatter.timef(Calendar.getInstance())}")).apply {
                    val messages = ArrayList<Message>()
                    for (elem in array) {
                        dialogStatus[elem]?.let { messages.add(getMessage(elem)) }
                    }
                    addAll(Message.selectMessages(recycleAdapter, messages
                    ))
                })
            }
        }
    }

    fun getMessage(hashname: String): Message {

        dialogStatus[hashname] ?.let { return Message(it.sender, it.text, "${CalFormatter.datef(
            Calendar.getInstance())} ${CalFormatter.timef(Calendar.getInstance())}", it.clickF) }
        return Message(sender, "", "${CalFormatter.datef(
            Calendar.getInstance())} ${CalFormatter.timef(Calendar.getInstance())}")
    }

    init {
        addMessage("back", "Назад", "", arrayOf("FAQ", "request", "call support"))
        addMessage("q1", "вопрос1", "Ans1", arrayOf("back"))
        addMessage("q2", "djghjc2", "Ans2", arrayOf("back"))
        addMessage("FAQ", "Популярные вопросы", "Выберете категорию", arrayOf("q1", "q2"))
        addMessage("request", "Создать заявку", "Выберете тип заявки", arrayOf("q1", "q2"))
        addMessage("call support", "Соединить с оператором", "", arrayOf("q1", "q2"))
    }
}