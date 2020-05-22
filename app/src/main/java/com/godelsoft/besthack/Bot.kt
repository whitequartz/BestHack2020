package com.godelsoft.besthack

import com.godelsoft.besthack.recycleViewAdapters.MessageAdapter
import java.util.*
import kotlin.collections.ArrayList

data class Bot(val botSender: User, val sender: User, val recycleAdapter: MessageAdapter, val connectToSupport: (user: User) -> Unit = {}) {
    private val dialogStatus =  hashMapOf<String, Message>()
    private val currentStatus: String = "/"

    fun addMessage(hashname: String, text: String, answer: String?, array: Array<String>, f: (user: User) -> Unit = {}) {
        dialogStatus[hashname] = Message(sender, text, "") {
            if (answer == null) {
                val messages = ArrayList<Message>()
                for (elem in array) {
                    messages.add(getMessage(elem))
                }
                recycleAdapter.add(Message.selectMessages(recycleAdapter, messages))
            } else {
                recycleAdapter.add(
                    mutableListOf(
                        Message(botSender, answer, "${CalFormatter.datef(Calendar.getInstance())} ${CalFormatter.timef(Calendar.getInstance())}")
                    ).apply {
                        val messages = ArrayList<Message>()
                        for (elem in array) {
                            messages.add(getMessage(elem))
                        }
                        addAll(Message.selectMessages(recycleAdapter, messages))
                    })
            }
            f(it)
        }
    }

    fun getMessage(hashname: String): Message {

        dialogStatus[hashname] ?.let { return Message(it.sender, it.text, "${CalFormatter.datef(
            Calendar.getInstance())} ${CalFormatter.timef(Calendar.getInstance())}", it.clickF) }
        return Message(sender, "", "${CalFormatter.datef(
            Calendar.getInstance())} ${CalFormatter.timef(Calendar.getInstance())}")
    }

    init {
        addMessage("back", "Назад", null, arrayOf("FAQ", "request", "call support"))

            addMessage("other", "Проблема другого рода, свяжите меня с оператором", "Соединаем с опретором...", arrayOf(), connectToSupport)
                addMessage("poweroff", "Я не знаю как выключить компьютер", "Чтобы выключить компьютер под управлением OS Windows" +
                        "нажмите на меню пуск в левом нижнем углу, выбере пункт `выключение` -> `Завершение работы`\nВам помогла данная инструкция?", arrayOf("yes", "no"))
                addMessage("notepad", "Блокнот завис", "Для решения проблемы последовательно выполните следующие пункты:" +
                        "\n1. Сохраните данные и принудительно закройте блокнот\n2. Перезапустите компьютер\nВам помогли эти советы?", arrayOf("yes", "no"))
                addMessage("excel", "Я не знаю куда эксель сохраняет мои файлы", "Для решения проблемы последовательно выполните следующее:" +
                        "\nВ правом верхнем углу найдите меню, выберете `файл` -> `сохранить как` и выберете путь для сохранения файла`\nВам помог этот совет?", arrayOf("yes", "no"))
                addMessage("win", "Виндовс не реагирует", "Для решения проблемы последовательно выполните следующие пункты:" +
                        "\n1. Перезапустите компьютер\n2. Поменяйте компьютер:)\nВам помогли эти советы?", arrayOf("yes", "no"))
            addMessage("soft", "Проблема с программой", "Что у вас не работает?", arrayOf("win", "notepad", "excel", "poweroff", "back_FAQ"))
                addMessage("back_FAQ", "Назад", null, arrayOf("hard", "soft", "back"))
                addMessage("monitor", "На мониторе не видно изображение", "Для решения проблемы последовательно выполните следующие пункты:" +
                        "\n1. Перезапустите компьютер\n2. Включите и выключите питание монитора\n3. Поменяйте монитор\nВам помогли эти советы?", arrayOf("yes", "no"))
                    addMessage("no", "Нет, свяжите меня с тех. поддержкой", "Соединяю с оператором...", arrayOf(), connectToSupport)
                    addMessage("yes", "Да", "Рад, что смог вам помочь!", arrayOf("FAQ", "request", "call support"))
                addMessage("mouse", "Мышь ни на что не реагирует", "Для решения проблемы последовательно выполните следующие пункты:" +
                        "\n1. Перезапустите компьютер\n2. Выткните мышь из гнезда и воткните снова\n3. Поменяйте мышь\nВам помогли эти советы?", arrayOf("yes", "no"))
            addMessage("hard", "Проблема с комплектующими", "Что у вас не работает?", arrayOf("mouse", "monitor", "back_FAQ"))
        addMessage("FAQ", "Популярные вопросы", "С какого рода проблемой вы столкнулись?", arrayOf("hard", "soft", "other", "back"))

            addMessage("order", "Заявка другого рода", "Соединаем с опретором...", arrayOf(), connectToSupport)
            addMessage("order", "Заявка на покупку нового оборудования", "Перенаправляем в форму заявки", arrayOf("back"))
            addMessage("crash", "Заявка на ремонт оборудования", "Перенаправляем в форму заявки", arrayOf("back"))
        addMessage("request", "Создать заявку", "Какой тип заявки вы хотите оставить?", arrayOf("crash", "order", "other", "back"))

        addMessage("call support", "Соединить с оператором", "Соединяю с оператором...", arrayOf(), connectToSupport)
    }
}