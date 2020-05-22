package com.godelsoft.besthack.recycleViewAdapters

import android.content.Context
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.widget.FrameLayout
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.godelsoft.besthack.*
import kotlinx.android.synthetic.main.card_message.view.*
import java.util.*


class MessageAdapter(
    private val context: Context,
    val recyclerView: RecyclerView,
    val chatID: Long
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

            if (senderId != User.current.ID && message.text.contains("Заказ:")) {
                message.clickF = fun(user: User) {
                    fun parse(str: String): Device {
                        fun getCal(str: String) : Calendar {
                            val tuple = str.split(".").toTypedArray()
                            return Calendar.getInstance().also {
                                it.set(Calendar.DAY_OF_MONTH, tuple[0].toInt())
                                it.set(Calendar.MONTH, tuple[1].toInt() - 1)
                            }
                        }
                        val ind1 = str.indexOf(" ") + 1
                        val dt = str.substring("Заказ:\n".length, ind1).let { DeviceType.values().filter { t -> t.displayName.compareTo(it) == 0 } }
                        val model = str.substring(ind1 + 1, str.indexOf("\"\n", ind1))
                        val calendar = getCal(str.substring(str.indexOf("Гарантия до: ") + "Гарантия до: ".length, str.indexOf("\nЦена")))
                        val cost = str.substring(str.indexOf("\nЦена: ") + "\nЦена: ".length, str.length).toInt()
                        return Device(dt[0], model, cost, Calendar.getInstance(), calendar.timeInMillis - Calendar.getInstance().timeInMillis)
                    }
                    val d = parse(message.text)
                    Toast.makeText(recyclerView.context, d.model, Toast.LENGTH_SHORT).show()
                }
            }

            if (message.clickF == null) {

                itemView.message_root.setCardBackgroundColor(ContextCompat.getColor(context, R.color.colorIcons))
                itemView.text.setTextColor(
                    ContextCompat.getColor(
                        context,
                        R.color.colorPrimaryText
                    )
                )
                itemView.time.visibility = VISIBLE
            } else {
                message.clickF?.let {
                    itemView.message_root.setOnClickListener {
                        it(User.current)
                        val jsonStr = """{"Sender":${senderId},"Dest":${chatID},"Data":"${text.text}"}"""
                        val t = TcpRequest("SEND_MSG $jsonStr") {}
                        Thread(t).start()
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

            if (message.sender.ID == User.current.ID) {
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