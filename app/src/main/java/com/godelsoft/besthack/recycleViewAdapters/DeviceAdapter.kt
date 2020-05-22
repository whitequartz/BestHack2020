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
import kotlin.math.cos


class DeviceAdapter(
    private val context: Context,
    private val callBack: (d: Device) -> Unit
) : RecyclerView.Adapter<DeviceAdapter.DeviceViewHolder>() {

    private var deviceList = mutableListOf<Device>()

    inner class DeviceViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private var header: TextView = itemView.findViewById(R.id.header)
        private var description: TextView = itemView.findViewById(R.id.description)
        private var type: TextView = itemView.findViewById(R.id.type)
        private var cost: TextView = itemView.findViewById(R.id.cost)

        fun bind(device: Device) {
            header.text = device.model
            description.text = "Гарантия до: ${
            CalFormatter.datef(Calendar.getInstance().apply {
                set(Calendar.SECOND, device.buyTime.get(Calendar.SECOND))
                add(Calendar.SECOND,
                    (device.validTime / 1000).toInt()
                ) })}"

            cost.text = "${device.cost}р"

            type.text = device.type.toString()
            itemView.setOnClickListener {
                callBack(device)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DeviceViewHolder {
        return DeviceViewHolder(
            LayoutInflater
                .from(context)
                .inflate(R.layout.card_device, parent, false)
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