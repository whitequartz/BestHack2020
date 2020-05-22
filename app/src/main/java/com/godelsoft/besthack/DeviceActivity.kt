package com.godelsoft.besthack

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.godelsoft.besthack.recycleViewAdapters.DeviceAdapter
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class DeviceActivity : AppCompatActivity() {
    lateinit var recycleAdapter: DeviceAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_device)
        val callBack = fun (device: Device) {
            setResult(Activity.RESULT_OK, Intent().apply {
                putExtra(
                    "request",
                    "Заказ:\n${device.type} \"${device.model}\"\n" +
                            "Гарантия до: ${CalFormatter.datef(Date(device.buyTime.timeInMillis + device.validTime))}\nЦена: ${device.cost}р")
            })
            finish()
        }

        recycleView.layoutManager = LinearLayoutManager(this)
        recycleAdapter =
            DeviceAdapter(this, callBack)
        recycleView.adapter = recycleAdapter

        recycleAdapter.update(listOf(
            Device(DeviceType.SYSTEM, "ASUS ROG 22-81", 74000, Calendar.getInstance().apply { set(2019, 10, 27) }, 700.daysToMillis()),
            Device(DeviceType.MONITOR, "AOC 23\"", 14000, Calendar.getInstance().apply { set(2019, 10, 27) }, 264.daysToMillis()),
            Device(DeviceType.MONITOR, "AOC 27\"", 17000, Calendar.getInstance().apply { set(2019, 10, 27) }, 124.daysToMillis()),
            Device(DeviceType.KEYBOARD, "ASUS Claymore", 10000, Calendar.getInstance().apply { set(2019, 10, 27) }, 424.daysToMillis()),
            Device(DeviceType.MOUSE, "Zowie EC2-A", 5000, Calendar.getInstance().apply { set(2019, 10, 27) }, 600.daysToMillis()),
            Device(DeviceType.HEADPHONES, "Razer Kraken", 7500, Calendar.getInstance().apply { set(2019, 10, 27) }, 500.daysToMillis()),
            Device(DeviceType.CAMERA, "LG 270", 3000, Calendar.getInstance().apply { set(2019, 10, 27) }, 365.daysToMillis()),
            Device(DeviceType.MICROPHONE, "YETI", 74000, Calendar.getInstance().apply { set(2019, 10, 27) }, 200.daysToMillis()),
            Device(DeviceType.WIFI, "Xiaomi A8", 74000, Calendar.getInstance().apply { set(2019, 10, 27) }, 900.daysToMillis())
        ))
    }
}