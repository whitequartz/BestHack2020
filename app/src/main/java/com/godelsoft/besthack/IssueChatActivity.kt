package com.godelsoft.besthack

import android.content.Intent
import android.content.pm.ActivityInfo
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.transition.TransitionManager
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

    var id = 0L
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT;
        setContentView(R.layout.activity_issue_chat)

        val arguments = intent.extras
        val chatId = arguments?.get("chatId")?.toString()?.toLong()?:1
        id = chatId

        recycleView.layoutManager = LinearLayoutManager(this)
        recycleAdapter = MessageAdapter(this, recycleView, chatId)
        recycleView.adapter = recycleAdapter
        bottom.visibility = GONE

        val t1 = TcpRequest("HISTORY $chatId") { res ->
            if (res?.succ == true) {
                val msgs = JSONArray(res.data).let {
                    0.until(it.length()).map { i -> it.optJSONObject(i) }
                }.map { MessageJSON(it.toString()) }.map { it1 ->
                    Message(
                        User(it1.sender, "NAME", when (it1.mtype) {
                            0 -> UserType.WORKER
                            1 -> UserType.SUPPORT
                            else -> UserType.WORKER // Care
                        }),
                        it1.text,
                        Date(it1.time.toString().toLong() * 1000L).let { "${CalFormatter.datef(it)} ${CalFormatter.timef(it)}" })
                }
                runOnUiThread {
                    recycleAdapter.add(msgs)
                }
            }
            else {
                // TODO
            }

            // Chat listener
            connector = ChatConnector(chatId) { res ->
                if (res != null) {
                    var tstr: String = res
                    if (tstr[tstr.lastIndex] != '}' && tstr[tstr.lastIndex] != ']')
                        tstr += if (tstr[0] == ']') ']' else '}'
                    println("[LISTENED]:$tstr")
                    val t = JSONObject(tstr)
                    val sender = t.optLong("SenderID")
                    if (sender != User.current.ID && sender != -1L) {
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
                            sendAndLoadMessage(chatId, editText_message.text.toString())
                            editText_message.text.clear()
                        }
                    }, 1000)
                    startActivityForResult(Intent(this, DeviceActivity::class.java), 10)
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
            val t = TcpRequest("REMOVE_ISSUE $chatId") { res ->
                if  (res?.succ == true) {
                    MainActivity.main.tryGetIssues()
                }
            }
            Thread(t).start()
        }

        delete.setOnClickListener {
            closeIssue()
            onBackPressed()
        }
    }

    fun sendAndLoadMessage(chatId: Long, str: String) {
        val jsonStr = """{"Sender":${User.current.ID},"Dest":${chatId},"Data":"${str.replace("\n", "^").replace("\"", "~")}"}"""
        val t = TcpRequest("SEND_MSG $jsonStr") {}
        Thread(t).start()
        recycleAdapter.add(
            arrayListOf(
                Message(
                    User.current,
                    str,
                    "${CalFormatter.datef(Calendar.getInstance())} ${CalFormatter.timef(
                        Calendar.getInstance()
                    )}"
                )
            )
        )
    }

    override fun onStop() {
        connector.stop()
        super.onStop()
    }

    override fun onActivityResult(
        requestCode: Int,
        resultCode: Int,
        data: Intent?
    ) {
        if (data == null) {
            return
        }
        val request = data.getStringExtra("request")?:"Failed"
        sendAndLoadMessage(id, request)
        super.onActivityResult(requestCode, resultCode, data)
    }
}