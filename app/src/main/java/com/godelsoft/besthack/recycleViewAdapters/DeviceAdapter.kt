package com.godelsoft.besthack.recycleViewAdapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.godelsoft.besthack.CalFormatter
import com.godelsoft.besthack.Device
import com.godelsoft.besthack.Issue
import com.godelsoft.besthack.R
import java.util.*


class DeviceAdapter(
    private val context: Context
) : RecyclerView.Adapter<DeviceAdapter.DeviceViewHolder>() {

    private var deviceList = mutableListOf<Device>()

    inner class DeviceViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private var header: TextView = itemView.findViewById(R.id.header)
        private var description: TextView = itemView.findViewById(R.id.description)
        private var time: TextView = itemView.findViewById(R.id.time)

        fun bind(device: Device) {
            header.text = device.type.toString()
            description.text = device.model
//            when (issue.event.category) {
//                EventCategory.PERSONAL ->
//                    categoryColor.setBackgroundColor(getColor(context, R.color.colorEventPersonal))
//                EventCategory.GLOBAL ->
//                    categoryColor.setBackgroundColor(getColor(context, R.color.colorEventGlobal))
//                EventCategory.LBG ->
//                    categoryColor.setBackgroundColor(getColor(context, R.color.colorEventLGB))
//            }
            time.text = CalFormatter.datef(device.buyTime.apply { add(Calendar.MILLISECOND,
                device.validTime.toInt()
            ) })
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DeviceViewHolder {
        return DeviceViewHolder(
            LayoutInflater
                .from(context)
                .inflate(R.layout.card_issue, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return deviceList.count()
    }

    override fun onBindViewHolder(holder: DeviceViewHolder, position: Int) {
        deviceList[position].let { holder.bind(it) }
    }

    fun update(data: List<Device>) {
        deviceList.clear()
        deviceList.addAll(data)
        notifyDataSetChanged()
    }
}