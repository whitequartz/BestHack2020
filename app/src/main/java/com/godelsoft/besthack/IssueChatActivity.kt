package com.godelsoft.besthack

import android.content.pm.ActivityInfo
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
import org.json.JSONArray
import org.json.JSONObject
import java.util.*

class IssueChatActivity : AppCompatActivity() {
    lateinit var recycleAdapter: MessageAdapter
    lateinit var connector: ChatConnector

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT;
        setContentView(R.layout.activity_issue_chat)

        recycleView.layoutManager = LinearLayoutManager(this)
        recycleAdapter = MessageAdapter(this, recycleView)
        recycleView.adapter = recycleAdapter
        bottom.visibility = GONE

        val t1 = TcpRequest("HISTORY ${1}") { res ->
            if (res?.succ == true) {
                val msgs = JSONArray(res.data).let {
                    0.until(it.length()).map { i -> it.optJSONObject(i) }
                }.map { MessageJSON(it.toString()) }.map {
                    Message(
                        User(it.sender, "NAME", when (it.mtype) {
                            0 -> UserType.WORKER
                            1 -> UserType.SUPPORT
                            else -> UserType.WORKER // Care
                        }),
                        it.text,
                        it.time.toString())
                }
                runOnUiThread {
                    recycleAdapter.add(msgs)
                }
            }
            else {
                // TODO
            }

            // Chat listener
            connector = ChatConnector(1) { res ->
                if (res != null) {
                    var tstr: String = res
                    if (tstr[tstr.lastIndex] != '}' && tstr[tstr.lastIndex] != ']')
                        tstr += if (tstr[0] == ']') ']' else '}'
                    println("[LISTENED]:$tstr")
                    val t = JSONObject(tstr)
                    if (t.optLong("SenderID") != User.current.ID) {
                        val ts = User(t.optLong("SenderID"), "Support", UserType.SUPPORT)
                        runOnUiThread {
                            recycleAdapter.add(listOf(Message(ts, t.optString("Content"), "sss")))
                        }
                    }
                }
            }
            Thread(connector).start()

            runOnUiThread {
                // Knopo4ki
                val ts = User(-1, "Support", UserType.SUPPORT)
                val bot = Bot(ts, User.current, recycleAdapter) {
                    recycleView.postDelayed({
                        bottom.visibility = VISIBLE
                        TransitionManager.beginDelayedTransition(root)

                        send.setOnClickListener {
                            recycleAdapter.add(
                                arrayListOf(
                                    Message(
                                        User.current,
                                        editText_message.text.toString(),
                                        "${CalFormatter.datef(Calendar.getInstance())} ${CalFormatter.timef(
                                            Calendar.getInstance()
                                        )}"
                                    )
                                )
                            )
                            // Moved to Message adapter
                            val jsonStr = """{"Sender":${User.current.ID},"Dest":${1},"Data":"${editText_message.text}"}"""
                            val t = TcpRequest("SEND_MSG $jsonStr") {}
                            Thread(t).start()
                            editText_message.text.clear()
                        }
                    }, 1000)
                }

                recycleAdapter.add(
                    Message.selectMessages(
                        recycleAdapter, arrayListOf(
                            bot.getMessage("FAQ"),
                            bot.getMessage("request"),
                            bot.getMessage("call support")
                        )
                    )
                )

                recycleView.apply {
                    layoutManager = LinearLayoutManager(MainActivity.main).apply {
                        stackFromEnd = true
                    }
                }
            }
        }
        Thread(t1).start()

        back.setOnClickListener {
            onBackPressed()
        }

        val closeIssue = fun() {
            //TODO close issue DB
        }

        delete.setOnClickListener {
            closeIssue()
            onBackPressed()
        }
    }

    override fun onStop() {
        connector.stop()
        super.onStop()
    }
}