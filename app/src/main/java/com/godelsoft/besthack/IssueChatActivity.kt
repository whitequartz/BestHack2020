package com.godelsoft.besthack

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.godelsoft.besthack.recycleViewAdapters.MessageAdapter
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.startActivity
import java.util.Collections.addAll
import kotlin.math.log

class IssueChatActivity : AppCompatActivity() {
    lateinit var recycleAdapter: MessageAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_issue_chat)

        recycleView.layoutManager = LinearLayoutManager(this)
        recycleAdapter =
            MessageAdapter(this, recycleView)
        recycleView.adapter = recycleAdapter

        val connector = ChatConnector(1) { res ->
            if (res != null) {
                val ts = User(0, "Support", UserType.SUPPORT)
                recycleAdapter.add(listOf(Message(ts, res, "sss")))
            }
        }

        val ts = User(0, "Support", UserType.SUPPORT)
        recycleAdapter.add(Message.selectMessages(recycleAdapter,
            Message(User.current, "1", "sss") {
                recycleAdapter.add(mutableListOf(Message(ts, "Точно 1?", "sss")).apply {
                    addAll(Message.selectMessages(recycleAdapter,
                        Message(User.current, "Точно!", "2") {
                            recycleAdapter.add(listOf(Message(ts, "ok", "228")))
                        },
                        Message(User.current, "Нет", "2")
                    ))
                })
            },
            Message(User.current, "2", "sss") {
                startActivity<LoginActivity>()
//                Log.d("TEEEEEEEST", "helllLLL0VeEEEEEEEEEEEEEu!ersDA")
//                var test = TcpRequest("SEND_MSG helLL0VeEu") {}
//                Log.d("TEEEEEEEST2", "helllLLL0VeEEEEEEEEEEEEEu!ersDA")
            }
        ))

        recycleView.apply {
            layoutManager = LinearLayoutManager(MainActivity.main).apply {
                stackFromEnd = true
            }
        }
    }
}