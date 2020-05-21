package com.godelsoft.besthack

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.os.Bundle
import android.transition.TransitionManager
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_issue_chat.*
import androidx.recyclerview.widget.LinearLayoutManager
import com.godelsoft.besthack.recycleViewAdapters.MessageAdapter
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
        recycleAdapter.add(Message.selectMessages(recycleAdapter,
            Message(User.current, "1", "sss") {
                recycleAdapter.add(mutableListOf(Message(ts, "Точно 1?", "sss")).apply {
                    addAll(Message.selectMessages(recycleAdapter,
                            Message(User.current, "Точно!", "2") {
                                recycleAdapter.add(listOf(Message(ts, "ok", "228")))
                            },
                            Message(User.current, "Нет", "2") {
                                connectToSupport()
                            }
                        ))
                })
            },
            Message(User.current, "2", "sss") {
                startActivity<LoginActivity>()
            }
        ))



//        var f = {}
//        f = {
//            recycleAdapter.add(Message.selectMessages(recycleAdapter,
//                Message(User.current, "Lorem 1", "time") {
//                    Toast.makeText(MainActivity.mainContext, "Lorem 1", Toast.LENGTH_SHORT).show()
//                    f()
//                },Message(User.current, "Lorem 2", "time") {
//                    Toast.makeText(MainActivity.mainContext, "Lorem 2", Toast.LENGTH_SHORT).show()
//                    f()
//                },Message(User.current, "Lorem 3", "time") {
//                    Toast.makeText(MainActivity.mainContext, "Lorem 3", Toast.LENGTH_SHORT).show()
//                    f()
//                },Message(User.current, "Lorem 4", "time") {
//                    Toast.makeText(MainActivity.mainContext, "Lorem 4", Toast.LENGTH_SHORT).show()
//                    f()
//                },Message(User.current, "Lorem 5", "time") {
//                    Toast.makeText(MainActivity.mainContext, "Lorem 5", Toast.LENGTH_SHORT).show()
//                    f()
//                },Message(User.current, "Lorem 6", "time") {
//                    Toast.makeText(MainActivity.mainContext, "Lorem 6", Toast.LENGTH_SHORT).show()
//                    f()
//                },Message(User.current, "Lorem 7", "time") {
//                    Toast.makeText(MainActivity.mainContext, "Lorem 7", Toast.LENGTH_SHORT).show()
//                    f()
//                }
//            ))
//        }
//
//        recycleAdapter.update(
//            mutableListOf(
//            Message(User.current, "text", "time"),
//            Message(User.current, "text", "time"),
//            Message(User.current, "text", "time"),
//            Message(ts, "text", "time"),
//            Message(ts, "text", "time"),
//            Message(User.current, "text", "time"),
//            Message(ts, "text", "time"),
//            Message(ts, "text", "time"),
//            Message(ts, "text", "time"),
//            Message(User.current, "text", "time")
//        ).apply { addAll(Message.selectMessages(recycleAdapter,
//                Message(User.current, "Lorem 1", "time") {
//                    Toast.makeText(MainActivity.mainContext, "Lorem 1", Toast.LENGTH_SHORT).show()
//                    f()
//                },Message(User.current, "Lorem 2", "time") {
//                    Toast.makeText(MainActivity.mainContext, "Lorem 2", Toast.LENGTH_SHORT).show()
//                    f()
//                },Message(User.current, "Lorem 3", "time") {
//                    Toast.makeText(MainActivity.mainContext, "Lorem 3", Toast.LENGTH_SHORT).show()
//                    f()
//                },Message(User.current, "Lorem 4", "time") {
//                    Toast.makeText(MainActivity.mainContext, "Lorem 4", Toast.LENGTH_SHORT).show()
//                    f()
//                },Message(User.current, "Lorem 5", "time") {
//                    Toast.makeText(MainActivity.mainContext, "Lorem 5", Toast.LENGTH_SHORT).show()
//                    f()
//                },Message(User.current, "Lorem 6", "time") {
//                    Toast.makeText(MainActivity.mainContext, "Lorem 6", Toast.LENGTH_SHORT).show()
//                    f()
//                },Message(User.current, "Lorem 7", "time") {
//                    Toast.makeText(MainActivity.mainContext, "Lorem 7", Toast.LENGTH_SHORT).show()
//                    f()
//                }
//                )) })

        recycleView.apply {
            layoutManager = LinearLayoutManager(MainActivity.main).apply {
                stackFromEnd = true
            }
        }

//        recycleAdapter.scrollDown()
    }
}