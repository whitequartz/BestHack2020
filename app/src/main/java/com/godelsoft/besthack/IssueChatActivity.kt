package com.godelsoft.besthack

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.transition.TransitionManager
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.recyclerview.widget.LinearLayoutManager
import com.godelsoft.besthack.recycleViewAdapters.MessageAdapter
import kotlinx.android.synthetic.main.activity_issue_chat.*
import kotlinx.android.synthetic.main.activity_main.recycleView
import java.util.*

class IssueChatActivity : AppCompatActivity() {
    lateinit var recycleAdapter: MessageAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_issue_chat)

        recycleView.layoutManager = LinearLayoutManager(this)
        recycleAdapter = MessageAdapter(this, recycleView)
        recycleView.adapter = recycleAdapter
        bottom.visibility = GONE

        // Chat listener
        val connector = ChatConnector(1) { res ->
            if (res != null) {
                val ts = User(0, "Support", UserType.SUPPORT)
                runOnUiThread {
                    recycleAdapter.add(listOf(Message(ts, res, "sss")))
                }
            }
        }
        Thread(connector).start()

        back.setOnClickListener {
            onBackPressed()
//            val t = TcpRequest("SEND_MSG helllLLL0VeEEEEEEEEEEEEEu!ersDA") {}
//            Thread(t).start()
        }


        val ts = User(0, "Support", UserType.SUPPORT)
        val bot = Bot(ts, User.current, recycleAdapter) {
            bottom.visibility = VISIBLE
            TransitionManager.beginDelayedTransition(root)

            send.setOnClickListener {
                recycleAdapter.add(arrayListOf(
                    Message(User.current, editText_message.text.toString(), "${CalFormatter.datef(Calendar.getInstance())} ${CalFormatter.timef(Calendar.getInstance())}")
                ))
                editText_message.text.clear()
            }
        }

        recycleAdapter.add(Message.selectMessages(recycleAdapter, arrayListOf(
            bot.getMessage("FAQ"),
            bot.getMessage("request"),
            bot.getMessage("call support"))))

        recycleView.apply {
            layoutManager = LinearLayoutManager(MainActivity.main).apply {
                stackFromEnd = true
            }
        }
    }
}