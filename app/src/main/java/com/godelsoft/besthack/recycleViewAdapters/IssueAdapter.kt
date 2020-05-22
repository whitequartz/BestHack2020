package com.godelsoft.besthack.recycleViewAdapters

import android.R.attr.name
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.godelsoft.besthack.*
import java.util.*


class IssueAdapter(
    private val context: Context
) : RecyclerView.Adapter<IssueAdapter.IssueViewHolder>() {

    private var issueList = mutableListOf<Issue>()

    inner class IssueViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private var header: TextView = itemView.findViewById(R.id.header)
        private var description: TextView = itemView.findViewById(R.id.description)
        private var time: TextView = itemView.findViewById(R.id.time)

        fun bind(issue: Issue) {
            header.text = "Заявка №${issue.ID + 1000L}"
            description.text = issue.description

            time.text = Date(issue.time.toLong() * 1000L).let { "${CalFormatter.datef(it)} ${CalFormatter.timef(it)}" }
            itemView.setOnClickListener {
                MainActivity.main.startActivity(Intent(MainActivity.main, IssueChatActivity::class.java).apply {
                    putExtra("chatId", issue.ID)
                })
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IssueViewHolder {
        return IssueViewHolder(
            LayoutInflater
                .from(context)
                .inflate(R.layout.card_issue, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return issueList.count()
    }

    override fun onBindViewHolder(holder: IssueViewHolder, position: Int) {
        issueList[position].let { holder.bind(it) }
    }

    fun update(data: List<Issue>) {
        issueList.clear()
        issueList.addAll(data)
        notifyDataSetChanged()
    }
}