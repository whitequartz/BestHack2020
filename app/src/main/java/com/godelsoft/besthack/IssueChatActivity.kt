package com.godelsoft.besthack

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.transition.TransitionManager
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.recyclerview.widget.LinearLayoutManager
import com.godelsoft.besthack.recycleViewAdapters.MessageAdapter
import kotlinx.android.synthetic.main.activity_issue_chat.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.recycleView
import org.jetbrains.anko.startActivity
import java.util.*

class IssueChatActivity : AppCompatActivity() {
    lateinit var recycleAdapter: MessageAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_issue_chat)

        recycleView.layoutManager = LinearLayoutManager(this)
        recycleAdapter =
            MessageAdapter(this, recycleView)
        recycleView.adapter = recycleAdapter
        bottom.visibility = GONE

        back.setOnClickListener {
            onBackPressed()
        }

        val connectToSupport = fun() {
            bottom.visibility = VISIBLE
            TransitionManager.beginDelayedTransition(root)

            send.setOnClickListener {
                recycleAdapter.add(arrayListOf(
                    Message(User.current, editText_message.text.toString(), "${CalFormatter.datef(Calendar.getInstance())} ${CalFormatter.timef(Calendar.getInstance())}")
                ))
                editText_message.text.clear()
            }
        }


        val ts = User("Support", UserType.SUPPORT)
        val bot = Bot(ts, User.current, recycleAdapter)
        recycleAdapter.add(Message.selectMessages(recycleAdapter, arrayListOf(
            bot.getMessage("Популярные вопросы"),
            bot.getMessage("Подать заявку"),
            bot.getMessage("Соединить с оператором"))))


        recycleView.apply {
            layoutManager = LinearLayoutManager(MainActivity.main).apply {
                stackFromEnd = true
            }
        }

//        recycleAdapter.scrollDown()
    }
}