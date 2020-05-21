package com.godelsoft.besthack

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.transition.TransitionManager
import android.util.Log
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.recyclerview.widget.LinearLayoutManager
import com.godelsoft.besthack.recycleViewAdapters.MessageAdapter
import kotlinx.android.synthetic.main.activity_issue_chat.*
import kotlinx.android.synthetic.main.activity_main.recycleView
import org.json.JSONObject
import java.util.*

class IssueChatActivity : AppCompatActivity() {
    lateinit var recycleAdapter: MessageAdapter
    lateinit var connector: ChatConnector

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_issue_chat)

        recycleView.layoutManager = LinearLayoutManager(this)
        recycleAdapter = MessageAdapter(this, recycleView)
        recycleView.adapter = recycleAdapter
        bottom.visibility = GONE

        // Chat listener
        connector = ChatConnector(1) { res ->
            if (res != null) {
                var tstr: String = res
                if (tstr[tstr.lastIndex] != '}' && tstr[tstr.lastIndex] != ']')
                    tstr += if (tstr[0] == ']') ']' else '}'
                println("[LISTENED]:$tstr")
                val t = JSONObject(tstr)
                val ts = User(t.optLong("SenderID"), "Support", UserType.SUPPORT)
                runOnUiThread {
                    recycleAdapter.add(listOf(Message(ts, t.optString("Content"), "sss")))
                }
            }
        }
        Thread(connector).start()

        back.setOnClickListener {
            onBackPressed()
        }

        val ts = User(0, "Support", UserType.SUPPORT)
        val bot = Bot(ts, User.current, recycleAdapter) {
            bottom.visibility = VISIBLE
            TransitionManager.beginDelayedTransition(root)

            send.setOnClickListener {
                recycleAdapter.add(arrayListOf(
                    Message(User.current, editText_message.text.toString(), "${CalFormatter.datef(Calendar.getInstance())} ${CalFormatter.timef(Calendar.getInstance())}")
                ))
                val jsonStr = """{"Sender":${User.current.ID},"Dest":${1},"Data":"${editText_message.text}"}"""
                val t = TcpRequest("SEND_MSG $jsonStr") {}
                Thread(t).start()
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

    override fun onStop() {
        connector.stop()
        super.onStop()
    }
}