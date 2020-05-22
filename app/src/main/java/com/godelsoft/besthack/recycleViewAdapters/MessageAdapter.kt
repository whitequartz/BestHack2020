package com.godelsoft.besthack.recycleViewAdapters

import android.content.Context
import android.util.DisplayMetrics
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.widget.FrameLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.godelsoft.besthack.Message
import com.godelsoft.besthack.R
import com.godelsoft.besthack.TcpRequest
import com.godelsoft.besthack.User
import kotlinx.android.synthetic.main.card_message.view.*


class MessageAdapter(
    private val context: Context,
    val recyclerView: RecyclerView
) : RecyclerView.Adapter<MessageAdapter.MessageViewHolder>() {
    var messageList = mutableListOf<Message>()

    inner class MessageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private var text: TextView = itemView.findViewById(R.id.text)
        private var time: TextView = itemView.findViewById(R.id.time)

        fun bind(message: Message) {
            text.text = message.text
            time.text = message.time
            var senderId = message.sender.ID

            itemView.message_root.setOnClickListener(null)

            if (message.clickF == null) {
                // send message to server
                val jsonStr = """{"Sender:${senderId},"Dest":${1},"Data":"${text.text}"}"""
                val t = TcpRequest("SEND_MSG $jsonStr") {}
                Thread(t).start()
                print(jsonStr)
                
                itemView.message_root.setCardBackgroundColor(ContextCompat.getColor(context, R.color.colorIcons))
                itemView.text.setTextColor(
                    ContextCompat.getColor(
                        context,
                        R.color.colorPrimaryText
                    )
                )
                itemView.time.visibility = VISIBLE
            } else {
                message.clickF.let {
                    itemView.message_root.setOnClickListener {
                        it(User.current)
                    }
                }
                itemView.message_root.setCardBackgroundColor(ContextCompat.getColor(context, R.color.colorAccent))
                itemView.text.setTextColor(
                    ContextCompat.getColor(
                        context,
                        R.color.colorIcons
                    )
                )
                itemView.time.visibility = GONE
            }

            if (message.sender == User.current) {
                itemView.message_root.apply {
                    val lParams = FrameLayout.LayoutParams(WRAP_CONTENT, WRAP_CONTENT, Gravity.END)
                    this.layoutParams = lParams
                }
            }
            else {
                itemView.message_root.apply {
                    val lParams = FrameLayout.LayoutParams(WRAP_CONTENT, WRAP_CONTENT, Gravity.START)
                    this.layoutParams = lParams
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageViewHolder {
        return MessageViewHolder(
            LayoutInflater
                .from(context)
                .inflate(R.layout.card_message, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return messageList.count()
    }

    override fun onBindViewHolder(holder: MessageViewHolder, position: Int) {
        messageList[position].let { holder.bind(it) }
    }

    fun update(data: List<Message>) {
        messageList.clear()
        messageList.addAll(data)
        notifyDataSetChanged()
    }

    fun add(data: List<Message>) {
        messageList.addAll(data)
        notifyItemRangeInserted(messageList.size - data.size, data.size)
    }

    fun remove(data: List<Message>) {
        var startPos = 0
        for ((i, m) in messageList.withIndex()) {
            if (m.text == data[0].text) startPos = i
        }
        messageList.removeAll(data)
        notifyItemRangeRemoved(startPos, data.size)
    }

//    fun scrollDown() {
//        recyclerView.postDelayed({ recyclerView.smoothScrollToPosition(itemCount - 1) }, 5)
//    }
}