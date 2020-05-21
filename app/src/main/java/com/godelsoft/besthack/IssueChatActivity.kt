package com.godelsoft.besthack

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.godelsoft.besthack.recycleViewAdapters.IssueAdapter
import com.godelsoft.besthack.recycleViewAdapters.MessageAdapter
import kotlinx.android.synthetic.main.activity_main.*

class IssueChatActivity : AppCompatActivity() {
    lateinit var recycleAdapter: MessageAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_issue_chat)

        recycleView.layoutManager = LinearLayoutManager(this)
        recycleAdapter =
            MessageAdapter(this, recycleView)
        recycleView.adapter = recycleAdapter

        var f = {}
        f = {
            recycleAdapter.add(Message.selectMessages(recycleAdapter,
                Message(User.current, "Lorem 1", "time") {
                    Toast.makeText(MainActivity.mainContext, "Lorem 1", Toast.LENGTH_SHORT).show()
                    f()
                },Message(User.current, "Lorem 2", "time") {
                    Toast.makeText(MainActivity.mainContext, "Lorem 2", Toast.LENGTH_SHORT).show()
                    f()
                },Message(User.current, "Lorem 3", "time") {
                    Toast.makeText(MainActivity.mainContext, "Lorem 3", Toast.LENGTH_SHORT).show()
                    f()
                },Message(User.current, "Lorem 4", "time") {
                    Toast.makeText(MainActivity.mainContext, "Lorem 4", Toast.LENGTH_SHORT).show()
                    f()
                },Message(User.current, "Lorem 5", "time") {
                    Toast.makeText(MainActivity.mainContext, "Lorem 5", Toast.LENGTH_SHORT).show()
                    f()
                },Message(User.current, "Lorem 6", "time") {
                    Toast.makeText(MainActivity.mainContext, "Lorem 6", Toast.LENGTH_SHORT).show()
                    f()
                },Message(User.current, "Lorem 7", "time") {
                    Toast.makeText(MainActivity.mainContext, "Lorem 7", Toast.LENGTH_SHORT).show()
                    f()
                }
            ))
        }

        val ts = User("Support", UserType.SUPPORT)
        recycleAdapter.update(
            mutableListOf(
            Message(User.current, "text", "time"),
            Message(User.current, "text", "time"),
            Message(User.current, "text", "time"),
            Message(ts, "text", "time"),
            Message(ts, "text", "time"),
            Message(User.current, "text", "time"),
            Message(ts, "text", "time"),
            Message(ts, "text", "time"),
            Message(ts, "text", "time"),
            Message(User.current, "text", "time")
        ).apply { addAll(Message.selectMessages(recycleAdapter,
                Message(User.current, "Lorem 1", "time") {
                    Toast.makeText(MainActivity.mainContext, "Lorem 1", Toast.LENGTH_SHORT).show()
                    f()
                },Message(User.current, "Lorem 2", "time") {
                    Toast.makeText(MainActivity.mainContext, "Lorem 2", Toast.LENGTH_SHORT).show()
                    f()
                },Message(User.current, "Lorem 3", "time") {
                    Toast.makeText(MainActivity.mainContext, "Lorem 3", Toast.LENGTH_SHORT).show()
                    f()
                },Message(User.current, "Lorem 4", "time") {
                    Toast.makeText(MainActivity.mainContext, "Lorem 4", Toast.LENGTH_SHORT).show()
                    f()
                },Message(User.current, "Lorem 5", "time") {
                    Toast.makeText(MainActivity.mainContext, "Lorem 5", Toast.LENGTH_SHORT).show()
                    f()
                },Message(User.current, "Lorem 6", "time") {
                    Toast.makeText(MainActivity.mainContext, "Lorem 6", Toast.LENGTH_SHORT).show()
                    f()
                },Message(User.current, "Lorem 7", "time") {
                    Toast.makeText(MainActivity.mainContext, "Lorem 7", Toast.LENGTH_SHORT).show()
                    f()
                }
                )) })

        recycleAdapter.scrollDown()
    }
}