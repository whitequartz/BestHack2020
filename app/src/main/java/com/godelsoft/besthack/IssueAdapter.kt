package com.godelsoft.besthack

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat.*
import androidx.recyclerview.widget.RecyclerView


class IssueAdapter(
    private val context: Context
) : RecyclerView.Adapter<IssueAdapter.EventViewHolder>() {

    private var issueList = mutableListOf<Issue>()

    inner class EventViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private var header: TextView = itemView.findViewById(R.id.header)
        private var description: TextView = itemView.findViewById(R.id.description)
        private var time: TextView = itemView.findViewById(R.id.time)

        fun bind(issue: Issue) {
            header.text = issue.header
            description.text = issue.description
//            when (issue.event.category) {
//                EventCategory.PERSONAL ->
//                    categoryColor.setBackgroundColor(getColor(context, R.color.colorEventPersonal))
//                EventCategory.GLOBAL ->
//                    categoryColor.setBackgroundColor(getColor(context, R.color.colorEventGlobal))
//                EventCategory.LBG ->
//                    categoryColor.setBackgroundColor(getColor(context, R.color.colorEventLGB))
//            }
            time.text = issue.time
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventViewHolder {
        return EventViewHolder(
            LayoutInflater
                .from(context)
                .inflate(R.layout.card_issue, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return issueList.count()
    }

    override fun onBindViewHolder(holder: EventViewHolder, position: Int) {
        issueList[position].let { holder.bind(it) }
    }

    fun update(data: List<Issue>) {
        issueList.clear()
        issueList.addAll(data)
        notifyDataSetChanged()
    }
}